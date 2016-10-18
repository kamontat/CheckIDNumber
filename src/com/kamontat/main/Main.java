package com.kamontat.main;

import com.kamontat.code.object.IDNumber;
import com.kamontat.gui.LauncherPage;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

import static com.kamontat.code.file.TextFile.textFile;

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
		LauncherPage page = new LauncherPage();
	}
}