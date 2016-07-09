package com.kamontat.gui;

import com.kamontat.main.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author kamontat
 * @since 3/7/59 - 21:29
 */
public class MainPage extends JFrame {
	private JButton enterBtn;
	private JButton showBtn;
	private JPanel contentPane;

	/**
	 * get current dir
	 */
	static File dir = Paths.get("").toAbsolutePath().toFile();
	/**
	 * get text file
	 */
	static File textFile = assignTextFile();
	/**
	 * list all id in text file, update by method assignIDList
	 */
	static ArrayList<String> idList = new ArrayList<>();

	public MainPage() {
		setContentPane(contentPane);
		pack();

		assignIDList();

		enterBtn.addActionListener(e -> {
			EnterNumberPage page = new EnterNumberPage();
			page.run(Main.getCenterLocation(page.getSize()));
		});
	}

	private static File assignTextFile() {
		File textFile = null;
		// if it isn't dir, create new one
		if (!dir.isDirectory()) {
			textFile = new File(dir.getPath() + "/folderList");
			textFile.mkdir();
			textFile = new File(dir.getPath() + "/folderList/output.txt");
		} else {
			textFile = new File(dir.getPath() + "/output.txt");
		}

		try {
			textFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return textFile;
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

	public void run(Point point) {
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
