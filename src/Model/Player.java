package src.Model;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * A class representing the player within a Maze.
 *
 * @author Eugene Oh, Jonathan Cho, Yavuzalp Turkoglu
 * @version Spring 2021
 */
public class Player implements Serializable {

    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = 4874303903866067117L;

    /**
     * Starting room for the player.
     */
    private static final int PLAYER_START = 1;

    /**
     * Current room X and Y-Coordinate.
     */
    private int roomX, roomY;

    /**
     * Represents a second in the GUI.
     */
    private int myClockSeconds1;

    /**
     * Represents the next digit in seconds in the GUI.
     */
    private int myClockSeconds2;

    /**
     * Represents a minute in the GUI.
     */
    private int myClockMinute1;

    /**
     * Represents the next digit in minutes in the GUI.
     */
    private int myClockMinute2;


    /**
     * Default constructor.
     */
    public Player() {
        roomX = PLAYER_START;
        roomY = PLAYER_START;
        myClockSeconds1 = 0;
        myClockSeconds1 = 0;
        myClockMinute1 = 0;
        myClockMinute2 = 0;
    }

    /**
     * Getter for Room's X Coordinate.
     *
     * @return roomX The X coordinate for the room the player is currently in.
     */
    public int getRoomXCoordinate() {
        return roomX;
    }

    /**
     * Getter for Room's Y Coordinate.
     *
     * @return roomX The Y coordinate for the room the player is currently in.
     */
    public int getRoomYCoordinate() {
        return roomY;
    }

    /**
     * Setter/Sets the clock.
     *
     * @param theSecond1 First value that represents a second of the clock.
     * @param theSecond2 Second value that represents a second of the clock.
     * @param theMinute1 First value that represents a minute of the clock.
     * @param theMinute2 Second value that represents a minute of the clock.
     */
    public void setClock(int theSecond1, int theSecond2, int theMinute1, int theMinute2) {
        myClockSeconds1 = theSecond1;
        myClockSeconds2 = theSecond2;
        myClockMinute1 = theMinute1;
        myClockMinute2 = theMinute2;
    }

    /**
     * Getter for Second 1.
     *
     * @return First value that represents a second of the clock.
     */
    public int getSecond1() {
        return myClockSeconds1;
    }

    /**
     * Getter for Second 2.
     *
     * @return Second value that represents a seconds of the clock.
     */
    public int getSecond2() {
        return myClockSeconds2;
    }

    /**
     * Getter for Minute 1.
     *
     * @return First value that represents a minutes of the clock.
     */
    public int getMinute1() {
        return myClockMinute1;
    }

    /**
     * Getter for Second 2.
     *
     * @return Second value that represents a minutes of the clock.
     */
    public int getMinute2() {
        return myClockMinute2;
    }

    /**
     * Moves the player.
     *
     * @param dx directional x value.
     * @param dy directional y value.
     */
    public void move(int dx, int dy) {
        roomX += dx;
        roomY += dy;
    }
}
