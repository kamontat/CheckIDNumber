package com.kamontat.main;

import com.kamontat.code.database.LocationAPI;
import com.kamontat.gui.MainPage;

import static com.kamontat.code.database.DatabaseAPI.update_local;
import static com.kamontat.code.window.Display.getCenterLocation;

/**
 * @author kamontat
 * @version 2.0
 * @since 10/19/2016 AD - 1:45 AM
 */
public class Main {
	public static void main(String[] args) {
		new LocationAPI().read();
		update_local();
		
		MainPage page = new MainPage();
		page.run(getCenterLocation(page.getSize()));
	}
}