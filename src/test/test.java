package test;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/24/2016 AD - 2:40 PM
 */
public class test {
	public static void main(String[] args) {
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:sqlite:database");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}
}
