package com.kamontat.gui;

import com.kamontat.code.font.FontBook;

import javax.swing.*;
import java.awt.*;

import static com.kamontat.code.window.Display.getCenterLocation;
import static com.kamontat.gui.MainPage.exPack;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/18/2016 AD - 2:30 PM
 */
public class LoadingPage extends JFrame {
	private JFrame self = this;
	private JPanel panel;
	public JProgressBar progressBar;
	public JLabel statusLabel;
	
	public static String statusMessage = "Start Loading";
	
	/**
	 * To show loading progress if some progress may using very long time <br>
	 * autoProgress is for if can't avg time to run progressBar the AutoProgress will do it, but <b>NOT good</b> <br>
	 * and threads is the running progress that want to run and include time to increase progressBar <br>
	 * To increase progressBar, it have to have <b>progressBar</b>, <b>statusLabel</b>, <b>LoadingPage.statusMessage</b>
	 *
	 * @param isAutoProgress
	 * 		true, if don't know how to manage time running
	 * @param threads
	 * 		running thread
	 */
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
	private void progress() {
		progressBar.setStringPainted(true);
		while (progressBar.getValue() != progressBar.getMaximum()) {
			progressBar.setString(progressBar.getValue() + "%");
			progressBar.setValue(progressBar.getValue() + 1);
			statusLabel.setText(LoadingPage.statusMessage);
			if (LoadingPage.statusMessage.contains("Finish")) {
				statusLabel.setForeground(Color.RED);
				if (progressBar.getValue() >= 95) {
					LoadingPage.statusMessage = "Finish load another content";
				}
			} else {
				statusLabel.setForeground(Color.BLUE);
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
