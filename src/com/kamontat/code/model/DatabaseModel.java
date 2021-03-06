package com.kamontat.code.model;

import com.kamontat.code.constant.SQLCode;
import com.kamontat.code.object.IDNumber;
import com.kamontat.gui.popup.LoadingPopup;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 11/29/2016 AD - 9:39 PM
 */
public class DatabaseModel extends Observable {
	private static String DATABASE = "jdbc:sqlite::resource:resources/database/";
	private static String DATABASE_NAME = DATABASE + "database.sqlite";
	private Connection connection;
	private Statement statement;
	
	private static DatabaseModel db;
	private LoadingPopup popup;
	
	// TODO 1/15/2017 AD 2:06 AM production must be true
	private boolean POPUP_ERROR = false;
	
	public static DatabaseModel getDatabase(Window parent) {
		if (db == null) {
			db = new DatabaseModel(parent);
		}
		return db;
	}
	
	private DatabaseModel(Window parent) {
		popup = new LoadingPopup(parent);
		
		addObserver(popup);
		assign(null);
		
		try {
			File file = new File("logfile");
			DriverManager.setLogWriter(new PrintWriter(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <B>BE SURE</B> TO USE THIS METHOD<br>
	 * YOUR DATABASE DATA WILL BE <b>RESET</b> IF YOU <b>HAVE PARAMETER NAME</b>
	 *
	 * @param name
	 * 		No need dot(.)
	 */
	@Nullable
	public boolean assign(String name) {
		if (name != null) {
			if (!name.contains(".")) name += ".sqlite";
			DATABASE_NAME = DATABASE + name;
		}
		
		return update();
	}
	
	private boolean update() {
		try {
			connection = DriverManager.getConnection(DATABASE_NAME);
			statement = connection.createStatement();
			return true;
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false;
	}
	
	public boolean close() {
		try {
			statement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false;
	}
	
	private boolean createTable() {
		String sql = "CREATE TABLE DATA(";
		sql += "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ";
		sql += "id_num TEXT NOT NULL, ";
		sql += "create_at TEXT NOT NULL);";
		
		String unique = "CREATE UNIQUE INDEX DATA_id_num_uindex ON DATA (id_num);";
		try {
			statement.executeUpdate(sql);
			statement.executeUpdate(unique);
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != SQLCode.SQLITE_FILE_NOT_FOUND.code) printSQLException(e);
		}
		return false;
	}
	
	public int getSqlID(IDNumber number) {
		int id = -1;
		String sql = String.format("SELECT id FROM DATA WHERE id_num = '%s';", number.getId());
		
		try {
			ResultSet set = statement.executeQuery(sql);
			while (set.next()) {
				id = set.getInt("id");
			}
			set.close();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return id;
	}
	
	public IDNumber getIDNumberBy(int sqlID) {
		// to check is table exist
		String sql = "SELECT * FROM DATA WHERE id = '" + sqlID + "'";
		try {
			ResultSet rs = statement.executeQuery(sql);
			String id = rs.getString("id_num");
			String createAt = rs.getString("create_at");
			rs.close();
			return new IDNumber(id, createAt);
		} catch (SQLException e) {
			printSQLException(e);
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	public int getSize() {
		String sql = "SELECT COUNT(*) FROM DATA;";
		
		try {
			ResultSet rs = statement.executeQuery(sql);
			return rs.getInt(1);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return 0;
	}
	
	public boolean add(IDNumber number) {
		String sql = "INSERT INTO DATA (id_num,create_at) ";
		sql += String.format("VALUES ('%s', '%s');", number.getId(), number.getCreateAt_string());
		
		try {
			statement.execute(sql);
			done();
			return true;
		} catch (SQLException e) {
			printSQLException(e);
			done();
			return false;
		}
	}
	
	public ArrayList<IDNumber> getAll() {
		ArrayList<IDNumber> list = new ArrayList<>();
		String sql = "SELECT * FROM DATA;";
		try {
			int readID = 0, size = getSize();
			
			popup.showPage(size);
			setChanged();
			notifyObservers("Start Loading new ID");
			
			ResultSet set = statement.executeQuery(sql);
			while (set.next()) {
				String id = set.getString("id_num");
				String createAt = set.getString("create_at");
				list.add(new IDNumber(id, createAt));
				
				setChanged();
				notifyObservers(++readID);
			}
			set.close();
			
			popup.hidePage(false);
			return list;
		} catch (SQLException e) {
			notifyObservers(SQLCode.which(e.getErrorCode()));
			printSQLException(e);
			
			popup.hidePage(true);
			return null;
		}
	}
	
	public boolean delete(IDNumber number) {
		int id = getSqlID(number);
		String sql = String.format("DELETE FROM DATA WHERE ID=%d;", id);
		try {
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false;
	}
	
	public boolean deleteAll() {
		try {
			String sql = "DELETE FROM DATA;";
			statement.executeUpdate(sql);
			sql = "DELETE FROM sqlite_sequence WHERE name='DATA';";
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isExist() {
		String sql = "SELECT COUNT(*) FROM DATA";
		try {
			ResultSet rs = statement.executeQuery(sql);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	private boolean printSQLException(SQLException e) {
		for (SQLCode error : SQLCode.values()) {
			if (e.getErrorCode() == error.code) {
				if (POPUP_ERROR)
					JOptionPane.showMessageDialog(null, e.getMessage(), error.message, JOptionPane.ERROR_MESSAGE);
				System.err.println("SQLState: " + e.getSQLState());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.printf("%13d\n", error.code);
				System.err.println("Message: " + e.getMessage());
				System.err.printf("%9s\n", error.message);
				e.printStackTrace();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				if (error == SQLCode.SQLITE_FILE_NOT_FOUND && POPUP_ERROR) {
					int a = JOptionPane.showConfirmDialog(null, "Important File Missing, Please contact developer", "Critical Error", JOptionPane.OK_CANCEL_OPTION);
					if (a == JOptionPane.OK_OPTION) System.exit(0);
					return false;
				}
			}
		}
		
		try {
			createTable();
			return true;
		} catch (Exception e1) {
			return false;
		}
	}
	
	public void done() {
		while (countObservers() != 0) deleteObservers();
	}
	
	@Override
	public String toString() {
		return "DatabaseModel{" + "connection=" + connection + ", statement=" + statement + '}';
	}
}
