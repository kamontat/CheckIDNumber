import com.kamontat.code.model.DatabaseModel;
import com.kamontat.code.test.AssignID;

import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 1/15/2017 AD - 2:27 AM
 */
public class DatabaseDump_TESTER {
	// null if want original file
	private final static String NAME = null;
	private final static int SIZE = 200000;
	
	// MAIN
	public static void main(String[] args) {
		DatabaseModel db = DatabaseModel.getDatabase(null);
		db.assign(NAME);
		AssignID assign = new AssignID(db);
		
		Scanner input = new Scanner(System.in);
		
		assign.assign(SIZE);
	}
}
