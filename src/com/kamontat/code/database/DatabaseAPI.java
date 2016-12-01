package com.kamontat.code.database;

import com.kamontat.code.object.IDNumber;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private static DatabaseModel db = DatabaseModel.getDatabase();
	public static File textFile = new File("");
	
	/**
	 * list all id in text file, update by method update_local
	 */
	public static ArrayList<IDNumber> idList = new ArrayList<>();
	
	/**
	 * get all id-number from database and assign it into <code>idList</code>
	 */
	public static void update_local() {
		idList = db.getAll();
	}
	
	public static void update_database() {
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
	 * search <code>IDNumber</code> by id from <code>database</code> and return index<br>
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
	
	public static boolean addID(IDNumber id) {
		boolean isDone = db.addID(id);
		if (isDone) idList.add(id);
		return isDone;
	}
	
	public static boolean removeID(IDNumber id) {
		boolean isDone = db.delete(id);
		if (isDone) idList.remove(id);
		return isDone;
	}
	
	public static void clearAll() {
		idList = new ArrayList<>();
		db.deleteAll();
	}
	
	/**
	 * update text-file by using <code>idList</code>
	 * O-notation = O(idList.length)
	 */
	public static boolean insertToFile(IDNumber id) {
		try {
			FileWriter writer = new FileWriter(textFile, true);
			writer.write(id.saveFormat() + "\n");
			writer.close();
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't save into File, \nplease try again", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
			//			textFile = createTextFile();
		}
	}
	
	public static boolean isExist() {
		return db.isExist();
	}
	
	/**
	 * open location of text-file automatic iff file exist
	 */
	public static void openFolder() {
		if (db.isExist()) {
			// redirect to file
		} else {
			JOptionPane.showMessageDialog(null, "File had been delete, \nProgram will backup current data to new file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
