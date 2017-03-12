package com.kamontat.code.model;

import com.kamontat.code.object.IDNumber;
import com.kamontat.code.test.AssignID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 1/15/2017 AD - 12:23 AM
 */
class DatabaseModel_TESTER {
	private static DatabaseModel model;
	private static DatabaseModel nModel;
	private static AssignID assignID;
	
	private IDNumber idn = new IDNumber("1231231234");
	
	private static int databaseSize = 20;
	
	@BeforeAll
	static void setModel() {
		model = DatabaseModel.getDatabase(null);
		model.assign("TESTER");
		
		nModel = DatabaseModel.getDatabase(null);
		nModel.assign("NOT_HERE");
		
		assignID = new AssignID(model);
		assignID.assign(databaseSize);
	}
	
	@BeforeEach
	void setUp() {
		model = DatabaseModel.getDatabase(null);
		model.assign("TESTER");
	}
	
	@Test
	void getSize() {
		assertEquals(databaseSize, model.getSize());
	}
	
	@Test
	void addAndGet() {
		model.add(idn);
		int id = model.getSqlID(idn);
		int newSize = model.getSize();
		System.out.println(id);
		System.out.println(newSize);
		//		assertEquals();
	}
	
	@Test
	void getAll() {
		
	}
	
	@Test
	void delete() {
		
	}
	
	@Test
	void deleteAll() {
		assertTrue(model.deleteAll());
		assertEquals(0, model.getSize());
	}
	
	@Test
	void isExist() {
		assertTrue(model.isExist());
		assertFalse(nModel.isExist());
	}
	
	@Test
	void done() {
		model.done();
		assertEquals(0, model.countObservers());
	}
	
	@Test
	void close() {
		assertTrue(model.close());
	}
}