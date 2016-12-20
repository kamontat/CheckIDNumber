package com.kamontat.main;

import com.kamontat.code.database.DatabaseAPI;
import com.kamontat.code.database.LocationModel;
import com.kamontat.gui.MainPage;

import static com.kamontat.code.window.Display.getCenterLocation;

/**
 * @author kamontat
 * @version 2.0
 * @since 10/19/2016 AD - 1:45 AM
 */
public class Main {
	public static void main(String[] args) {
		LocationModel.getInstance().read();
		DatabaseAPI.getDatabase(null).update_local();
		
		MainPage page = new MainPage();
		page.run(getCenterLocation(page.getSize()));
	}
}