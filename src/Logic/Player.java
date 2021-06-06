package src.Logic;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Player implements Serializable {

    /** A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 4874303903866067117L;

    /** The size of each room. */
    private static final int ROOM_SIZE = 45;

    /** Starting room for the player. */
    private static final int PLAYER_START = 1;

    /** Current room X and Y-Coordinate. */
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


    public Player() {
        roomX = PLAYER_START;
        roomY = PLAYER_START;
        myClockSeconds1 = 0;
        myClockSeconds1 = 0;
        myClockMinute1 = 0;
        myClockMinute2 = 0;
    }


    public int getRoomXCoordinate() {
        return roomX;
    }

    public int getRoomYCoordinate() {
        return roomY;
    }

    public void setClock (int theSecond1, int theSecond2, int theMinute1, int theMinute2) {
        myClockSeconds1 = theSecond1;
        myClockSeconds2 = theSecond2;
        myClockMinute1 = theMinute1;
        myClockMinute2 = theMinute2;
    }

    public int getSecond1() {
        return myClockSeconds1;
    }

    public int getSecond2() {
        return myClockSeconds2;
    }

    public int getMinute1() {
        return myClockMinute1;
    }

    public int getMinute2() {
        return myClockMinute2;
    }

    public void move(int dx, int dy) {
        roomX += dx;
        roomY += dy;
    }
}
