package src.View;

import src.Model.Player;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
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

/**
 * A class that handles the creates the maze in the actual GUI.
 *
 * @author Eugene Oh, Jonathan Cho, Yavuzalp Turkoglu
 * @version 4.0
 */
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
     * Volume control for BGM.
     */
    private static final Float BGM_VOLUME = -30f;

    /**
     * Volume control for footsteps.
     */
    private static final Float FOOTSTEPS_VOLUME = -12f;

    /**
     * Volume control for finish sound effect.
     */
    private static final Float FINISH_VOLUME = -10f;

    /**
     * Coordinates of player sprite.
     */
    private final int[][] SPRITE_SHEET_COORDINATES = {{0, 0, 32, 41}, {32, 0, 32, 41}, {64, 0, 32, 41},
            {0, 41, 32, 41}, {32, 41, 32, 41}, {64, 41, 32, 41},
            {0, 82, 32, 41}, {32, 82, 32, 41}, {64, 82, 32, 41},
            {0, 123, 32, 41}, {32, 123, 32, 41}, {64, 123, 32, 41}};

    /**
     * Value of the sprite.
     */
    private int SPRITE_VALUE = 0;

    /**
     * Value of sprite start.
     */
    private int SPRITE_START = 0;

    /**
     * Value of sprite end.
     */
    private int SPRITE_END = 11;

    /**
     * Represents if player should be animated.
     */
    private Boolean SHOULD_ANIMATE = false;

    /**
     * Represents the player's location at an x-coordinate.
     */
    private double USER_LOCATION_X = 1;

    /**
     * Represents the player's location at an y-coordinate.
     */
    private double USER_LOCATION_Y = 1;

    /**
     * Represents the next player's location at an x-coordinate.
     */
    private double USER_NEXT_LOCATION_X = 1;

    /**
     * Represents the next player's location at an y-coordinate.
     */
    private double USER_NEXT_LOCATION_Y = 1;

    /**
     * Buffered Image of sprite.
     */
    private BufferedImage IMAGE = null;

    /**
     * Boolean representing if player is at question block in maze.
     */
    private Boolean myAtQuestion;

    /**
     * Boolean representing if the player can pass the question block.
     */
    private Boolean myCanPass;

    /**
     * Boolean representing if player can move without bounds.
     */
    private Boolean myNoClipActivated;

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
    private ScheduledExecutorService myExecutor;

    /**
     * Represents the player in the maze.
     */
    private Player myPlayer;

    /**
     * Logic behind the maze.
     */
    private src.Model.Map myMap;

    /**
     * Audio input for walking noises.
     */
    private AudioInputStream myAudioInputStreamFootSteps;

    /**
     * Audio input for BGM.
     */
    private AudioInputStream myAudioInputStreamBGM;

    /**
     * Audio input for BGM.
     */
    private AudioInputStream myAudioInputStreamFinish;

    /**
     * Audio clip for walking noises.
     */
    private Clip myClipFootSteps;

    /**
     * Audio clip for BGM.
     */
    private Clip myClipBGM;

    /**
     * Audio clip for BGM.
     */
    private Clip myClipFinish;

    /**
     * If the player has reached the finished line.
     */
    private Boolean myFinished;

    /**
     * Sets up each component necessary for the overall maze panel.
     */
    public MazePanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        myFinished = false;
        myTimer = new Timer(25, this);
        myPlayer = new Player();
        myMap = new src.Model.Map();
        addKeyListener(new Movement());
        setFocusable(true);
        myTimer.start();
        myAtQuestion = false;
        myCanPass = true;
        myNoClipActivated = false;
        myClockSeconds1 = myPlayer.getSecond1();
        myClockSeconds1 = myPlayer.getSecond2();
        myClockMinute1 = myPlayer.getMinute1();
        myClockMinute2 = myPlayer.getMinute2();
        clock();
        // Sprite setup.
        try {
            File yasuoFile = new File("TriviaMaze\\src\\Sprites\\yasuo.png");
            if (!yasuoFile.isFile()) {
                yasuoFile = new File("./src/Sprites/yasuo.png");
            }
            IMAGE = ImageIO.read(yasuoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer(50, actionListener);
        timer.setInitialDelay(0);
        timer.start();
        audioSetup();
    }

    /**
     * Repaints the panel each time a player moves.
     *
     * @param e The event that happens on the maze.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    /**
     * Sets up the sound effects.
     */
    public void audioSetup() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // Audio setup for footsteps.
        File sandFile = new File("TriviaMaze\\src\\Sound\\sand.wav");
        if (!sandFile.isFile()) {
            sandFile = new File("./src/Sound/sand.wav");
        }
        myAudioInputStreamFootSteps = AudioSystem.getAudioInputStream(sandFile.getAbsoluteFile());
        myClipFootSteps = AudioSystem.getClip();
        myClipFootSteps.open(myAudioInputStreamFootSteps);
        FloatControl volume1 = (FloatControl) myClipFootSteps.getControl(FloatControl.Type.MASTER_GAIN);
        volume1.setValue(FOOTSTEPS_VOLUME);

        // Audio setup for BGM.
        File backgroundmusicFile = new File("TriviaMaze\\src\\Sound\\backgroundmusic.wav");
        if (!backgroundmusicFile.isFile()) {
            backgroundmusicFile = new File("./src/Sound/backgroundmusic.wav");
        }
        myAudioInputStreamBGM = AudioSystem.getAudioInputStream(backgroundmusicFile.getAbsoluteFile());
        myClipBGM = AudioSystem.getClip();
        myClipBGM.open(myAudioInputStreamBGM);
        FloatControl volume2 = (FloatControl) myClipBGM.getControl(FloatControl.Type.MASTER_GAIN);
        volume2.setValue(BGM_VOLUME);
        myClipBGM.loop(Clip.LOOP_CONTINUOUSLY);

        // Audio setup for footsteps.
        File winFile = new File("TriviaMaze\\src\\Sound\\win.wav");
        if (!winFile.isFile()) {
            winFile = new File("./src/Sound/win.wav");
        }
        myAudioInputStreamFinish = AudioSystem.getAudioInputStream(winFile.getAbsoluteFile());
        myClipFinish = AudioSystem.getClip();
        myClipFinish.open(myAudioInputStreamFinish);
        FloatControl volume3 = (FloatControl) myClipFinish.getControl(FloatControl.Type.MASTER_GAIN);
        volume3.setValue(FINISH_VOLUME);
    }

    /**
     * Draws each maze component onto the GUI.
     *
     * @param g The object used to draw the maze.
     */
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < myMap.getMazeSize(); i++) {
            for (int j = 0; j < myMap.getMazeSize(); j++) {
                if (myMap.getElement(i, j) == 0) {
                    g.drawImage(myMap.getFireBlock(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (myMap.getElement(i, j) == 1) {
                    g.drawImage(myMap.getWater(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (myMap.getElement(i, j) == 2) {
                    g.drawImage(myMap.getWall(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (myMap.getElement(i, j) == 3) {
                    g.drawImage(myMap.getQuestion(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                } else if (myMap.getElement(i, j) == 4) {
                    g.drawImage(myMap.getFinish(), j * ROOM_SIZE, i * ROOM_SIZE, null);
                }
            }
        }

        Image subSprite = IMAGE.getSubimage(SPRITE_SHEET_COORDINATES[SPRITE_VALUE][0], SPRITE_SHEET_COORDINATES[SPRITE_VALUE][1], SPRITE_SHEET_COORDINATES[SPRITE_VALUE][2], SPRITE_SHEET_COORDINATES[SPRITE_VALUE][3]);
        g.drawImage(subSprite, (int) (USER_LOCATION_X * ROOM_SIZE) + 6, (int) (USER_LOCATION_Y * ROOM_SIZE), null);

        // Creates the current room interface.
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("Current room:", 40, Y_COORDINATE_INTERFACE);
        if (myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate()) == 1) {
            g.drawImage(myMap.getWater(), 200, Y_COORDINATE_INTERFACE - 30, this);
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

    /**
     * Method that handles the game clock.
     */
    public void clock() {
        myExecutor = Executors.newScheduledThreadPool(1);
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
        myExecutor.scheduleAtFixedRate(run, 0, 1, TimeUnit.SECONDS);
        myPlayer.setClock(myClockSeconds1, myClockSeconds2, myClockMinute1, myClockMinute2);
    }

    /**
     * Default override blank method.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    /**
     * Adds a change listener.
     *
     * @param listener The listener you want to change to.
     */
    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    /**
     * Getter for ChangeListeners.
     *
     * @return ChangeListener.
     */
    public ChangeListener[] getChangeListeners() {
        return listenerList.getListeners(ChangeListener.class);
    }

    /**
     * Fires the change listeners.
     */
    protected void fireChangeListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : getChangeListeners()) {
            listener.stateChanged(event);
        }
    }

    /**
     * Sets boolean value for if the player can pass or not.
     *
     * @param myCanPass boolean value for if the player can pass or not.
     */
    public void setMyCanPass(Boolean myCanPass) {
        this.myCanPass = myCanPass;
    }

    /**
     * Getter for boolean whether the player is sitting at a question block or not.
     *
     * @return boolean whether the player is sitting at a question block or not.
     */
    public Boolean getAtQuestion() {
        return myAtQuestion;
    }

    /**
     * Removes a question room.
     */
    public void removeQuestionRoom() {
        myMap.changeElement(myPlayer.getRoomYCoordinate(), myPlayer.getRoomXCoordinate(), 1);
    }

    /**
     * @param userLocationX
     * @param userLocationY
     */
    public void setUserLocation(double userLocationX, double userLocationY) {
        this.USER_LOCATION_X = userLocationX;
        this.USER_LOCATION_Y = userLocationY;
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
            if (myCanPass && !myFinished) {
                if (keycode == KeyEvent.VK_W && (!(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() - 1) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() - 1) == 2))) {
                    animatePlayer(0, -1);
                } else if (keycode == KeyEvent.VK_S && (!(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() + 1) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate() + 1) == 2))) {
                    animatePlayer(0, 1);
                } else if (keycode == KeyEvent.VK_A && !(myMap.getMapRoom(myPlayer.getRoomXCoordinate() - 1, myPlayer.getRoomYCoordinate()) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate() - 1, myPlayer.getRoomYCoordinate()) == 2)) {
                    animatePlayer(-1, 0);
                } else if (keycode == KeyEvent.VK_D && (!(myMap.getMapRoom(myPlayer.getRoomXCoordinate() + 1, myPlayer.getRoomYCoordinate()) == 0) &&
                        !(myMap.getMapRoom(myPlayer.getRoomXCoordinate() + 1, myPlayer.getRoomYCoordinate()) == 2))) {
                    animatePlayer(1, 0);
                }
                if (myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate()) == 3) {
                    myAtQuestion = true;
                } else {
                    myAtQuestion = false;
                }
                myClipFootSteps.stop();
                myClipFootSteps.setMicrosecondPosition(0);
                myClipFootSteps.start();
                SPRITE_VALUE = SPRITE_START;
                // If at finish line.
                if (myMap.getMapRoom(myPlayer.getRoomXCoordinate(), myPlayer.getRoomYCoordinate()) == 4) {
                    myExecutor.shutdown();
                    myClipFinish.start();
                    myFinished = true;
                    showFinished();
                }
            }
            fireChangeListeners();
        }
    }

    /**
     * Shows a video when the player has finished the maze.
     */
    public void showFinished() {
        ImageIcon img = new ImageIcon("TriviaMaze\\src\\Sprites\\cow.gif");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/cow.gif");
        }
        Image finishedImage = img.getImage();
        Image finished = finishedImage.getScaledInstance(400, 400, java.awt.Image.SCALE_DEFAULT);

        ImageIcon icon = new ImageIcon(finished);
        Image image = icon.getImage();
        image = image.getScaledInstance(400, 400, Image.SCALE_DEFAULT);

        icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);

        JFrame f = new JFrame("You Won!!!");
        f.getContentPane().add(label);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /**
     * Default override blank method.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Default override blank method.
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Default override blank method.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Getter for the player.
     *
     * @return the Player.
     */
    public Player getPlayer() {
        return myPlayer;
    }

    /**
     * Setter for the player
     *
     * @param thePlayer the player.
     */
    public void setPlayer(Player thePlayer) {
        myPlayer = thePlayer;
    }

    /**
     * Getter for boolean value whether no clip is activated or not.
     *
     * @return boolean value whether no clip is activated or not.
     */
    public Boolean getMyNoClipActivated() {
        return myNoClipActivated;
    }

    /**
     * Setter for the noClip.
     *
     * @param myNoClipActivated boolean value whether no clip is activated or not.
     */
    public void setMyNoClipActivated(Boolean myNoClipActivated) {
        this.myNoClipActivated = myNoClipActivated;
    }

    /**
     * Action listener for animation.
     */
    private ActionListener actionListener = e -> {
        if (SHOULD_ANIMATE) {
            if (SPRITE_VALUE == SPRITE_END) {
                SPRITE_VALUE = SPRITE_START;
            }
            SPRITE_VALUE = SPRITE_VALUE + 1;
            if (USER_NEXT_LOCATION_X > USER_LOCATION_X) {
                USER_LOCATION_X += (USER_NEXT_LOCATION_X - Math.floor(USER_LOCATION_X)) / 10;
            } else {
                USER_LOCATION_X += (USER_NEXT_LOCATION_X - Math.ceil(USER_LOCATION_X)) / 10;
            }
            if (USER_NEXT_LOCATION_Y > USER_LOCATION_Y) {
                USER_LOCATION_Y += (USER_NEXT_LOCATION_Y - Math.floor(USER_LOCATION_Y)) / 10;
            } else {
                USER_LOCATION_Y += (USER_NEXT_LOCATION_Y - Math.ceil(USER_LOCATION_Y)) / 10;
            }
            if (Math.abs(USER_NEXT_LOCATION_X - USER_LOCATION_X) < 0.00000000000001 && Math.abs(USER_NEXT_LOCATION_Y - USER_LOCATION_Y) < 0.00000000000001) {
                SHOULD_ANIMATE = false;
            }
        } else {
            SPRITE_VALUE = 1;
        }
        revalidate();
        repaint();
    };

    /**
     * Animates the player.
     *
     * @param x x-value of the player.
     * @param y y-value of the player.
     */
    private void animatePlayer(int x, int y) {
        if (x == 0 && y == 1) {
            SPRITE_START = 0;
            SPRITE_END = 2;
        } else if (x == -1 && y == 0) {
            SPRITE_START = 3;
            SPRITE_END = 5;
        } else if (x == 1 && y == 0) {
            SPRITE_START = 6;
            SPRITE_END = 8;
        } else if (x == 0 && y == -1) {
            SPRITE_START = 9;
            SPRITE_END = 11;
        }
        USER_LOCATION_X = myPlayer.getRoomXCoordinate();
        USER_LOCATION_Y = myPlayer.getRoomYCoordinate();
        SHOULD_ANIMATE = true;
        myPlayer.move(x, y);
        USER_NEXT_LOCATION_X = myPlayer.getRoomXCoordinate();
        USER_NEXT_LOCATION_Y = myPlayer.getRoomYCoordinate();
    }
}
