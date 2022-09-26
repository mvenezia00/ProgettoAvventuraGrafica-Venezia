package it.uniba.app.avventura;

/**
 * <p>Room class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class Room {
    /**
     * Contains the ID of the room.
     */
    private final int id;
    /**
     * Contains the name of the room.
     */
    private final String name;
    /**
     * Contains the description of the room.
     */
    private final String description;
    /**
     * Contains the look command description of the room.
     */
    private final String look;
    /**
     * Tells if the room is deadly.
     */
    private final boolean deadly;
    /**
     * Contains the ID of the south room.
     */
    private final int south;
    /**
     * Contains the ID of the north room.
     */
    private final int north;
    /**
     * Contains the ID of the east room.
     */
    private final int east;
    /**
     * Contains the ID of the west room.
     */
    private final int west;

    /**
     * <p>Constructor for Room.</p>
     *
     * @param id a int.
     * @param name a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @param look a {@link java.lang.String} object.
     * @param deadly a boolean.
     * @param south a int.
     * @param north a int.
     * @param east a int.
     * @param west a int.
     */
    public Room(int id, String name, String description, String look, boolean deadly, int south, int north, int east, int west) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.look = look;
        this.deadly = deadly;
        this.south = south;
        this.north = north;
        this.east = east;
        this.west = west;
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
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Getter for the field <code>look</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getLook() { return look;}

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a int.
     */
    public int getId() {return id;}

    /**
     * <p>isDeadly.</p>
     *
     * @return a boolean.
     */
    public boolean isDeadly() {
        return deadly;
    }


    /**
     * <p>Getter for the field <code>south</code>.</p>
     *
     * @return a int.
     */
    public int getSouth() {
        return south;
    }


    /**
     * <p>Getter for the field <code>north</code>.</p>
     *
     * @return a int.
     */
    public int getNorth() {
        return north;
    }


    /**
     * <p>Getter for the field <code>east</code>.</p>
     *
     * @return a int.
     */
    public int getEast() {
        return east;
    }


    /**
     * <p>Getter for the field <code>west</code>.</p>
     *
     * @return a int.
     */
    public int getWest() {
        return west;
    }
    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
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
        final Room other = (Room) obj;
        return this.id == other.id;
    }

}
