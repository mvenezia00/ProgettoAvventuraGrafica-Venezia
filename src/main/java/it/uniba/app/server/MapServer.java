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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * <p>MapServer class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class MapServer {

    /**
     * <p>main.</p>
     * Method that starts the server.
     *
     * @param args the command line arguments
     * @throws java.io.IOException if any.
     * @throws java.sql.SQLException if any.
     */
    public static void main(String[] args) throws IOException, SQLException {
        MapData md = new MapData();
        ServerSocket s = new ServerSocket(6666);
        System.out.println("Started: " + s);
        final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS mappaServer (NAME VARCHAR(120), MAPPA VARCHAR)";
        Connection conn = DriverManager.getConnection("jdbc:h2:./resources/db/mappaServer");
        Statement stm = conn.createStatement();
        stm.executeUpdate(CREATE_TABLE);
        try {
            while (true) {
                Socket socket = s.accept();
                //UUID è utilizzato per creare un id univoco da utilizzare come nome del Thread
                Thread t = new MapServerThread(socket, md, UUID.randomUUID().toString());
                t.start();
            }
        } finally {
            s.close();
        }
    }

}
