/*
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.uniba.app.utility;

import it.uniba.app.boundaries.ExceptionsBoundary;
import it.uniba.app.exceptions.EmptyMapException;
import it.uniba.app.exceptions.ServernotAvailableException;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * <p>MapClient class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class MapClient {

    /*
    * Questo Thread è utilizzato per leggere dall'input stream connesso al socket
    * e stampare la stringa ricevuta sul terminale.
     */
    private static class ClientThread extends Thread {

        private final BufferedReader in;

        private boolean run = true;

        public ClientThread(BufferedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            while (run) {
                try {
                        String map = in.readLine();
                        if (map.contains("#")) System.out.println(map);
                        else {
                            try (PrintWriter out1 = new PrintWriter("ServerMap.txt")) {
                                int semicolon = 0;
                                int ultpos = 0;
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < map.length(); i++) {
                                    if (map.charAt(i) == ';') semicolon++;
                                    if (semicolon == 9 && map.charAt(i) == ';') {
                                        sb.append(map, ultpos, i + 1);
                                        sb.append("\n\r");
                                        semicolon = 0;
                                        ultpos = i + 1;
                                    }
                                }
                                out1.println(sb);
                            }
                            System.out.println("Map received");
                        }
                } catch (SocketException ex) {
                    run = false;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void setRun(boolean run) {
            this.run = run;
        }
    }
    /**
     * <p>manageMappe.</p>
     * Starts the client and can send or retrieve a map from the server.
     *
     * @param args the command line arguments
     * @param path a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     * @throws ServernotAvailableException if any.
     */
    public static void manageMappe(String args, String path) throws IOException, ServernotAvailableException { //carica mappe
        // indirizzo riservato al localhost 127.0.0.1
        InetAddress addr = InetAddress.getByName("localhost");
        // Pone tutto in un blocco try-finally per assicurarsi che
        // il socket sia chiuso:
        try (Socket socket = new Socket(addr, 6666)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Flush automatico con PrintWriter:
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            Scanner scanner = new Scanner(System.in);
            // Crea e avvia il thread per leggere dalla socket:
            String user;
            ClientThread ct = new ClientThread(in);
            ct.start();
            boolean flag = true;
            while (flag) {
                if (args.equals("1")) {
                    System.out.println("Inserisci il nome della tua mappa: ");
                    user = scanner.nextLine();
                    out.println("#send " + user + " " + loadMappe(path));
                } else if (args.equals("2")) {
                    System.out.println("Inserisci il nome della mappa da caricare: ");
                    user = scanner.nextLine();
                    out.println("#receive " + user);
                    //System.out.println(in.readLine());
                }
                flag = false;
            }
            //Termino il Thread che si occupa di stampare i messaggi sul terminale
            ct.setRun(false);
            try {
                ct.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } catch (EmptyMapException e) {
            try (PrintWriter out = new PrintWriter("./MiaMappa.txt")) {
                out.println("ID;DESC;DEAD;SUD;EST;OVEST;NORD;NOME;LOOK; \n\r");
            }
            ExceptionsBoundary.fileCreated();
            ExceptionsBoundary.emptyMapException();
        } catch (ConnectException e) {
                throw new ServernotAvailableException();
        } catch (RuntimeException e) {
            ExceptionsBoundary.serverError();
        }
    }
    /**
     * <p>loadMappe.</p>
     * Method that loads the map from the file and puts into a string.
     *
     * @param path a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws EmptyMapException if any.
     * @throws java.io.IOException if any.
     */
    private static String loadMappe(String path) throws EmptyMapException, IOException { //carica le mappe dal file per inserirle nel server.
        BufferedReader reader;
        StringBuilder mappa = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            mappa = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                mappa.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new EmptyMapException();
        }
        if ((mappa.length() == 0) || (mappa == null) || mappa.equals("ID;DESC;DEAD;SUD;EST;OVEST;NORD;NOME;LOOK;")) {
            throw new EmptyMapException();
        }
        else return mappa.toString();
    }

}
