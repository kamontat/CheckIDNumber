package com.kamontat.code.database;

import com.kamontat.code.object.IDNumber;
import com.kamontat.code.watch.StopWatch;
import com.kamontat.gui.LoadingPage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.kamontat.code.window.Display.dir;

/**
 * this class is about text file of id-number (server) and list in the app (local)
 *
 * @author kamontat
 * @version 1.2
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
	 * this method <b>already</b> update textFile if have something wrong
	 */
	public static void assignIDList() {
		LoadingPage loading = LoadingPage.getInstance();
		loading.setProgressLabel("Start loading ID");
		StopWatch watch = new StopWatch();
		watch.start();
		
		idList.removeAll(idList);
		try {
			Scanner input = new Scanner(textFile);
			final boolean[] hasWrong = {false};
			
			loading.startLoading(new Thread() {
				@Override
				public void run() {
					super.run();
					int idCount = getLine();
					int readID = 0;
					while (input.hasNextLine()) {
						String[] dataIDNumber = input.nextLine().split(" ");
						String id = dataIDNumber[0];
						// if wrong format
						if (dataIDNumber.length == 1) {
							hasWrong[0] = true;
							idList.add(new IDNumber(id));
						} else if (dataIDNumber.length == 3 || dataIDNumber.length == 4) {
							LocalDateTime time = LocalDateTime.of(LocalDate.parse(dataIDNumber[1]), LocalTime.parse(dataIDNumber[2]));
							idList.add(new IDNumber(id, time));
						} else {
							loading.setProgressLabel("Some id Error (can't load)");
						}
						loading.setProgressValue(((++readID) * 100) / idCount);
					}
					watch.stop();
					loading.setDoneLabel("Finish loaded IDNumber" + watch);
				}
			});
			
			if (hasWrong[0]) updateTextFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * search <code>IDNumber</code> by id from <code>idList</code> and return index that id <br>
	 * O-notation = O(idList.length)
	 *
	 * @param id
	 * 		id that want to search
	 * @return position of that id if it's exist, otherwise return -1
	 */
	public static int searchingIDList(IDNumber id) {
		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i).getId().equals(id.getId()) && idList.get(i).getStatus() == id.getStatus()) {
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
		File textFile;
		
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
	
	public static int getLine() {
		LineNumberReader lnr;
		try {
			lnr = new LineNumberReader(new FileReader(textFile));
			lnr.skip(Long.MAX_VALUE);
			return lnr.getLineNumber();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * update text-file by using <code>idList</code>
	 * O-notation = O(idList.length)
	 */
	public static void updateTextFile() {
		LoadingPage loading = LoadingPage.getInstance();
		loading.setProgressLabel("Start update text-file");
		StopWatch watch = new StopWatch();
		watch.start();
		
		loading.startLoading(new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					FileWriter writer = new FileWriter(textFile);
					for (int i = 0; i < idList.size(); i++) {
						writer.write(idList.get(i).saveFormat() + "\n");
						
						loading.setProgressValue(((i + 1) * 100) / idList.size());
					}
					writer.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Can't save into File, \nplease try again", "Error", JOptionPane.ERROR_MESSAGE);
					textFile = createTextFile();
				}
				watch.stop();
				loading.setDoneLabel("Finish update textfile" + watch);
			}
		});
	}
	
	/**
	 * update text-file by using <code>idList</code>
	 * O-notation = O(idList.length)
	 */
	public static void insertToFile(IDNumber id) {
		try {
			FileWriter writer = new FileWriter(textFile, true);
			writer.write(id.saveFormat() + "\n");
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't save into File, \nplease try again", "Error", JOptionPane.ERROR_MESSAGE);
			textFile = createTextFile();
		}
	}
	
	public static void clearFile() {
		textFile.delete();
		createTextFile();
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
