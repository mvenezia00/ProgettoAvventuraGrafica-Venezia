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
import java.sql.*;

/**
 * <p>MapData class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class MapData {

    /**
     * <p>addUser.</p>
     * Method thad checks if the name of the map is already in use.
     *
     * @param username a {@link java.lang.String} object.
     * @throws MapAlreadyExistException if any.
     * @throws java.sql.SQLException if any.
     */
    public synchronized void addName(String username) throws MapAlreadyExistException, SQLException {

        try (Connection conn = DriverManager.getConnection("jdbc:h2:./resources/db/mappaServer")) {
            PreparedStatement ps = conn.prepareStatement("select * from MAPPASERVER where NAME = lower(?)");
            ps.setString(1, username);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            String mappa = null;
            if (rs.next()) {
                mappa = rs.getString("MAPPA");
            }
            if (mappa != null) {
                throw new MapAlreadyExistException();
            }
        }

    }

    /**
     * <p>sendMap.</p>
     * Method used to send the user-generated map to the server.
     *
     * @param name a {@link java.lang.String} object.
     * @param map  a {@link java.lang.String} object.
     * @throws MapAlreadyExistException if any.
     * @throws java.sql.SQLException if any.
     */
    public synchronized void sendMap(String name, String map) throws MapAlreadyExistException, SQLException { //qua carico la mappa suk server
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./resources/db/mappaServer")) {
            addName(name);
            PreparedStatement ps = conn.prepareStatement("insert into MAPPASERVER values(?,?)");
            ps.setString(1, name);
            ps.setString(2, map);
            ps.executeUpdate();
            ps.close();
        }
    }

    /**
     * <p>retrieveMap.</p>
     * Method that retrieves the selected map from the database.
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws MapNotFoundException if any.
     * @throws java.sql.SQLException if any.
     */
    public synchronized String retrieveMap(String name) throws MapNotFoundException, SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./resources/db/mappaServer")) {
            PreparedStatement stm = conn.prepareStatement("select * from MAPPASERVER where NAME = lower(?)");
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            String mappa = null;
            if (rs.next()) {
                mappa = rs.getString("MAPPA");
            }
            if (mappa == null) {
                throw new MapNotFoundException();
            }
            rs.close();
            stm.close();
            return mappa;
        }

    }

}
