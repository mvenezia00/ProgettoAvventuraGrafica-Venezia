/*
 * Copyright (C) 2020
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
package it.uniba.app.server;

import it.uniba.app.exceptions.MapAlreadyExistException;
import it.uniba.app.exceptions.MapNotFoundException;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>MapServerThread class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class MapServerThread extends Thread {

    private final Socket socket;
    private final MapData md;
    private boolean run = true;
    private PrintWriter out = null;

    /**
     * <p>Constructor for MapServerThread.</p>
     *
     * @param socket a {@link java.net.Socket} object.
     * @param md     a {@link MapData} object.
     */
    public MapServerThread(Socket socket, MapData md) {
        this.socket = socket;
        this.md = md;
    }

    /**
     * <p>Constructor for MapServerThread.</p>
     *
     * @param socket a {@link java.net.Socket} object.
     * @param md     a {@link MapData} object.
     * @param name   a {@link java.lang.String} object.
     */
    public MapServerThread(Socket socket, MapData md, String name) {
        this(socket, md);
        this.setName(name);
    }

    /**
     * {@inheritDoc}
     * Prepare the socket for the communication with the client.
     */
    @Override
    public void run() {
        try {
            System.out.println("Connessione accettata: " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            while (run) {
                String str = in.readLine();
                if (str != null) {
                    str = str.trim();
                    Pattern pattern = Pattern.compile("\\S+");
                    Matcher matcher = pattern.matcher(str);
                    boolean findcmd = matcher.find();
                    if (findcmd && matcher.group().equalsIgnoreCase("#name")) {
                        if (matcher.find()) {
                            String name = matcher.group();
                            try {
                                md.addName(name);
                                out.println("#ok");
                            } catch (Exception ex) {
                                out.println("#error " + ex.getMessage());
                            }
                        }
                    } else if (findcmd && matcher.group().equalsIgnoreCase("#send")) {
                        String name = null;
                        String msg = null;
                        if (matcher.find()) {
                            name = matcher.group();
                            if (matcher.end() < str.length()) {
                                msg = str.substring(matcher.end()).trim();
                            }
                        }
                        if (name != null && msg != null) {
                            try {
                                md.sendMap(name, msg); //inserimento in database
                                out.println("#ok");
                            } catch (MapAlreadyExistException ex) {
                                out.println("#Errore, il nome della mappa è già stato utilizzato.");
                            } finally {
                                run = false;
                            }
                        }
                    } else if (findcmd && matcher.group().equalsIgnoreCase("#receive")) {
                        String name = null;
                        if (matcher.find()) {
                            name = matcher.group();
                        }
                        try {
                            if (name != null) {
                                out.println(md.retrieveMap(name));
                            }
                        } catch (MapNotFoundException ex) {
                            out.println("#Errore, mappa non trovata. ");
                        } finally {
                            run = false;
                        }
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            out.println("#Errore, " + e.getMessage());
        } finally {
            System.out.println("closing...");
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
