package com.kamontat.gui;

import com.kamontat.code.file.Location;
import com.kamontat.code.font.FontBook;

import javax.swing.*;
import java.awt.*;

import static com.kamontat.code.window.Display.getCenterLocation;
import static com.kamontat.code.database.Database.assignIDList;

/**
 * @author kamontat
 * @since 10/18/2016 AD - 2:30 PM
 */
public class LauncherPage extends JFrame {
	private JProgressBar progressBar;
	private JPanel panel;
	private JLabel statusLabel;

	public static String statusMessage = "Start Loading";

	public LauncherPage() {
		addFont();
		
		Thread runThread = new Thread() {
			@Override
			public void run() {
				super.run();
				setContentPane(panel);
				pack();
				setLocation(getCenterLocation(getSize()));
				setVisible(true);
				setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			}
		};

		Thread progressThread = new Thread() {
			@Override
			public void run() {
				super.run();
				progress(statusLabel);
			}
		};

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
				// read province and district
				if (!Location.read()) {
					JOptionPane.showMessageDialog(null, "Can't read json_location file \nplease contact to developer.\nif you want information feature.", "Error Loading file", JOptionPane.ERROR_MESSAGE);
				} else {
					LauncherPage.statusMessage = "Finish load Province and District " + (System.currentTimeMillis() - start) + " ms";
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
				}

				start = System.currentTimeMillis();
				LauncherPage.statusMessage = "Start loading id-number";
				// read id number from file
				assignIDList();
				LauncherPage.statusMessage = "Finish loading id-number " + (System.currentTimeMillis() - start) + " ms";
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
				}
			}
		};

		runThread.start();

		try {
			// must run page first
			runThread.join();

			progressThread.start();
			readingThread.start();


			readingThread.join();

			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			dispose();

			MainPage page = new MainPage();

			try {
				page.run(getCenterLocation(page.getSize()));
			} catch (Exception e) {
				page.run(new Point(0, 0));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param status
	 * 		label of status when pass specify number
	 */
	private void progress(JLabel status) {
		progressBar.setStringPainted(true);
		while (progressBar.getValue() != progressBar.getMaximum()) {
			progressBar.setString(progressBar.getValue() + "%");
			progressBar.setValue(progressBar.getValue() + 1);
			status.setText(LauncherPage.statusMessage);
			if (LauncherPage.statusMessage.contains("Finish")) {
				status.setForeground(Color.RED);
			} else {
				status.setForeground(Color.BLUE);
			}

			pack();
			try {
				Thread.sleep((long) Math.ceil(Math.random() * 100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void addFont() {
		statusLabel.setFont(FontBook.getFontLabel());
	}
}
