package src;

/**
 * This class will start the Trivia Maze GUI.
 *
 * @author Eugene Oh, Yavuzalp Turkoglu, Jonathan Cho
 * @version Spring 2021
 */

import java.awt.EventQueue;

public class TriviaMazeMain {

    /**
     * Runs the Trivia Maze GUI.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> new src.View.TriviaMazeGUI().start());
    }
}
