package src.Logic;

import javax.swing.*;
import java.awt.*;

public class Player extends Rectangle {

    /** The size of each room. */
    private static final int ROOM_SIZE = 45;

    /** Starting room for the player. */
    private static final int PLAYER_START = 1;

    /** Current room X and Y-Coordinate. */
    private int roomX, roomY;

    private Image player;

    public Player() {
        ImageIcon img = new ImageIcon("TriviaMaze\\src\\Sprites\\player.png");
        if (img.getIconHeight()==-1){
            img = new ImageIcon("./src/Sprites/player.png");
        }
        Image playerImage = img.getImage();
        player = playerImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE,  java.awt.Image.SCALE_SMOOTH);

        roomX = PLAYER_START;
        roomY = PLAYER_START;
    }

    public Image getPlayer() {
        return player;
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
