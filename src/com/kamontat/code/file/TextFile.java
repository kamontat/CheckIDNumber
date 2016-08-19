package com.kamontat.code.file;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.kamontat.code.window.Display.dir;
import static com.kamontat.main.Main.idList;

/**
 * @author kamontat
 * @since 17/8/59 - 23:52
 */
public class TextFile {
	/**
	 * get text file
	 */
	public static File textFile = createTextFile();


	public static File createTextFile() {
		File textFile = null;

		textFile = new File(dir.getPath() + "/folderList");
		textFile.mkdir();
		textFile = new File(dir.getPath() + "/folderList/textfile.txt");

		try {
			textFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return textFile;
	}

	public static void updateTextFile() {
		try {
			FileWriter writer = new FileWriter(textFile);
			for (String id : idList) {
				writer.write(id + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void openFolder() {
		try {
			Desktop desktop = Desktop.getDesktop();
			desktop.open(new File(dir.getPath() + "/folderList"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't automatic open, please open by yourself At " + textFile.getPath(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
