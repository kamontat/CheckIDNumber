package com.kamontat.main;

import com.kamontat.gui.MainPage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Main {
	/**
	 * this variable is display size, <br>using by said <code>"display.getWidth"</code> and <code>"display.getHeight"</code>
	 */
	private static DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

	/**
	 * get current dir
	 */
	public static File dir = Paths.get("").toAbsolutePath().toFile();

	/**
	 * get text file
	 */
	public static File textFile = createTextFile();

	/**
	 * list all id in text file, update by method assignIDList
	 */
	public static ArrayList<String> idList = new ArrayList<>();

	public static Point getCenterLocation(Dimension pageSize) {
		return new Point((int) ((display.getWidth() / 2) - (pageSize.getWidth() / 2)), (int) ((display.getHeight() / 2) - (pageSize.getHeight() / 2)));
	}

	public static void assignIDList() {
		idList.removeAll(idList);
		try {
			Scanner input = new Scanner(textFile);
			while (input.hasNextLine()) {
				idList.add(input.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int searchingIDList(String id) {
		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i).equals(id)) {
				return i;
			}
		}
		return -1;
	}

	public static File createTextFile() {
		File textFile = null;

		textFile = new File(dir.getPath() + "/folderList");
		textFile.mkdir();
		textFile = new File(dir.getPath() + "/folderList/output.txt");

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

	public static void main(String[] args) {
		MainPage page = new MainPage();
		try {
			page.run(getCenterLocation(page.getSize()));
		} catch (Exception e) {
			page.run(new Point(0, 0));
		}
	}
}