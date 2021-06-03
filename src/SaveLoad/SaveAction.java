/*
* TCSS 305 � Fall 2020
* Assignment 4: GUI Drawing and Menus
*
*/
package src.SaveLoad;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JPanel;

import src.View.MazePanel;

/**
 * SaveAction is the action we use to save drawings to a file.
 *
 * @author Jonathan Cho
 * @author Yavuzalp
 * @version Fall 2020
 */
public class SaveAction {

	/**
     * Constructs a SaveAction.
     * 
     * Save all the data for the current drawing to a file.
     */
	public SaveAction(MazePanel thePanel) {
		// Save all the data for the current drawing to a file.
		Frame fr = new Frame();

		FileDialog fd; // A file dialog box that will let the user
						// specify the output file.

		fd = new FileDialog(fr, "Save to File", FileDialog.SAVE);
		fd.show();

		String fileName = fd.getFile(); // Get the file name specified by the user.
		System.out.println(fileName);
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

			System.out.println(thePanel.getPlayer().getRoomXCoordinate());
			System.out.println(thePanel.getPlayer().getRoomYCoordinate());

			obj_out.writeObject(thePanel.getPlayer()); // s
			obj_out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
			return;
		}
	}
}
