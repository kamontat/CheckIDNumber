package com.kamontat.code.database;

import com.kamontat.code.object.IDNumber;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

/**
 * this class is about text file of id-number (server) and list in the app (local)
 *
 * @author kamontat
 * @version 1.2
 * @since 17/8/59 - 23:52
 */
public class DatabaseAPI {
	/**
	 * get current dir
	 */
	public static File dir = Paths.get("").toAbsolutePath().toFile();
	
	/**
	 * get database.db (using sqlite)
	 */
	public static Database db = Database.getDatabase();
	public static File textFile = new File("");
	
	/**
	 * list all id in text file, update by method assignIDList
	 */
	public static ArrayList<IDNumber> idList = new ArrayList<>();
	
	/**
	 * get all id-number from database.db and assign it into <code>idList</code>
	 */
	public static void assignIDList() {
		idList = db.getAll();
	}
	
	/**
	 * search <code>IDNumber</code> by id from <code>local</code> and return index<br>
	 * O-notation = O(idList.length)
	 *
	 * @param id
	 * 		id that want to search
	 * @return index of that id if it's exist, otherwise return -1
	 */
	public static int search_index_local(IDNumber id) {
		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i).getId().equals(id.getId()) && idList.get(i).getStatus() == id.getStatus()) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * search <code>IDNumber</code> by id from <code>database.db</code> and return index<br>
	 *
	 * @param id
	 * 		id that want to search
	 * @return index of that id if it's exist, otherwise return -1
	 */
	public static int search_index_database(IDNumber id) {
		return db.getSqlID(id);
	}
	
	public static int getLocalSize() {
		return idList.size();
	}
	
	public static int getDatabaseSize() {
		return db.getSize();
	}
	
	/**
	 * update text-file by using <code>idList</code>
	 * O-notation = O(idList.length)
	 */
	public static void updateDatabase() {
		/*
		LoadingPopup loading = LoadingPopup.getInstance();
		
		loading.setProgressLabel("Start update " + idList.size() + " ID to text-file");
		StopWatch watch = new StopWatch();
		watch.start();
		Runnable runner = () -> {
			try {
				FileWriter writer = new FileWriter(textFile);
				for (int i = 0; i < idList.size(); i++) {
					writer.write(idList.get(i).saveFormat() + "\n");
					
					loading.setProgressValue(((i + 1) * 100) / idList.size());
				}
				writer.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Can't save into File, \nplease try again", "Error", JOptionPane.ERROR_MESSAGE);
				//				textFile = createTextFile();
			}
			watch.stop();
			loading.setDoneLabel("Finish update textfile" + watch);
		};
		loading.startLoading(new Thread(runner));
		*/
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
			//			textFile = createTextFile();
		}
	}
	
	public static void clearFile() {
		textFile.delete();
		//		createTextFile();
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
				//				textFile = createTextFile();
				updateDatabase();
			} else {
				desktop.open(folder);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't automatic open, please open by yourself At " + textFile.getPath(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
