package src.Logic;

import javax.swing.*;
import java.awt.*;

public class Player extends Rectangle {

    /** The size of each room. */
    private static final int ROOM_SIZE = 45;

    private int roomX, roomY;

    private Image player;

    public Player() {
        ImageIcon img = new ImageIcon("TriviaMaze\\src\\Sprites\\player.png");
        Image playerImage = img.getImage();
        player = playerImage.getScaledInstance(ROOM_SIZE, ROOM_SIZE,  java.awt.Image.SCALE_SMOOTH);

        roomX = 1;
        roomY = 1;
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
