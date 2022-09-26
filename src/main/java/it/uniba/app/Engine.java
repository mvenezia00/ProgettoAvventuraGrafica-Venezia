package it.uniba.app;

import it.uniba.app.avventura.GameDescription;
import it.uniba.app.avventura.Music;
import it.uniba.app.boundaries.EngineBoundary;
import it.uniba.app.exceptions.CommandNotExistException;
import it.uniba.app.command.CommandType;
import it.uniba.app.gioco.Dungeon;
import it.uniba.app.parser.Parser;
import it.uniba.app.parser.ParserOutput;
import it.uniba.app.utility.FileCleaner;
import it.uniba.app.utility.Utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

/**
 * <p>Engine class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class Engine {

    private final GameDescription game;

    private final Parser parser;

    /**
     * <p>Constructor for Engine.</p>
     *
     * @param game a {@link GameDescription} object.
     * @throws java.lang.Exception if any.
     */
    public Engine(GameDescription game) throws Exception {
        this.game = game;
        try {
            FileCleaner.clean();
            this.game.init();
        } catch (SQLException e) {
            //System.out.println("Errore: " + e.getMessage());
        } catch (Exception ex) {
            System.out.println();
        }
        try {
            Set<String> stopwords = Utils.loadFileListInSet(new File("./resources/stopwords"));
            parser = new Parser(stopwords);
        } catch (IOException ex) {
            throw new IOException();
        }
    }
    /**
     * <p>execute.</p>
     */
    public void execute() {
            if (EngineBoundary.get_welcome_message(game) == 1) {
                try {
                    this.game.init();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                try {
                    String command = scanner.nextLine();
                    ParserOutput p = parser.parse(command, game.getCommands());
                    if (p == null || p.getCommand() == null) {
                        throw new CommandNotExistException();
                    } else if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {
                        EngineBoundary.exit();
                        System.exit(0);
                    } else {
                        game.nextMove(p, System.out);
                        System.out.println();
                    }
                } catch (CommandNotExistException e) {
                    EngineBoundary.command_not_exist();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    /**
     * <p>main.</p>
     *
     * @param args the command line arguments
     */
    public static void main (String[] args)  {
        try {
            Thread thread1 = new Thread(new Music());
            thread1.start();
            new Thread (() -> {
                try {
                    new Engine(new Dungeon()).execute();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
