package it.uniba.app.parser;

import it.uniba.app.command.Command;

/**
 * <p>ParserOutput class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class ParserOutput {

    private final Command command;


    /**
     * <p>Constructor for ParserOutput.</p>
     *
     * @param command a {@link Command} object.
     */
    public ParserOutput(Command command) {
        this.command = command;
    }
    /**
     * <p>Getter for the field <code>command</code>.</p>
     *
     * @return a {@link Command} object.
     */
    public Command getCommand() {
        return command;
    }


}
