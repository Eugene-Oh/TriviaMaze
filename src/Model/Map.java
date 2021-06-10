package src.Model;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * A class representing the logic behind the maze for the Maze Room Program.
 *
 * @author Eugene Oh, Yavuzalp Turkoglu, Jonathan Cho
 * @version Spring 2021
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
     * 0 = FIRE BLOCK
     * 1 = WATER
     * 2 = WALL
     * 3 = QUESTION ROOM
     * 4 = FINISH
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
     * Image for the grass of the maze.
     */
    private Image myFireBlock;

    /**
     * Image for the sand of the maze.
     */
    private Image myWater;

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
     * Constructs the images for each block on the maze and the player.
     */
    public Map() {
        ImageIcon img = new ImageIcon("TriviaMaze\\src\\Sprites\\difRoad.png");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/difRoad.png");
        }
        Image grassImage = img.getImage();
        myFireBlock = grassImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE, java.awt.Image.SCALE_SMOOTH);

        img = new ImageIcon("TriviaMaze\\src\\Sprites\\blue.png");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/blue.png");
        }
        Image sandImage = img.getImage();
        myWater = sandImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE, java.awt.Image.SCALE_SMOOTH);

        img = new ImageIcon("TriviaMaze\\src\\Sprites\\smallRoad.png");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/smallRoad.png");
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

    }

    /**
     * Getter for the image of the fire block.
     *
     * @return Image of fire block.
     */
    public Image getFireBlock() {
        return myFireBlock;
    }

    /**
     * Getter for the image of the water.
     *
     * @return Image of water.
     */
    public Image getWater() {
        return myWater;
    }

    /**
     * Getter for the image of the wall.
     *
     * @return Image of wall.
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
     * Getter for the size of the maze.
     *
     * @return Integer of maze size.
     */
    public static int getMazeSize() {
        return MAZE_SIZE;
    }

    /**
     * Returns an element within the maze using (x,y).
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The element in the array.
     */
    public int getMapRoom(int x, int y) {
        return myMap[y][x];
    }

    /**
     * Returns the element in the designated spot in the array at (y,x).
     *
     * @param y The y-coordinate.
     * @param x The x-coordinate.
     * @return The element in the array.
     */
    public int getElement(int y, int x) {
        return myMap[y][x];
    }

    /**
     * Changes the element in the array.
     *
     * @param y The y-coordinate.
     * @param x The x-coordinate.
     * @param roomValue An integer value that the room should be changed to.
     */
    public void changeElement(int y, int x, int roomValue) {
        myMap[y][x] = roomValue;
    }
}