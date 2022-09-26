package it.uniba.app.gioco;

import it.uniba.app.avventura.GameDescription;
import it.uniba.app.avventura.Room;
import it.uniba.app.boundaries.DungeonBoundary;
import it.uniba.app.boundaries.ExceptionsBoundary;
import it.uniba.app.command.CommandList;
import it.uniba.app.command.CommandType;
import it.uniba.app.exceptions.ServernotAvailableException;
import it.uniba.app.parser.ParserOutput;
import it.uniba.app.utility.MapClient;
import it.uniba.app.utility.TimeAPI;

import java.io.*;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

/**
 * <p>Dungeon class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class Dungeon extends GameDescription {

    /** {@inheritDoc} */
    @Override
    public void init() throws SQLException, IOException {
        //Commands
        CommandList listCommands = new CommandList();
        for (int i = 0; i < 8; i++) {
            getCommands().add(listCommands.create(i));
        }
        //Rooms

        //Creo database in base alla modalità scelta.
        final String CREATE_TABLE_DEFAULT = "CREATE TABLE IF NOT EXISTS mappa (roomId INT PRIMARY KEY, desc VARCHAR(320), deadly BIT, south INT, east INT, west INT, north INT, name VARCHAR(50), look VARCHAR(320)) AS SELECT * FROM CSVREAD('./resources/mappe.txt', null  ,'fieldSeparator=;')";
        final String CREATE_TABLE_SERVER = "CREATE TABLE IF NOT EXISTS downloaded_mappa (roomId INT PRIMARY KEY, desc VARCHAR(320), deadly BIT, south INT, east INT, west INT, north INT, name VARCHAR(50), look VARCHAR(320)) AS SELECT * FROM CSVREAD('./ServerMap.txt', null  ,'fieldSeparator=;')";
        Connection conn = DriverManager.getConnection("jdbc:h2:./resources/db/mappa");
        Statement stm = conn.createStatement();
        stm.executeUpdate("DROP TABLE IF EXISTS downloaded_mappa");
        int mode;
        try {
            mode = newGame();
            while (mode == 4) mode = newGame();
        } catch (ServernotAvailableException e) {
            ExceptionsBoundary.serverError();
            mode = 1;
        }
        ResultSet rs;
        if ((mode == 1) || (mode == 3)) {
            stm.executeUpdate(CREATE_TABLE_DEFAULT); //Inizia il gioco con mappa base;
            rs = stm.executeQuery("SELECT * FROM MAPPA");
        }
        else {
            stm.executeUpdate(CREATE_TABLE_SERVER); //Inizia il gioco con mappa server;
            rs = stm.executeQuery("SELECT * FROM downloaded_mappa");
        }
        while (rs.next()) {
            String desc = rs.getString("desc").replace("\\n", "\n");
            String look = rs.getString("look").replace("\\n", "\n");
            Room room = new Room(rs.getInt("ROOMID"), rs.getString("name"), desc, look, rs.getBoolean("deadly"), rs.getInt("SOUTH"), rs.getInt("NORTH"), rs.getInt("EAST"), rs.getInt("WEST"));
            getRooms().add(room);
        }
        rs.close();
        stm.close();
        conn.close();
        if (mode == 3) {
            loadGame();
        } else setCurrentRoom(getRooms().get(0));
        setTentativi(3);
    }

    /**
     * {@inheritDoc}
     *
     *This method checks if the command is valid. If it is, it is executed.
     */
    @Override
    public void nextMove(ParserOutput p, PrintStream out) throws IOException {
        //move
        boolean noroom = false;
        boolean move = false;
        Room previousRoom = getCurrentRoom();
        if (p.getCommand().getType() == CommandType.NORD) {
            if (getCurrentRoom().getNorth() != -1) {
                setCurrentRoom(getRooms().get(getCurrentRoom().getNorth()));
                move = true;
            } else if (getCurrentRoom().getNorth() == -1) {
                noroom = true;
            } else endGame();
        } else if (p.getCommand().getType() == CommandType.SOUTH) {
            if (getCurrentRoom().getSouth() != -1) {
                setCurrentRoom(getRooms().get(getCurrentRoom().getSouth()));
                move = true;
            } else if (getCurrentRoom().getSouth() == -1) {
                noroom = true;
            } else endGame();
        } else if (p.getCommand().getType() == CommandType.EAST) {
            if (getCurrentRoom().getEast() != -1) {
                setCurrentRoom(getRooms().get(getCurrentRoom().getEast()));
                move = true;
            } else if (getCurrentRoom().getEast() == -1) {
                noroom = true;
            }
        } else if (p.getCommand().getType() == CommandType.WEST) {
            if (getCurrentRoom().getWest() != -1) {
                setCurrentRoom(getRooms().get(getCurrentRoom().getWest()));
                move = true;
            } else if (getCurrentRoom().getWest() == -1) {
                noroom = true;
            }
        }
        //azioni
        if (p.getCommand().getType() == CommandType.LOOK_AT) {
            if (getCurrentRoom().getLook() != null) {
                out.println(getCurrentRoom().getLook());
            } else {
                DungeonBoundary.actionMessages(1);
            }
        } else if (p.getCommand().getType() == CommandType.HELP) {
            //mostra aiuto
            DungeonBoundary.actionMessages(2);
        } else if (p.getCommand().getType() == CommandType.SAVE) {
            StringBuilder save = new StringBuilder();
            save.append(getCurrentRoom().getId()).append(";");
            save.append(TimeAPI.getTime());
            try (PrintWriter output = new PrintWriter("savegame")) {
                output.println(save);
            }
            DungeonBoundary.saveMessages(1);
        } else if (noroom) {
            DungeonBoundary.actionMessages(4);
        } else if (move) {
            if (getCurrentRoom().isDeadly()) {
                end(previousRoom);
            }
            DungeonBoundary.dungeonMessages(getCurrentRoom().getName(), getCurrentRoom().getDescription());
            if ((getCurrentRoom().getWest() == 99) || (getCurrentRoom().getEast() == 99) || (getCurrentRoom().getNorth() == 99) || (getCurrentRoom().getSouth() == 99)) {
                endGame();
            }

        }
    }

    /**
     * This method loads the game to the previous room when you die. It also change attempts number.
     *
     * @param previousRoom a {@link Room} object.
     */
    private void end(Room previousRoom) {
        Random random = new Random();
        int r = random.nextInt(7);
        DungeonBoundary.endMessages(r);
        Scanner sc = new Scanner(System.in);
        boolean end = false;
        do {
            if (getTentativi() > 0) {
                int var = getTentativi();
                DungeonBoundary.retryMessages(1);
                System.out.println("Tentativi: " + (var));
                String s = sc.nextLine();
                if (s.equals("s") || s.equals("S")) {
                    setCurrentRoom(previousRoom);
                    end = true;
                    setTentativi(var - 1);
                } else if (s.equals("n") || s.equals("N")) {
                    DungeonBoundary.retryMessages(2);
                    System.exit(0);
                } else {
                    DungeonBoundary.retryMessages(3);
                }
            }
            else {
                DungeonBoundary.retryMessages(4);
                end = true;
                System.exit(0);
            }
        } while (!end);
    }

    /**
     * This method shows a welcome messages and the users can choose the game mode.
     * @return mapFile, a int which indicates the game mode.
     * @throws IOException if any.
     */
    private int newGame() throws IOException, ServernotAvailableException {
        DungeonBoundary.newGameMessage();
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        int mapFile = 0;
        do {
            switch (command) {
                case "1":
                    mapFile = 1;
                    break;
                case "2":
                    mapFile = 3;
                    break;
                case "3":
                    MapClient.manageMappe("1", "./MiaMappa.txt");
                    mapFile = 4;
                    break;
                case "4":
                    MapClient.manageMappe("2", "./ServerMap.txt");
                    mapFile = 2;
                    break;
                case "5":
                    DungeonBoundary.retryMessages(2);
                    System.exit(0);
            }
        } while (mapFile == 0);
        return mapFile;
    }

    /**
     * This method loads the game from the savegame file.
     */
    private void loadGame() {
        int code = 0;
        String risposta = "0";
        while (!risposta.contains("1") && !risposta.contains("2")) {
            DungeonBoundary.saveMessages(3);
            Scanner scanner = new Scanner(System.in);
            risposta = scanner.nextLine();
            if (risposta.contains("1")) code = 1;
        }
        if (code == 1) {
            try (BufferedReader reader = new BufferedReader(new FileReader("./savegame"))) {
                String line = reader.readLine();
                String[] split = line.split(";");
                int id = Integer.parseInt(split[0]);
                setCurrentRoom(getRooms().get(id));
                DungeonBoundary.loadMessages(split[1]);
            } catch (IOException e) {
                ExceptionsBoundary.fileNotFound();
            }
        }
    }

    /**
     * This method ends the game.
     */
    private void endGame() {
        DungeonBoundary.endMessages(8);
        System.exit(0);
    }
}
