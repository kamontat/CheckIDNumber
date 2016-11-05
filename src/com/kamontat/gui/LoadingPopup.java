package com.kamontat.gui;

import com.kamontat.code.font.FontBook;

import javax.swing.*;

import java.awt.*;

import static com.kamontat.code.window.Display.getCenterLocation;

public class LoadingPopup extends JDialog {
	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel statusLabel;
	
	private String done = "Finish";
	
	public LoadingPopup() {
		setContentPane(contentPane);
		
		setSize(450, 100);
		setLocation(getCenterLocation(getSize()));
		setVisible(true);
		
		progressBar.setStringPainted(true);
		addFont();
		
		setAlwaysOnTop(true);
	}
	
	private void addFont() {
		statusLabel.setFont(FontBook.getFontLabel());
	}
	
	public void setProgressValue(int value) {
		progressBar.setValue(value);
		progressBar.setString(progressBar.getValue() + "%");
	}
	
	public void setProgressLabel(String status) {
		statusLabel.setText(status);
		statusLabel.setForeground(Color.GREEN);
	}
	
	public void setDoneLabel(String status) {
		done = status;
	}
	
	public void startLoading(Thread thread) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		try {
			thread.start();
			thread.join();
			
			System.out.println(done);
			statusLabel.setText(done);
			statusLabel.setForeground(Color.RED);
			
			Thread.sleep(500);
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
