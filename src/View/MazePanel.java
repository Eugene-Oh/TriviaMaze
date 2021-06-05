package src.View;

import src.Logic.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MazePanel extends JPanel implements ActionListener, KeyListener, PropertyChangeListener {

    /**
     * The size of each room.
     */
    private static final int ROOM_SIZE = 45;

    /**
     * The y-coordinate for the widgets below the maze.
     */
    private static final int Y_COORDINATE_INTERFACE = 590;

    /**
     * Timer for painting.
     */
    private Timer myTimer;

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
     * Used for the clock.
     */
    private ScheduledExecutorService executor;

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
     * If the player has reached the finished line.
     */
    private Boolean isFinished;

    /**
     * Coordinates of player sprite.
     */
    private final int[][] spriteSheetCoords = { { 0, 0, 32, 41 }, { 32, 0, 32, 41 }, { 64, 0, 32, 41 },
                                                { 0, 41, 32, 41 }, { 32, 41, 32, 41 }, { 64, 41, 32, 41 },
                                                { 0, 82, 32, 41 }, { 32, 82, 32, 41 }, { 64, 82, 32, 41 },
                                                { 0, 123, 32, 41 }, { 32, 123, 32, 41 }, { 64, 123, 32, 41 }};

    private int spriteValue=0;
    private int spriteStart=0;
    private int spriteEnd=11;
    private Boolean shouldAnimate=false;
    BufferedImage img=null;

    /**
     * Sets up each component necessary.
     */
    public MazePanel() {
        isFinished = false;
        myTimer = new Timer(25, this);
        myPlayer = new Player();
        myMap = new src.Model.Map();
        addKeyListener(new Movement());
        setFocusable(true);
        myTimer.start();
        myClockSeconds1 = myPlayer.getSecond1();
        myClockSeconds1 = myPlayer.getSecond2();
        myClockMinute1 = myPlayer.getMinute1();
        myClockMinute2 = myPlayer.getMinute2();
        clock();

        try {
            File yasuoFile =new File("TriviaMaze\\src\\Sprites\\yasuo.png");
            if (!yasuoFile.isFile()){
                yasuoFile = new File("./src/Sprites/yasuo.png");
            }
            img = ImageIO.read(yasuoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer(25, actionListener);
        timer.setInitialDelay(0);
        timer.start();
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

        Image subSprite = img.getSubimage(spriteSheetCoords[spriteValue][0], spriteSheetCoords[spriteValue][1], spriteSheetCoords[spriteValue][2], spriteSheetCoords[spriteValue][3]);
        g.drawImage(subSprite, myPlayer.getRoomXCoordinate() * ROOM_SIZE+6, myPlayer.getRoomYCoordinate() * ROOM_SIZE, null);

        // Creates the current room interface.
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("Current room:", 40, Y_COORDINATE_INTERFACE);
        if (myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate()) == 1) {
            g.drawImage(myMap.getSand(), 200, Y_COORDINATE_INTERFACE - 30, this);
        } else if (myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate()) == 3) {
            g.drawImage(myMap.getQuestion(), 200, Y_COORDINATE_INTERFACE - 30, this);
        } else if (myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate()) == 4) {
            g.drawImage(myMap.getFinish(), 200, Y_COORDINATE_INTERFACE - 30, this);
        }

        // Creates the timer.
        g.drawString("Elapsed Time:", 300, Y_COORDINATE_INTERFACE);
        g.drawString(String.valueOf(myClockSeconds1), 514, Y_COORDINATE_INTERFACE);
        g.drawString(String.valueOf(myClockSeconds2), 500, Y_COORDINATE_INTERFACE);
        g.drawString(":", 495, Y_COORDINATE_INTERFACE);
        g.drawString(String.valueOf(myClockMinute1), 482, Y_COORDINATE_INTERFACE);
        g.drawString(String.valueOf(myClockMinute2), 468, Y_COORDINATE_INTERFACE);
    }

    public void clock() {
        executor = Executors.newScheduledThreadPool(1);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                myClockSeconds1 = myClockSeconds1 + 1;
                if (myClockSeconds1 > 9) {
                    myClockSeconds1 = 0;
                    myClockSeconds2 = myClockSeconds2 + 1;
                }
                if (myClockSeconds2 > 5) {
                    myClockSeconds1 = 0;
                    myClockSeconds2 = 0;
                    myClockMinute1 = myClockMinute1 + 1;
                }
                if (myClockMinute1 > 9) {
                    myClockMinute1 = 0;
                    myClockMinute2 = myClockMinute2 + 1;
                }
                if (myClockMinute2 > 5) {
                    myClockMinute1 = 0;
                    myClockMinute2 = 0;
                }
            }
        };
        executor.scheduleAtFixedRate(run, 0, 1, TimeUnit.SECONDS);
        myPlayer.setClock(myClockSeconds1, myClockSeconds2, myClockMinute1, myClockMinute2);
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
            int keycode = e.getKeyCode();
            if (canPass && !isFinished) {
                if (keycode == KeyEvent.VK_W && (!(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() - 1) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() - 1) == 2))) {
                    animatePlayer(0, -1);
                }
                if (keycode == KeyEvent.VK_S && (!(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() + 1) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() + 1) == 2))) {
                    animatePlayer(0, 1);
                }
                if (keycode == KeyEvent.VK_A && !(myMap.getMapRoom(myPlayer.getRoomXCoordinate() - 1, myPlayer.getRoomYCoordinate()) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate() - 1, myPlayer.getRoomYCoordinate()) == 2)) {
                    animatePlayer(-1, 0);
                }
                if (keycode == KeyEvent.VK_D && (!(myMap.getMapRoom(myPlayer.getRoomXCoordinate() + 1, myPlayer.getRoomYCoordinate()) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate() + 1, myPlayer.getRoomYCoordinate()) == 2))) {
                    animatePlayer(1, 0);
                }
                if (myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate()) == 3) {
                    isAtQuestion = true;
                }
                else {
                    isAtQuestion = false;
                }
                spriteValue = spriteStart;
                // If at finish line.
                if (myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate()) == 4) {
                    executor.shutdown();
                    isFinished = true;
                    final JOptionPane aboutPane = new JOptionPane();
                    aboutPane.showMessageDialog(new JFrame(), "You have finished the maze!",
                            "Finished", JOptionPane.INFORMATION_MESSAGE);
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

    public Player getPlayer() {
        return myPlayer;
    }

    public void setPlayer(Player thePlayer) {
        myPlayer = thePlayer;
    }

    public Boolean getNoClipActivated() {
        return noClipActivated;
    }

    public void setNoClipActivated(Boolean noClipActivated) {
        this.noClipActivated = noClipActivated;
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(shouldAnimate){
                if (spriteValue == spriteEnd) {
                    spriteValue = spriteStart;
                }
                spriteValue = spriteValue+1;
                shouldAnimate=false;
            }else{
                spriteValue=1;
            }

            revalidate();
            repaint();
        }
    };

    private void animatePlayer(int x, int y){
        if(x==0 && y==1){
            spriteStart=0;
            spriteEnd=2;
        }else if(x==-1 && y==0){
            spriteStart=3;
            spriteEnd=5;
        }else if(x==1 && y==0){
            spriteStart=6;
            spriteEnd=8;
        }else if(x==0&& y==-1){
            spriteStart=9;
            spriteEnd=11;
        }
        shouldAnimate=true;
        myPlayer.move(x, y);
    }
}
