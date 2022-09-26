package it.uniba.app.avventura;

import it.uniba.app.parser.ParserOutput;
import it.uniba.app.command.Command;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Abstract GameDescription class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public abstract class GameDescription {

    private final List<Room> rooms = new ArrayList<>();

    private final List<Command> commands = new ArrayList<>();

    private Room currentRoom;

    /**
     * <p>Getter for the field <code>rooms</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * <p>Getter for the field <code>commands</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * <p>Getter for the field <code>currentRoom</code>.</p>
     *
     * @return a {@link Room} object.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * <p>Setter for the field <code>currentRoom</code>.</p>
     *
     * @param currentRoom a {@link Room} object.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    private int tentativi;

    /**
     * <p>Getter for the field <code>tentativi</code>.</p>
     *
     * @return a int.
     */
    public int getTentativi() {
        return tentativi;
    }

    /**
     * <p>Setter for the field <code>tentativi</code>.</p>
     *
     * @param tentativi a int.
     */
    public void setTentativi(int tentativi) {
        this.tentativi = tentativi;
    }


    /**
     * <p>init.</p>
     *
     * @throws java.lang.Exception if any.
     */
    public abstract void init() throws Exception;

    /**
     * <p>nextMove.</p>
     *
     * @param p a {@link ParserOutput} object.
     * @param out a {@link java.io.PrintStream} object.
     * @throws java.io.IOException if any.
     */
    public abstract void nextMove(ParserOutput p, PrintStream out) throws IOException;

}
