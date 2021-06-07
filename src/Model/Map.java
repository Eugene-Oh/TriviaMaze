
package src.Model;
/**
 * TCSS 360
 */

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * A class representing the Maze on the Maze Room Program.
 *
 * @author Eugene Oh
 * @version 4.0
 */

public class Map implements Serializable {

    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = 4624303903866067117L;

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
     * 0 = GRASS
     * 1 = SAND
     * 2 = WALL
     * 3 = Question Room
     * 4 = Finish
     */
    private int myMap[][] = {{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 2},
            {2, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 2},
            {2, 0, 1, 1, 3, 0, 1, 0, 0, 0, 1, 2},
            {2, 0, 0, 0, 1, 0, 3, 1, 3, 0, 1, 2},
            {2, 1, 3, 1, 1, 0, 1, 0, 1, 0, 0, 2},
            {2, 0, 1, 0, 0, 0, 1, 0, 1, 1, 3, 2},
            {2, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 2},
            {2, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 2},
            {2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 1, 1, 3, 1, 1, 1, 1, 1, 4, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}};

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
     * Image for the grass of the maze.
     */
    private Image myGrass;
    /**
     * Image for the sand of the maze.
     */
    private Image mySand;
    /**
     * Image for the walls of the maze.
     */
    private Image myWall;
    /**
     * Image for the question blocks of the maze.
     */
    private Image myQuestion;
    /**
     * Image for the finish block of the maze.
     */
    private Image myFinish;
    /**
     * Image for the player of the maze.
     */
    private Image player;

    /**
     * Constructs a new map using default values.
     */
    public Map() {
        ImageIcon img = new ImageIcon("TriviaMaze\\src\\Sprites\\grass.png");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/grass.png");
        }
        Image grassImage = img.getImage();
        myGrass = grassImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE, java.awt.Image.SCALE_SMOOTH);

        img = new ImageIcon("TriviaMaze\\src\\Sprites\\sand.png");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/sand.png");
        }
        Image sandImage = img.getImage();
        mySand = sandImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE, java.awt.Image.SCALE_SMOOTH);

        img = new ImageIcon("TriviaMaze\\src\\Sprites\\wall.png");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/wall.png");
        }
        Image wallImage = img.getImage();
        myWall = wallImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE, java.awt.Image.SCALE_SMOOTH);

        img = new ImageIcon("TriviaMaze\\src\\Sprites\\question.png");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/question.png");
        }
        Image questionImage = img.getImage();
        myQuestion = questionImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE, java.awt.Image.SCALE_SMOOTH);

        img = new ImageIcon("TriviaMaze\\src\\Sprites\\finish.PNG");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/finish.PNG");
        }
        Image finishImage = img.getImage();
        myFinish = finishImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE, java.awt.Image.SCALE_SMOOTH);

        img = new ImageIcon("TriviaMaze\\src\\Sprites\\yasuo.png");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/yasuo.png");
        }
        Image playerImage = img.getImage();
        player = playerImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE, java.awt.Image.SCALE_SMOOTH);


        myHeight = MAZE_SIZE;
        myLength = MAZE_SIZE;
//        myMap = new int[DEFAULT_SIZE][DEFAULT_SIZE];
        currentX = 0;
        currentY = 0;
    }

    /**
     * Getter for the image of the Grass.
     *
     * @return Image of grass.
     */
    public Image getGrass() {
        return myGrass;
    }

    /**
     * Getter for the image of the Sand.
     *
     * @return Image of Sand.
     */
    public Image getSand() {
        return mySand;
    }

    /**
     * Getter for the image of the Wall.
     *
     * @return Image of Wall.
     */
    public Image getWall() {
        return myWall;
    }

    /**
     * Getter for the question.
     *
     * @return question.
     */
    public Image getQuestion() {
        return myQuestion;
    }

    /**
     * Getter for image of the finish line.
     *
     * @return Image of finish line.
     */
    public Image getFinish() {
        return myFinish;
    }

    /**
     * Getter for image of the player.
     *
     * @return Image of player.
     */
    public Image getPlayer() {
        return player;
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

    /**
     * Changes the element.
     *
     * @param y         value.
     * @param x         value.
     * @param roomValue value for the room.
     */
    public void changeElement(int y, int x, int roomValue) {
        myMap[y][x] = roomValue;
//        System.out.println(myMap[y][x]);
    }
}