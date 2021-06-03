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


    public Player() {


        roomX = PLAYER_START;
        roomY = PLAYER_START;
    }


    public int getRoomXCoordinate() {
        return roomX;
    }

    public int getRoomYCoordinate() {
        return roomY;
    }

    public void move(int dx, int dy) {
        roomX += dx;
        roomY += dy;
    }
}
