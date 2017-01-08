package com.kamontat.code.model;

import com.kamontat.code.object.IDNumber;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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
	 * get model.db (using sqlite)
	 */
	private DatabaseModel db;
	private static DatabaseAPI dbApi;
	
	/**
	 * list all id in text file, update by method update_local
	 */
	public static ArrayList<IDNumber> idList = new ArrayList<>();
	
	private DatabaseAPI(Window parent) {
		db = DatabaseModel.getDatabase(parent);
	}
	
	public static DatabaseAPI getDatabase(Window parent) {
		if (dbApi == null) dbApi = new DatabaseAPI(parent);
		return dbApi;
	}
	
	;
	
	/**
	 * get all id-number from model and assign it into <code>idList</code>
	 */
	public void update_local() {
		idList = db.getAll();
	}
	
	public void update_database() {
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
	public int search_index_local(IDNumber id) {
		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i).getId().equals(id.getId()) && idList.get(i).getStatus() == id.getStatus()) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * search <code>IDNumber</code> by id from <code>model</code> and return index<br>
	 *
	 * @param id
	 * 		id that want to search
	 * @return index of that id if it's exist, otherwise return -1
	 */
	public int search_index_database(IDNumber id) {
		return db.getSqlID(id);
	}
	
	public int getLocalSize() {
		return idList.size();
	}
	
	public int getDatabaseSize() {
		return db.getSize();
	}
	
	public boolean addID(IDNumber id) {
		boolean isDone = db.addID(id);
		if (isDone) idList.add(id);
		return isDone;
	}
	
	public boolean removeID(IDNumber id) {
		boolean isDone = db.delete(id);
		if (isDone) idList.remove(id);
		return isDone;
	}
	
	public void clearAll() {
		idList = new ArrayList<>();
		db.deleteAll();
	}
	
	public boolean isExist() {
		return db.isExist();
	}
	
	/**
	 * open location of text-file automatic iff file exist
	 */
	public void openFolder() {
		if (db.isExist()) {
			// redirect to file
		} else {
			JOptionPane.showMessageDialog(null, "File had been delete, \nProgram will backup current data to new file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
