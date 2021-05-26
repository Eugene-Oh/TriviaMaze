
package src.Model; /**
 * TCSS 360
 */

import javax.swing.*;
import java.awt.*;

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
    private static final int MAZE_SIZE = 12;

    /**
     * The default size for each room.
     */
    private static final int ROOM_SIZE = 45;


    /**
     * 2-D array representing the map.
     */
    final private int myMap[][] = { { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                                    { 2, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 2},
                                    { 2, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 2 },
                                    { 2, 0, 1, 1, 2, 0, 1, 0, 0, 0, 1, 2 },
                                    { 2, 0, 0, 0, 1, 0, 2, 1, 2, 0, 1, 2 },
                                    { 2, 1, 2, 1, 1, 0, 1, 0, 1, 0, 0, 2 },
                                    { 2, 0, 1, 0, 0, 0, 1, 0, 1, 1, 2, 2 },
                                    { 2, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 2 },
                                    { 2, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 2 },
                                    { 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 2 },
                                    { 2, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2 },
                                    { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}};

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

    private Image grass;

    private Image sand;

    private Image wall;

    /**
     * Constructs a new map using default values.
     */
    public Map() {
        ImageIcon img = new ImageIcon("TriviaMaze\\src\\Sprites\\grass.png");
        Image grassImage = img.getImage();
        grass = grassImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE,  java.awt.Image.SCALE_SMOOTH);

        img = new ImageIcon("TriviaMaze\\src\\Sprites\\sand.png");
        Image sandImage = img.getImage();
        sand = sandImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE,  java.awt.Image.SCALE_SMOOTH);

        img = new ImageIcon("TriviaMaze\\src\\Sprites\\wall.png");
        Image wallImage = img.getImage();
        wall = wallImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE,  java.awt.Image.SCALE_SMOOTH);


        myHeight = MAZE_SIZE;
        myLength = MAZE_SIZE;
//        myMap = new int[DEFAULT_SIZE][DEFAULT_SIZE];
        currentX = 0;
        currentY = 0;
    }

    public Image getGrass(){
        return grass;
    }

    public Image getSand(){
        return sand;
    }

    public Image getWall(){
        return wall;
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
    public int getMapRoom(int x, int y) {
        return myMap[y][x];
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

    /**
     * Returns the element in the designated spot in the array.
     *
     * @return The element in the array.
     */
    public int getElement(int y, int x) {
        return myMap[y][x];
    }
}