package src.View;

import src.Logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MazePanel extends JPanel implements ActionListener, KeyListener {

    /** The size of each room. */
    private static final int ROOM_SIZE = 45;

    private Timer t;
    private Player p;
    private src.Model.Map m;


    /**
     * Sets up each component necessary.
     */
    public MazePanel(){
        t = new Timer(25, this);
        p = new Player();
        m = new src.Model.Map();
        addKeyListener(new Movement());
        setFocusable(true);
        t.start();
    }

    /**
     * Repaints the panel each time a player moves.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    /**
     * Draws each maze component onto the GUI.
     */
    public void paint(Graphics g){
        super.paint(g);
        for (int i = 0; i < m.getLength(); i++) {
            for (int j = 0; j < m.getHeight(); j++) {
                if (m.getElement(i, j) == 0) {
                    g.drawImage(m.getGrass(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (m.getElement(i, j) == 1) {
                    g.drawImage(m.getSand(),j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (m.getElement(i, j) == 2) {
                    g.drawImage(m.getWall(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                }
            }
        }
        g.drawImage(p.getPlayer(), p.getRoomXCoordinate() * ROOM_SIZE, p.getRoomYCoordinate() * ROOM_SIZE, null);
    }

    /**
     * Sets up the ability for the user to move their player icon.
     */
    public class Movement extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            if (keycode == KeyEvent.VK_W && (!(m.getMapRoom(p.getRoomXCoordinate(), p.getRoomYCoordinate() - 1) == 0) &&
                    !(m.getMapRoom(p.getRoomXCoordinate(), p.getRoomYCoordinate() - 1) == 2))) {
                p.move(0, -1);
            }
            if (keycode == KeyEvent.VK_S && (!(m.getMapRoom(p.getRoomXCoordinate(), p.getRoomYCoordinate() + 1) == 0) &&
                    !(m.getMapRoom(p.getRoomXCoordinate(), p.getRoomYCoordinate() + 1) == 2))) {
                p.move(0, 1);
            }
            if (keycode == KeyEvent.VK_A && !(m.getMapRoom(p.getRoomXCoordinate() - 1, p.getRoomYCoordinate()) == 0) &&
                    !(m.getMapRoom(p.getRoomXCoordinate() - 1, p.getRoomYCoordinate()) == 2)) {
                p.move(-1, 0);
            }
            if (keycode == KeyEvent.VK_D && (!(m.getMapRoom(p.getRoomXCoordinate() + 1, p.getRoomYCoordinate()) == 0) &&
                    !(m.getMapRoom(p.getRoomXCoordinate() + 1, p.getRoomYCoordinate()) == 2))) {
                p.move(1, 0);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
