package com.kamontat.code.database;

import com.kamontat.code.constant.SQLCode;
import com.kamontat.code.object.IDNumber;
import com.kamontat.gui.LoadingPopup;

import java.sql.*;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 11/29/2016 AD - 9:39 PM
 */
public class Database extends Observable {
	private Connection connection;
	private Statement statement;
	private static Database db = new Database();
	
	public int progress;
	
	private Database() {
		addObserver(LoadingPopup.getInstance());
		try {
			assign();
			// to check is table exist
			String sql = "SELECT COUNT(id='1') FROM DATA";
			ResultSet rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			if (e.getErrorCode() == SQLCode.SQLITE_ERROR.code) {
				createTable();
			} else {
				printSQLException(e);
			}
		}
	}
	
	private void assign() throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:database");
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
	
	public static Database getDatabase() {
		if (db == null) {
			db = new Database();
		}
		return db;
	}
	
	private void createTable() {
		String sql = "CREATE TABLE DATA (";
		sql += "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ";
		sql += "id_num TEXT NOT NULL, ";
		sql += "create_at DATE NOT NULL);";
		
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
		return -1;
	}
	
	public void addID(IDNumber number) {
		String sql = "INSERT INTO DATA (id_num,create_at) ";
		sql += String.format("VALUES ('%s', '%s');", number.getId(), number.getCreateAt_string());
		
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			printSQLException(e);
		}
		done();
	}
	
	public ArrayList<IDNumber> getAll() {
		ArrayList<IDNumber> list = new ArrayList<>();
		String sql = "SELECT * FROM DATA;";
		try {
			int readID = 0, size = getSize();
			
			LoadingPopup.getInstance().showPage();
			setChanged();
			notifyObservers("Start Loading " + size + " IDs");
			
			ResultSet set = statement.executeQuery(sql);
			while (set.next()) {
				String id = set.getString("id_num");
				String createAt = set.getString("create_at");
				list.add(new IDNumber(id, createAt));
				// done
				++readID;
				
				setChanged();
				notifyObservers((readID * 100) / size);
			}
			set.close();
			LoadingPopup.getInstance().hidePage(false);
			return list;
		} catch (SQLException e) {
			notifyObservers(SQLCode.which(e.getErrorCode()));
			printSQLException(e);
		}
		LoadingPopup.getInstance().hidePage(true);
		return null;
	}
	
	public void setAll(ArrayList<IDNumber> ids) {
		// do something
	}
	
	public void delete(IDNumber number) {
		int id = getSqlID(number);
		String sql = String.format("DELETE FROM DATA WHERE ID=%d;", id);
		
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			printSQLException(e);
		}
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
	
	private void printSQLException(SQLException e) {
		for (SQLCode code : SQLCode.values()) {
			if (e.getErrorCode() == code.code) {
				//				JOptionPane.showMessageDialog(null, e.getMessage(), code.message, JOptionPane.ERROR_MESSAGE);
				System.err.println("SQLState: " + e.getSQLState());
				System.err.println("Error Code: " + e.getErrorCode());
				System.err.println("Message: " + e.getMessage());
			}
		}
	}
	
	public void done() {
		deleteObservers();
	}
}
