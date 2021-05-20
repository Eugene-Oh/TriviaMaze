package src.View;


/**
 * This class will set up the entirety of the Trivia Maze GUI.
 *
 * @author Eugene Oh
 * @version Spring 2021
 */

import src.Model.QuestionAnswer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TriviaMazeGUI extends JPanel {

    /** The size of the frame. */
    private static final Dimension FRAME_SIZE = new Dimension(1000, 600);

    /** The size of the panel. */
    private static final Dimension PANEL_SIZE = new Dimension(250, 250);

    /** Frame for the overall GUI. */
    private final JFrame myFrame;

    /** Panel for the overall GUI. */
    private final JPanel myMazePanel;

    /** Panel for the overall GUI. */
    private final JPanel myQuestionPanel;

    /** Panel for the overall GUI. */
//    private final JPanel myControlPanel;

    /** The menu bar for the GUI. */
    private final JMenuBar myMenuBar;

    /** The save menu item. */
    private JMenuItem mySaveMenuItem;

    /** The load menu item. */
    private JMenuItem myLoadMenuItem;

    /** The about menu item. */
    private JMenuItem myAboutMenuItem;

    /** The instruction menu item. */
    private JMenuItem myInstructionsMenuItem;

    /** The cheat menu item. */
    private JMenuItem myCheatsMenuItem;

    /**
     * Sets up the overall frame and adds its necessary components.
     */
    public TriviaMazeGUI() {
        myFrame = new JFrame();
        myMazePanel = new JPanel(new BorderLayout());
        myMenuBar = createMenuBar();
        myFrame.setTitle("TriviaMaze");

        // Add code here ---------------
        myFrame.setPreferredSize(FRAME_SIZE);
        myFrame.setBackground(Color.BLACK);

        // Does not work properly. Fills the whole GUI.
        myMazePanel.setPreferredSize(PANEL_SIZE);

        myFrame.add(myMazePanel);

        myFrame.add(myMenuBar, BorderLayout.NORTH);

        QuestionAnswer question = new QuestionAnswer("What is 2 x 2?", new String[]{"2", "3","4", "5"}, "4");
        myQuestionPanel = new QuestionPane(question);
        myFrame.add(myQuestionPanel, BorderLayout.EAST);
//        System.out.println(question.getIsAnswered());
        myQuestionPanel.setVisible(true);
        createMapGUI();
    }

    /**
     * Starts the overall GUI.
     */
    public void start() {
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);

//        DrawPanel s = new DrawPanel();
//
//        myFrame.add(s);
//
//        s.setVisible(true);
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

        // Creates a mouse listener for the save game menu item.
        mySaveMenuItem.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                if (mySaveMenuItem.isEnabled()) {
                    // Add code here ---------------
                }
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
        myLoadMenuItem.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                if (myLoadMenuItem.isEnabled()) {
                    // Add code here ---------------
                }
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
                aboutPane.showMessageDialog(new JFrame(), "This is the Trivia Maze Game\n",
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
                instructionsPane.showMessageDialog(new JFrame(), "Instructions: Play the game!\n",
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
            }
        });
    }

    /**
     * Creates the map from the 2-D Model.Map object and inserts it into the GUI.
     */
    private void createMapGUI() {
        // Creates a new panel and map object
        JPanel panel = new JPanel();
        src.Model.Map map = new src.Model.Map();
        panel.setPreferredSize(PANEL_SIZE);
        panel.setLayout(new GridLayout(map.getLength(),map.getHeight()));
        JLabel[][] element = new JLabel[map.getLength()][map.getHeight()];

        // Creates a 2-D array of JLabels representing each "room" based on the map's numbers.
        for (int i = 0; i < map.getLength(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                element[i][j] = new JLabel();
                if (map.getElement(i, j) == 0) {
                    element[i][j].setBackground(Color.BLACK);
                } else if (map.getElement(i, j) == 1) {
                    element[i][j].setBackground(Color.WHITE);
                } else if (map.getElement(i, j) == 2) {
                    element[i][j].setBackground(Color.BLUE);
                }
                element[i][j].setOpaque(true);
                panel.add(element[i][j]);
            }
        }
        myFrame.add(panel);
    }
}
