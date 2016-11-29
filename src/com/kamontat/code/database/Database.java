package com.kamontat.code.database;

/**
 * @author kamontat
 * @version 1.0
 * @since 11/29/2016 AD - 9:39 PM
 */
public class Database {
	private static Database db = new Database();
	
	public static Database getDatabase() {
		if (db == null) {
			db = new Database();
		}
		return db;
	}
}
