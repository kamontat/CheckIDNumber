package com.kamontat.main;

import com.kamontat.code.file.Location;
import com.kamontat.code.object.IDNumber;
import com.kamontat.gui.MainPage;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

import static com.kamontat.code.file.TextFile.textFile;
import static com.kamontat.code.window.Display.getCenterLocation;

public class Main {
	/**
	 * list all id in text file, update by method assignIDList
	 */
	public static ArrayList<IDNumber> idList = new ArrayList<>();

	public static void assignIDList() {
		idList.removeAll(idList);
		try {
			Scanner input = new Scanner(textFile);
			while (input.hasNextLine()) {
				idList.add(new IDNumber(input.nextLine()));
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
	
	// TODO: 10/18/2016 AD Loading page
	public static void main(String[] args) throws URISyntaxException {
		if (!Location.read()) {
			JOptionPane.showMessageDialog(null, "Can't read json_location file \nplease contact to developer.\nif you want information feature.", "Error Loading file", JOptionPane.ERROR_MESSAGE);
		}

		MainPage page = new MainPage();

		try {
			page.run(getCenterLocation(page.getSize()));
		} catch (Exception e) {
			page.run(new Point(0, 0));
		}
	}
}