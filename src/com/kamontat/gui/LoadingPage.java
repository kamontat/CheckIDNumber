package com.kamontat.gui;

import com.kamontat.code.font.FontBook;

import javax.swing.*;
import java.awt.*;

import static com.kamontat.code.window.Display.getCenterLocation;
import static com.kamontat.gui.MainPage.exPack;

/**
 * @author kamontat
 * @since 10/18/2016 AD - 2:30 PM
 */
public class LoadingPage extends JFrame {
	private JFrame self = this;
	private JProgressBar progressBar;
	private JPanel panel;
	private JLabel statusLabel;
	
	public static String statusMessage = "Start Loading";
	
	public LoadingPage(boolean isAutoProgress, Thread... threads) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
		
		try {
			runThread.start();
			runThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread progressThread = new Thread();
		if (isAutoProgress) {
			progressThread = new Thread() {
				@Override
				public void run() {
					super.run();
					progress();
				}
			};
			
			progressThread.start();
		}
		
		try {
			for (Thread thread : threads) {
				thread.start();
			}
			
			for (Thread thread : threads) {
				thread.join();
			}
			
			if (isAutoProgress) progressThread.join();
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			dispose();
			
			
			MainPage page = new MainPage();
			page.run(getCenterLocation(page.getSize()));
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * auto run progress bar if cannot manage it.
	 */
	public void progress() {
		progressBar.setStringPainted(true);
		while (progressBar.getValue() != progressBar.getMaximum()) {
			progressBar.setString(progressBar.getValue() + "%");
			progressBar.setValue(progressBar.getValue() + 1);
			statusLabel.setText(LoadingPage.statusMessage);
			if (LoadingPage.statusMessage.contains("Finish")) {
				statusLabel.setForeground(Color.RED);
			} else {
				statusLabel.setForeground(Color.BLUE);
				if (progressBar.getValue() >= 95) {
					LoadingPage.statusMessage = "Finish load another content";
				}
			}
			statusLabel.setText(LoadingPage.statusMessage);
			
			exPack(this);
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
	
	private void run() {
		pack();
		setLocation(getCenterLocation(getSize()));
		setVisible(true);
	}
}
