package it.uniba.app.command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Command class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class Command {

    private final CommandType type;

    private final String name;

    private Set<String> alias;

    /**
     * <p>Constructor for Command.</p>
     *
     * @param type a {@link CommandType} object.
     * @param name a {@link java.lang.String} object.
     */
    public Command(CommandType type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Getter for the field <code>alias</code>.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    public Set<String> getAlias() {
        return alias;
    }

    /**
     * <p>Setter for the field <code>alias</code>.</p>
     *
     * @param alias an array of {@link java.lang.String} objects.
     */
    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link CommandType} object.
     */
    public CommandType getType() {
        return type;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.type);
        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Command other = (Command) obj;
        return this.type == other.type;
    }

}
