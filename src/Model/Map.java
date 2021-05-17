
package Model; /**
 * TCSS 360
 */

/**
 * A class representing the Maze on the Maze Room Program.
 *
 * @author Eugene Oh
 * @version 4.0
 */

public class Map {

    /**
     * The default initial size for a new map.
     */
    private static final int DEFAULT_SIZE = 10;

    /**
     * 2-D array representing the map.
     */
    final private int myMap[][];

    /**
     * The height of the map.
     */
    final private int myHeight;

    /**
     * The length of the map.
     */
    final private int myLength;

    /**
     * The x-coordinate of the current room the player is on.
     */
    private int currentX;

    /**
     * The y-coordinate of the current room the player is on.
     */
    private int currentY;

    /**
     * Constructs a new map using default values.
     */
    public Map() {
        myHeight = DEFAULT_SIZE;
        myLength = DEFAULT_SIZE;
        myMap = new int[DEFAULT_SIZE][DEFAULT_SIZE];
        currentX = 0;
        currentY = 0;
    }

    /**
     * Constructs a new map based off given values.
     *
     * @param theHeight Height of the map.
     * @param theLength Length of the map.
     */
    public Map(final int theHeight, final int theLength) {
        myHeight = theHeight;
        myLength = theLength;
        myMap = new int[myHeight][myLength];
        currentX = 0;
        currentY = 0;
    }

    /**
     * Setter method for the player's current coordinates.
     *
     * @param theX The x-coordinate of the player.
     * @param theY The y-coordinate of the player.
     */
    public void setCoordinates(final int theX, final int theY) {
        currentX = theX;
        currentY = theY;
    }

    /**
     * Returns the 2-D array representing the map.
     *
     * @return The map.
     */
    public int[][] getMap() {
        return myMap;
    }

    /**
     * Returns the length of the map.
     *
     * @return The length.
     */
    public int getLength() {
        return myLength;
    }

    /**
     * Returns the height of the map.
     *
     * @return The height.
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * Returns the player's current x-coordinate.
     *
     * @return The x-coordinatex
     */
    public int getX() {
        return currentX;
    }

    /**
     * Returns the player's current y-coordinate.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return currentY;
    }
    }
