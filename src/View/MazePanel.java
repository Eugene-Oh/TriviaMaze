package src.View;

import src.Logic.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

public class MazePanel extends JPanel implements ActionListener, KeyListener, PropertyChangeListener {

    /** A generated serial version UID for object Serialization. */
    /**
     * The size of each room.
     */
    private static final int ROOM_SIZE = 45;

    /**
     * Timer for painting.
     */
    private Timer myTimer;

    /**
     * Represents the player in the maze.
     */
    private Player myPlayer;

    /**
     * Logic behind the maze.
     */
    private src.Model.Map myMap;

    /**
     * .
     */
    private Boolean isAtQuestion = false;
    /**
     * .
     */
    private Boolean canPass = true;


    private Boolean noClipActivated = false;



    /**
     * Sets up each component necessary.
     */
    public MazePanel() {
        myTimer = new Timer(25, this);
        myPlayer = new Player();
        myMap = new src.Model.Map();
        addKeyListener(new Movement());
        setFocusable(true);
        myTimer.start();
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
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < myMap.getLength(); i++) {
            for (int j = 0; j < myMap.getHeight(); j++) {
                if (myMap.getElement(i, j) == 0) {
                    g.drawImage(myMap.getGrass(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (myMap.getElement(i, j) == 1) {
                    g.drawImage(myMap.getSand(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (myMap.getElement(i, j) == 2) {
                    g.drawImage(myMap.getWall(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (myMap.getElement(i, j) == 3) {
                    g.drawImage(myMap.getQuestion(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (myMap.getElement(i, j) == 4) {
                    g.drawImage(myMap.getFinish(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                }
            }
        }
        g.drawImage(myMap.getPlayer(), myPlayer.getRoomXCoordinate() * ROOM_SIZE, myPlayer.getRoomYCoordinate() * ROOM_SIZE, null);
//        g.drawString("Current room:", 530, 150);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    public ChangeListener[] getChangeListeners() {
        return listenerList.getListeners(ChangeListener.class);
    }

    protected void fireChangeListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : getChangeListeners()) {
            listener.stateChanged(event);
        }
    }

    public void setCanPass(Boolean canPass) {
        this.canPass = canPass;
    }

    public Boolean getAtQuestion() {
        return isAtQuestion;
    }

    public void removeQuestionRoom() {
        myMap.changeElement(myPlayer.getRoomYCoordinate(), myPlayer.getRoomXCoordinate(), 1);
    }

    public int getRoomXCoordinate() {
        return myPlayer.getRoomXCoordinate();
    }

    public int getRoomYCoordinate() {
        return myPlayer.getRoomYCoordinate();
    }

    /**
     * Sets up the ability for the user to move their player icon and the bounds where the player
     * cannot move to.
     */
    public class Movement extends KeyAdapter {

        /**
         * 0 = GRASS
         * 1 = SAND
         * 2 = WALL
         * 3 = QUESTION
         */
        public void keyPressed(KeyEvent e) {
            // ADD CODE FOR HANDLING QUESTIONS HERE!
            int keycode = e.getKeyCode();
            if (canPass) {
                if (keycode == KeyEvent.VK_W && (!(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() - 1) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() - 1) == 2))) {
                    myPlayer.move(0, -1);
                }
                if (keycode == KeyEvent.VK_S && (!(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() + 1) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() + 1) == 2))) {
                    myPlayer.move(0, 1);
                }
                if (keycode == KeyEvent.VK_A && !(myMap.getMapRoom(myPlayer.getRoomXCoordinate() - 1, myPlayer.getRoomYCoordinate()) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate() - 1, myPlayer.getRoomYCoordinate()) == 2)) {
                    myPlayer.move(-1, 0);
                }
                if (keycode == KeyEvent.VK_D && (!(myMap.getMapRoom(myPlayer.getRoomXCoordinate() + 1, myPlayer.getRoomYCoordinate()) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate() + 1, myPlayer.getRoomYCoordinate()) == 2))) {
                    myPlayer.move(1, 0);
                }
                if (myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate()) == 3) {
                    isAtQuestion = true;
                }
                else {
                    isAtQuestion = false;
                }
            }
            fireChangeListeners();
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

    public Player getPlayer(){
        return myPlayer;
    };

    public void setPlayer(Player thePlayer){
        myPlayer = thePlayer;
    };

    public Boolean getNoClipActivated() {
        return noClipActivated;
    }

    public void setNoClipActivated(Boolean noClipActivated) {
        this.noClipActivated = noClipActivated;
    }

}
