/*
 * TCSS 305 � Fall 2020
 * Assignment 4: GUI Drawing and Menus
 *
 */
package src.SaveLoad;

import src.Model.Player;
import src.View.MazePanel;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


/**
 * LoadAction is the action we use to load drawings from a file.
 *
 * @author Jonathan Cho
 * @author Yavuzalp
 * @version Fall 2020
 */
public class LoadAction {

    /**
     * Constructs a LoadAction.
     * <p>
     * Loads all the drawing data from a file to app.
     */
    public LoadAction(MazePanel thePanel) {

        FileDialog fd; // A file dialog box that will let the user
        // specify the input file.

        fd = new FileDialog(new Frame(), "Load File", FileDialog.LOAD);
        fd.show();

        String fileName = fd.getFile(); // Get the file name specified by the user.

        if (fileName == null)
            return; // User has canceled.

        String directoryName = fd.getDirectory(); // The name of the directory
        // where the specified file is located.

        File file = new File(directoryName, fileName); // Combine the directory name with the
        // name to produce a usable file specification.

        ObjectInputStream ois;
        try { // Open the file.
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            Player player = (Player) ois.readObject();
            thePanel.setPlayer(player);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

    }
}
