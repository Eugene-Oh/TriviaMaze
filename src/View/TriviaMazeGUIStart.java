package src.View;

/**
 * This class will start the Trivia Maze GUI.
 *
 * @author Eugene Oh
 * @version Spring 2021
 */

import java.awt.EventQueue;

public class TriviaMazeGUIStart {

    /**
     * Runs the Trivia Maze GUI.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> new TriviaMazeGUI().start());
    }
}
