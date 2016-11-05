package com.kamontat.main;

import com.kamontat.code.database.LocationModel;
import com.kamontat.gui.MainPage;

import static com.kamontat.code.database.Database.assignIDList;
import static com.kamontat.code.window.Display.getCenterLocation;

/**
 * @author kamontat
 * @version 2.0
 * @since 10/19/2016 AD - 1:45 AM
 */
public class Main {
	public static void main(String[] args) {
		
		Thread loadingLocation = new Thread() {
			@Override
			public void run() {
				super.run();
				// read location
				LocationModel.read();
			}
		};
		
		Thread loadingID = new Thread() {
			@Override
			public void run() {
				super.run();
				// read id
				assignIDList();
			}
		};
		
		try {
			loadingLocation.run();
			loadingLocation.join();
			
			loadingID.run();
			loadingID.join();
			
			MainPage page = new MainPage();
			page.run(getCenterLocation(page.getSize()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}