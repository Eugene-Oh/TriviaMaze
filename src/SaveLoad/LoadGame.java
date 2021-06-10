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
 * A class used to load a game state from a file.
 *
 * @author Eugene Oh, Yavuzalp Turkoglu, Jonathan Cho
 * @version Spring 2021
 */

public class LoadGame {

    /**
     * Constructs a LoadGame and loads a game from a file.
     */
    public LoadGame(MazePanel thePanel) {

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
