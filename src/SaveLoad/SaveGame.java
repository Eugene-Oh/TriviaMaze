package src.SaveLoad;

import src.View.MazePanel;
import java.awt.*;
import java.io.*;

/**
 * A class used to save a game's state.
 *
 * @author Eugene Oh, Yavuzalp Turkoglu, Jonathan Cho
 * @version Spring 2021
 */

public class SaveGame {

    /**
     * Constructs a SaveGame and saves all the data for the current game to a file.
     */
    public SaveGame(MazePanel thePanel) {
        // Save all the data for the current drawing to a file.
        Frame fr = new Frame();

        FileDialog fd; // A file dialog box that will let the user
        // specify the output file.

        fd = new FileDialog(fr, "Save to File", FileDialog.SAVE);
        fd.show();

        String fileName = fd.getFile(); // Get the file name specified by the user.
        if (fileName == null)
            return; // User has canceled.

        String directoryName = fd.getDirectory(); // The name of the directory
        // where the specified file is located.

        File file = new File(directoryName, fileName); // Combine the directory name with the
        // name to produce a usable file specification.

        file.setWritable(true); // make it writable.

        ObjectOutputStream obj_out;
        try {
            obj_out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            obj_out.writeObject(thePanel.getPlayer()); // s
            obj_out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
            return;
        }
    }
}
