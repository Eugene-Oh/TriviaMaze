package src.View;

/**
 * This class will set up the entirety of the Trivia Maze GUI.
 *
 * @author Eugene Oh, Yavuzalp Turkoglu, Jonathan Cho
 * @version Spring 2021
 */

import src.Model.QuestionAnswer;
import src.sql.SQLHelper;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class TriviaMazeGUI extends JPanel {

    /**
     * The size of the frame.
     */
    private static final Dimension FRAME_SIZE = new Dimension(800, 700);

    /**
     * Volume control for when player gets a correct answer.
     */
    private static final Float CORRECT_VOLUME = -20f;

    /**
     * Volume control for when a player gets a wrong answer.
     */
    private static final Float WRONG_VOLUME = -20f;

    /**
     * RGB Value for background color.
     */
    private static final Color BACKGROUND_COLOR = new Color(142, 120, 120);

    /**
     * Frame for the overall GUI.
     */
    private final JFrame myFrame;

    /**
     * QuestionPanel for the overall GUI.
     */
    private src.View.QuestionPane myQuestionPanel;

    /**
     * MazePanel for the overall GUI.
     */
    private src.View.MazePanel myMaze;

    /**
     * The menu bar for the GUI.
     */
    private final JMenuBar myMenuBar;

    /**
     * The save menu item.
     */
    private JMenuItem mySaveMenuItem;

    /**
     * The load menu item.
     */
    private JMenuItem myLoadMenuItem;

    /**
     * The about menu item.
     */
    private JMenuItem myAboutMenuItem;

    /**
     * The instruction menu item.
     */
    private JMenuItem myInstructionsMenuItem;

    /**
     * The cheat menu item.
     */
    private JMenuItem myCheatsMenuItem;

    /**
     * The question counter.
     */
    private src.View.QuestionsAnsweredCounter myCounter;

    /**
     * The panel that holds multiple panels.
     */
    private JPanel myEastPanel;

    /**
     * Audio input for correct noise.
     */
    private AudioInputStream myAudioInputStreamCorrect;

    /**
     * Audio input for wrong noise.
     */
    private AudioInputStream myAudioInputStreamWrong;

    /**
     * Audio clip for correct noise.
     */
    private Clip myClipCorrect;

    /**
     * Audio clip for wrong noise.
     */
    private Clip myClipWrong;

    /**
     * Sets up the overall frame and adds its necessary components.
     */
    public TriviaMazeGUI() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        myFrame = new JFrame();
        ImageIcon img = new ImageIcon("TriviaMaze\\src\\Sprites\\mazeicon.png");
        if (img.getIconHeight() == -1) {
            img = new ImageIcon("./src/Sprites/mazeicon.png");
        }
        myFrame.setIconImage(img.getImage());
        myMenuBar = createMenuBar();
        myFrame.setTitle("TriviaMaze");
        myFrame.setPreferredSize(FRAME_SIZE);
        myFrame.add(myMenuBar, BorderLayout.NORTH);
        myEastPanel = new JPanel(new GridLayout(2, 0));
        myCounter = new src.View.QuestionsAnsweredCounter();
        myCounter.setPreferredSize(new Dimension(240, 240));
        myEastPanel.setPreferredSize(new Dimension(245, 240));
        myEastPanel.setVisible(true);
        myCounter.setVisible(true);
        QuestionAnswer[] myQuestion = {SQLHelper.getQuestionAnswer()};
        myQuestionPanel = new src.View.QuestionPane(myQuestion[0]);
        myQuestionPanel.setVisible(false);
        myEastPanel.add(myQuestionPanel, "North");
        myEastPanel.add(myCounter, "South");
        revalidate();
        repaint();
        myMaze = new src.View.MazePanel();
        final Boolean[] needNewQuestion = {true};

        // Adds required listeners.
        questionPanelListenerSetup(myQuestion, needNewQuestion);
        mazeListenerSetup(myQuestion, needNewQuestion);

        myCounter.setBackground(BACKGROUND_COLOR);
        myEastPanel.setBackground(BACKGROUND_COLOR);
        myQuestionPanel.setBackground(BACKGROUND_COLOR);
        myMaze.setBackground(BACKGROUND_COLOR);
        myFrame.add(myMaze);
        myFrame.add(myEastPanel, BorderLayout.EAST);

        // Adds audio to GUI.
        audioSetup();

        start();
    }

    /**
     * Setup for the question panel.
     *
     * @param myQuestion      The questions from the database.
     * @param needNewQuestion Represents if a new question should be fetched.
     */
    public void questionPanelListenerSetup(QuestionAnswer[] myQuestion, Boolean[] needNewQuestion) {
        myQuestionPanel.addChangeListener(new ChangeListener() {
            /** Called in response to slider events in this window. */
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                src.View.QuestionPane questionpane = (src.View.QuestionPane) theEvent.getSource();
                if (questionpane.getMyIsAnsweredCorrect()) {
                    myMaze.setMyCanPass(true);
                    needNewQuestion[0] = true;
                    myMaze.removeQuestionRoom();
                    myCounter.myQuestionsAnsweredRightAdd();
                    myCounter.myQuestionsAnsweredTotalAdd();
                    myCounter.repaint();
                    myClipCorrect.stop();
                    myClipCorrect.setMicrosecondPosition(0);
                    myClipCorrect.start();
                } else if (!questionpane.getMyIsAnsweredCorrect()) {
                    myMaze.setMyCanPass(false);
                    myQuestion[0] = SQLHelper.getQuestionAnswer();
                    myQuestionPanel.updateQuestion(myQuestion[0], true);
                    myCounter.myQuestionsAnsweredWrongAdd();
                    myCounter.myQuestionsAnsweredTotalAdd();
                    myCounter.repaint();
                    myClipWrong.stop();
                    myClipWrong.setMicrosecondPosition(0);
                    myClipWrong.start();
                }
            }
        });
    }

    /**
     * Setup for the maze listeners.
     *
     * @param myQuestion      The questions from the database.
     * @param needNewQuestion Represents if a new question should be fetched.
     */
    public void mazeListenerSetup(QuestionAnswer[] myQuestion, Boolean[] needNewQuestion) {
        myMaze.addChangeListener(new ChangeListener() {
            /** Called in response to slider events in this window. */
            @Override
            public void stateChanged(final ChangeEvent theEvent) {

                if (myMaze.getMyNoClipActivated() == true) {
                    myMaze.setMyCanPass(true);
                    myQuestionPanel.setVisible(false);
                } else {
                    if (!myMaze.getAtQuestion()) {
                        myQuestionPanel.setVisible(false);
                    } else {
                        myMaze.setMyCanPass(false);
                        myQuestionPanel.setVisible(true);
                        if (myQuestion[0].getIsAnswered()) {
                            myMaze.setMyCanPass(true);
                        }
                    }
                    if (needNewQuestion[0]) {
                        myQuestion[0] = SQLHelper.getQuestionAnswer();
                        myQuestionPanel.updateQuestion(myQuestion[0], false);
                        needNewQuestion[0] = false;
                    }
                }
            }
        });
    }

    /**
     * Sets up the correct and wrong sound effects.
     */
    public void audioSetup() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File correctFile = new File("TriviaMaze\\src\\Sound\\correct.wav");
        if (!correctFile.isFile()) {
            correctFile = new File("./src/Sound/correct.wav");
        }
        myAudioInputStreamCorrect = AudioSystem.getAudioInputStream(correctFile.getAbsoluteFile());
        myClipCorrect = AudioSystem.getClip();
        myClipCorrect.open(myAudioInputStreamCorrect);
        FloatControl volume1 = (FloatControl) myClipCorrect.getControl(FloatControl.Type.MASTER_GAIN);
        volume1.setValue(CORRECT_VOLUME);

        File wrongFile = new File("TriviaMaze\\src\\Sound\\wrong.wav");
        if (!wrongFile.isFile()) {
            wrongFile = new File("./src/Sound/wrong.wav");
        }
        myAudioInputStreamWrong = AudioSystem.getAudioInputStream(wrongFile.getAbsoluteFile());
        myClipWrong = AudioSystem.getClip();
        myClipWrong.open(myAudioInputStreamWrong);
        FloatControl volume2 = (FloatControl) myClipWrong.getControl(FloatControl.Type.MASTER_GAIN);
        volume2.setValue(WRONG_VOLUME);
    }

    /**
     * Starts the overall GUI.
     */
    public void start() {
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    /**
     * Creates the individual menu options for the menu bar.
     *
     * @return The completed JMenuBar.
     */
    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createOptionsMenu(), BorderLayout.EAST);
        menuBar.add(createHelpMenu(), BorderLayout.EAST);
        return menuBar;
    }

    /**
     * Creates the options menu.
     *
     * @return The completed options menu.
     */
    private JMenu createOptionsMenu() {
        final JMenu optionsMenu = new JMenu("Menu");
        optionsMenu.setMnemonic('O');

        // Adds the "Save" option to the menu.
        createSaveMenuItem();
        optionsMenu.add(mySaveMenuItem);
        optionsMenu.addSeparator();

        // Adds the "Load" option to the menu.
        createLoadMenuItem();
        optionsMenu.add(myLoadMenuItem);

        return optionsMenu;
    }

    /**
     * A completed save menu item that allows the player to save their state of game.
     */
    private void createSaveMenuItem() {
        mySaveMenuItem = new JMenuItem("Save Game", KeyEvent.VK_C);
        mySaveMenuItem.setEnabled(true);
        mySaveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new src.SaveLoad.SaveGame(myMaze);
            }
        });
    }

    /**
     * A completed load menu item that allows the player to load a state of game.
     */
    private void createLoadMenuItem() {
        myLoadMenuItem = new JMenuItem("Load Game", KeyEvent.VK_C);
        myLoadMenuItem.setEnabled(true);

        // Creates a mouse listener for the load game menu item.
        myLoadMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new src.SaveLoad.LoadGame(myMaze);
                myMaze.setUserLocation(myMaze.getPlayer().getRoomXCoordinate(),
                        myMaze.getPlayer().getRoomYCoordinate());
                validate();
                repaint();
            }
        });
    }

    /**
     * Creates the help menu.
     *
     * @return The help options menu.
     */
    private JMenu createHelpMenu() {
        final JMenu optionsMenu = new JMenu("Help");
        optionsMenu.setMnemonic('H');

        // Adds the "About" option to the menu.
        createAboutMenuItem();
        optionsMenu.add(myAboutMenuItem);
        optionsMenu.addSeparator();

        // Adds the "Instructions" option to the menu.
        createInstructionsMenuItem();
        optionsMenu.add(myInstructionsMenuItem);
        optionsMenu.addSeparator();

        // Adds the "Instructions" option to the menu.
        createCheatsMenuItem();
        optionsMenu.add(myCheatsMenuItem);
        optionsMenu.addSeparator();

        return optionsMenu;
    }

    /**
     * A completed about menu item that shows a displays a message about the game.
     */
    private void createAboutMenuItem() {
        myAboutMenuItem = new JMenuItem("About", KeyEvent.VK_A);
        myAboutMenuItem.addMouseListener(new MouseAdapter() {
            /**
             * Creates a mouse listener for the help menu item.
             */
            public void mousePressed(MouseEvent event) {
                final JOptionPane aboutPane = new JOptionPane();
                aboutPane.showMessageDialog(new JFrame(), "<html>This is a Maze game where you must" +
                                " answer <br> Trivia questions from League of Legends to traverse through " +
                                "and escape.\n<html>",
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * A completed instruction menu item that shows a displays the game's instructions.
     */
    private void createInstructionsMenuItem() {
        myInstructionsMenuItem = new JMenuItem("Instructions", KeyEvent.VK_I);
        myInstructionsMenuItem.addMouseListener(new MouseAdapter() {
            /**
             * Creates a mouse listener for the instructions menu item.
             */
            public void mousePressed(MouseEvent event) {
                final JOptionPane instructionsPane = new JOptionPane();
                instructionsPane.showMessageDialog(new JFrame(), "<html>Instructions: Movement: Up" +
                                " (W) Down(S) Left(A) Right(D) <br> when you run into a wall, " +
                                "you must answer a question on the right panel correctly to continue. " +
                                "<br> Otherwise you will have a small time penalty to answer again." +
                                "<br> Reach the finish line to win! \n<html>",
                        "Game Instructions: ", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * A completed cheat menu item that gives the player options to bypass usual game actions.
     */
    private void createCheatsMenuItem() {
        myCheatsMenuItem = new JMenuItem("Cheats", KeyEvent.VK_C);
        myCheatsMenuItem.addMouseListener(new MouseAdapter() {
            /**
             * Creates a mouse listener for the cheats menu item.
             */
            public void mousePressed(MouseEvent event) {
                myMaze.setMyNoClipActivated(true);
            }
        });
    }
}