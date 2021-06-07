package src.View;

/**
 * This class will set up the entirety of the Trivia Maze GUI.
 *
 * @author Eugene Oh, Yavuzalp Turkoglu, Jonathan Cho
 * @version Spring 2021
 */

import src.Model.QuestionAnswer;
import src.SaveLoad.LoadAction;
import src.SaveLoad.SaveAction;
import src.sql.SQLHelper;
import src.View.QuestionsAnsweredCounter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class TriviaMazeGUI extends JPanel {

    /**
     * The size of the frame.
     */
    private static final Dimension FRAME_SIZE = new Dimension(800, 700);

    /**
     * Frame for the overall GUI.
     */
    private final JFrame myFrame;

    /**
     * Panel for the overall GUI.
     */
    private src.View.QuestionPane myQuestionPanel;


    /**
     * Panel for the overall GUI.
     */
    private src.View.MazePanel maze;

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
     * The amount of questions answered right by the user.
     */
    private int myQuestionsAnsweredRight;

    /**
     * The amount of questions answered wrong by the user.
     */
    private int myQuestionsAnsweredWrong;

    /**
     * The total mount of questions answered.
     */
    private int myQuestionsAnsweredTotal;

    /**
     * Sets up the overall frame and adds its necessary components.
     */
    public TriviaMazeGUI() {
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
        JPanel eastPanel = new JPanel(new GridLayout(2, 0));

        QuestionsAnsweredCounter counter = new src.View.QuestionsAnsweredCounter();


        JPanel northEastPanel = new JPanel();

        counter.setPreferredSize(new Dimension(240,240));
        eastPanel.setPreferredSize(new Dimension(245,240));
        eastPanel.setVisible(true);

        counter.setVisible(true);
        final QuestionAnswer[] question = {SQLHelper.getQuestionAnswer()};
        myQuestionPanel = new src.View.QuestionPane(question[0]);
        myQuestionPanel.setVisible(false);

        eastPanel.add(myQuestionPanel, "North");
        eastPanel.add(counter, "South");
        revalidate();
        repaint();
        maze = new src.View.MazePanel();
        final Boolean[] needNewQuestion = {true};
        myQuestionsAnsweredRight = 0;
        myQuestionPanel.addChangeListener(new ChangeListener() {
            /** Called in response to slider events in this window. */
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                src.View.QuestionPane questionpane = (src.View.QuestionPane) theEvent.getSource();
                if (questionpane.isAnsweredCorrect) {
                    maze.setCanPass(true);
                    needNewQuestion[0] = true;
                    maze.removeQuestionRoom();
                    counter.myQuestionsAnsweredRightAdd();
                    counter.myQuestionsAnsweredTotalAdd();
                    counter.repaint();
                } else if (!questionpane.isAnsweredCorrect) {
                    maze.setCanPass(false);
                    question[0] = SQLHelper.getQuestionAnswer();
                    myQuestionPanel.updateQuestion(question[0], true);
                    counter.myQuestionsAnsweredWrongAdd();
                    counter.myQuestionsAnsweredTotalAdd();
                    counter.repaint();
                }

            }
        });

        maze.addChangeListener(new ChangeListener() {
            /** Called in response to slider events in this window. */
            @Override
            public void stateChanged(final ChangeEvent theEvent) {

                if (maze.getNoClipActivated() == true) {
                    maze.setCanPass(true);
                    myQuestionPanel.setVisible(false);
                } else {
                    if (!maze.getAtQuestion()) {
                        myQuestionPanel.setVisible(false);
                    } else {
                        maze.setCanPass(false);
                        myQuestionPanel.setVisible(true);
                        if (question[0].getIsAnswered()) {
                            maze.setCanPass(true);
                        }
                    }
                    if (needNewQuestion[0]) {
                        question[0] = SQLHelper.getQuestionAnswer();
                        myQuestionPanel.updateQuestion(question[0], false);
                        needNewQuestion[0] = false;
                    }
                }
            }
        });
        myFrame.add(maze);
        myFrame.add(eastPanel,BorderLayout.EAST);

        start();
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
                new SaveAction(maze);
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
                new LoadAction(maze);
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
                aboutPane.showMessageDialog(new JFrame(), "<html>This is a Maze game where you must answer <br> Trivia questions from League of Legends to traverse through and escape.\n<html>",
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
                instructionsPane.showMessageDialog(new JFrame(), "<html>Instructions: Movement: Up (W) Down(S) Left(A) Right(D) <br> when you run into a wall, " +
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
                // Add code here ---------------
                maze.setNoClipActivated(true);

            }
        });
    }
}