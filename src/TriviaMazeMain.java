package src;

/**
 * This class will start the Trivia Maze GUI.
 *
 * @author Eugene Oh, Yavuzalp Turkoglu, Jonathan Cho
 * @version Spring 2021
 */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.EventQueue;
import java.io.IOException;

public class TriviaMazeMain {

    /**
     * Runs the Trivia Maze GUI.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> {
            try {
                new src.View.TriviaMazeGUI().start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
