package com.kamontat.main;

import com.kamontat.code.object.Location;
import com.kamontat.gui.LoadingPage;

import javax.swing.*;

import static com.kamontat.code.database.Database.assignIDList;

/**
 * @author kamontat
 * @version 2.0
 * @since 10/19/2016 AD - 1:45 AM
 */
public class Main {
	public static String version = "2.1.2";
	
	public static void main(String[] args) {
		
		Thread readingThread = new Thread() {
			@Override
			public void run() {
				super.run();
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
				}
				// get time
				long start = System.currentTimeMillis();
				
				// label in launcher
				LoadingPage.statusMessage = "Start loading Province and District";
				
				// read province and district
				if (!Location.read()) {
					JOptionPane.showMessageDialog(null, "Can't read json_location file \nplease contact to developer.\nif you want information feature.", "Error Loading file", JOptionPane.ERROR_MESSAGE);
				} else {
					LoadingPage.statusMessage = "Finish load Province and District " + (System.currentTimeMillis() - start) + " ms";
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
				}
				
				start = System.currentTimeMillis();
				LoadingPage.statusMessage = "Start loading id-number";
				// read id number from file
				assignIDList();
				LoadingPage.statusMessage = "Finish load id-number " + (System.currentTimeMillis() - start) + " ms";
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
				}
			}
		};
		
		LoadingPage page = new LoadingPage(true, readingThread);
	}
}