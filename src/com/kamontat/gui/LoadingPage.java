package com.kamontat.gui;

import com.kamontat.code.font.FontBook;

import javax.swing.*;
import java.awt.*;

import static com.kamontat.code.window.Display.getCenterLocation;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/18/2016 AD - 2:30 PM
 */
public class LoadingPage extends JFrame {
	private JFrame self = this;
	private JPanel panel;
	private JProgressBar progressBar;
	private JLabel statusLabel;
	
	private String done = "Finish";
	
	private static LoadingPage page;
	
	/**
	 * To show loading progress if some progress may using very long time <br>
	 * to use this class you must setting something before <br>
	 * first: setProgressLabel -> for setting label to tell what thing that on load
	 * second: setProgressValue -> for setting percent that on loading
	 * third: startLoading -> for setting which progress that you what to progress
	 */
	private LoadingPage() {
		addFont();
		progressBar.setStringPainted(true);
		setAlwaysOnTop(true);
		setContentPane(panel);
		setSize(450, 100);
		setLocation(getCenterLocation(getSize()));
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
	public static LoadingPage getInstance() {
		if (page == null) {
			page = new LoadingPage();
		}
		if (!page.isVisible()) {
			page.setVisible(true);
		}
		return page;
	}
	
	private void addFont() {
		statusLabel.setFont(FontBook.getFontLabel());
	}
	
	public void setProgressValue(int value) {
		progressBar.setValue(value);
		progressBar.setString(progressBar.getValue() + "%");
	}
	
	public void setProgressLabel(String status) {
		this.statusLabel.setText(status);
		statusLabel.setForeground(Color.GREEN);
	}
	
	public void setDoneLabel(String status) {
		done = status;
	}
	
	public void startLoading(Thread... threads) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		try {
			for (Thread thread : threads) {
				thread.start();
				thread.join();
			}
			
			this.statusLabel.setText(done);
			statusLabel.setForeground(Color.RED);
			
			Thread.sleep(500);
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			setVisible(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
