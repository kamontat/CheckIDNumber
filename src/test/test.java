package test;

import com.kamontat.code.database.Database;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/24/2016 AD - 2:40 PM
 */
public class test {
	public static void main(String[] args) {
		Database db = Database.getDatabase();
		db.getAll().forEach(System.out::println);
		db.close();
	}
}
