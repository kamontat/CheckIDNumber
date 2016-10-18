package com.kamontat.code.database;

import com.kamontat.code.object.IDNumber;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.kamontat.code.window.Display.dir;

/**
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

	public static void assignIDList() {
		idList.removeAll(idList);
		try {
			Scanner input = new Scanner(textFile);
			while (input.hasNextLine()) {
				String[] dataIDNumber = input.nextLine().split(" ");

				String id = dataIDNumber[0];
				LocalDateTime time = LocalDateTime.of(LocalDate.parse(dataIDNumber[1]), LocalTime.parse(dataIDNumber[2]));
				idList.add(new IDNumber(id, time));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int searchingIDList(IDNumber id) {
		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i).isSame(id)) {
				return i;
			}
		}
		return -1;
	}

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

	public static void updateTextFile() {
		try {
			FileWriter writer = new FileWriter(textFile);
			for (IDNumber id : idList) {
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
