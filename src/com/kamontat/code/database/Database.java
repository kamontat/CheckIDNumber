package com.kamontat.code.database;

import com.kamontat.code.object.IDNumber;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.kamontat.code.window.Display.dir;

/**
 * this class is about text file of id-number (server) and list in the app (local)
 *
 * @author kamontat
 * @since 17/8/59 - 23:52
 */
public class Database {

	/**
	 * get text file
	 */
	public static File textFile = createTextFile();

	/**
	 * list all id in text file, update by method assignIDList
	 */
	public static ArrayList<IDNumber> idList = new ArrayList<>();

	/**
	 * remove all id-number in <code>idList</code> and <br>
	 * get all id-number from textFile and assign it into <code>idList</code>
	 */
	public static void assignIDList() {
		boolean hasWrong = false;
		idList.removeAll(idList);
		try {
			Scanner input = new Scanner(textFile);
			while (input.hasNextLine()) {
				String[] dataIDNumber = input.nextLine().split(" ");

				String id = dataIDNumber[0];
				// if wrong format
				if (dataIDNumber.length == 1) {
					hasWrong = true;
					idList.add(new IDNumber(id));
				} else {
					LocalDateTime time = LocalDateTime.of(LocalDate.parse(dataIDNumber[1]), LocalTime.parse(dataIDNumber[2]));
					idList.add(new IDNumber(id, time));
				}
			}
			if (hasWrong) updateTextFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * search <code>IDNumber</code> id from <code>idList</code> and return index that id
	 * O-notation = O(idList.length)
	 *
	 * @param id
	 * 		id that want to search
	 * @return position of that id if it's exist, otherwise return -1
	 */
	public static int searchingIDList(IDNumber id) {
		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i).getId().equals(id.getId())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * create new text-file to save id-number if it isn't exist
	 *
	 * @return that new text-file
	 */
	private static File createTextFile() {
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

	/**
	 * update text-file by using <code>idList</code>
	 * O-notation = O(idList.length)
	 */
	public static void updateTextFile() {
		try {
			FileWriter writer = new FileWriter(textFile);
			for (IDNumber id : idList) {
				writer.write(id.saveFormat() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't save into File, \nplease try again", "Error", JOptionPane.ERROR_MESSAGE);
			textFile = createTextFile();
		}
	}

	/**
	 * open location of text-file automatic iff file exist
	 */
	public static void openFolder() {
		try {
			Desktop desktop = Desktop.getDesktop();
			File folder = new File(dir.getPath() + "/folderList");

			int numFile = folder.listFiles(pathname -> !pathname.isHidden()).length;

			if (numFile == 0) {
				JOptionPane.showMessageDialog(null, "File had been delete, \nProgram will backup current data to new file", "Error", JOptionPane.ERROR_MESSAGE);
				textFile = createTextFile();
				updateTextFile();
			} else {
				desktop.open(folder);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't automatic open, please open by yourself At " + textFile.getPath(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
