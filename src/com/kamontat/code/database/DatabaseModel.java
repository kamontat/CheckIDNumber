package com.kamontat.code.database;

import com.kamontat.code.constant.SQLCode;
import com.kamontat.code.object.IDNumber;
import com.kamontat.gui.popup.LoadingPopup;

import java.sql.*;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 11/29/2016 AD - 9:39 PM
 */
public class DatabaseModel extends Observable {
	private final static String DATABASE_NAME = "jdbc:sqlite:database.sqlite";
	private Connection connection;
	private Statement statement;
	
	private static DatabaseModel db = new DatabaseModel();
	private LoadingPopup popup = new LoadingPopup();
	
	public int progress;
	
	private DatabaseModel() {
		addObserver(popup);
		try {
			assign();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	private void assign() throws SQLException {
		connection = DriverManager.getConnection(DATABASE_NAME);
		statement = connection.createStatement();
	}
	
	public void close() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	public static DatabaseModel getDatabase() {
		if (db == null) {
			db = new DatabaseModel();
		}
		return db;
	}
	
	private void createTable() {
		String sql = "CREATE TABLE DATA (";
		sql += "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ";
		sql += "id_num TEXT NOT NULL, ";
		sql += "create_at TEXT NOT NULL);";
		
		String unique = "CREATE UNIQUE INDEX DATA_id_num_uindex ON DATA (id_num);";
		try {
			statement.executeUpdate(sql);
			statement.executeUpdate(unique);
		} catch (SQLException e) {
			printSQLException(e);
		}
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
	
	public boolean addID(IDNumber number) {
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
			notifyObservers("Start Loading " + size + " IDs");
			
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
		}
		popup.hidePage(true);
		return null;
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
	
	public void deleteAll() {
		try {
			String sql = "DELETE FROM DATA;";
			statement.executeUpdate(sql);
			sql = "DELETE FROM sqlite_sequence WHERE name='DATA';";
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isExist() {
		// to check is table exist
		String sql = "SELECT COUNT(id='1') FROM DATA";
		try {
			ResultSet rs = statement.executeQuery(sql);
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() != SQLCode.SQLITE_ERROR.code) {
				printSQLException(e);
			}
			return false;
		}
	}
	
	private void printSQLException(SQLException e) {
		if (!isExist()) createTable();
		for (SQLCode code : SQLCode.values()) {
			if (e.getErrorCode() == code.code) {
				//				JOptionPane.showMessageDialog(null, e.getMessage(), code.message, JOptionPane.ERROR_MESSAGE);
				System.err.println("SQLState: " + e.getSQLState());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("Message: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void done() {
		deleteObservers();
	}
}
