package com.kamontat.gui;

import com.kamontat.code.font.FontBook;
import com.kamontat.code.watch.StopWatch;

import javax.swing.*;

import java.awt.*;
import java.util.*;

import static com.kamontat.code.window.Display.getCenterLocation;

public class LoadingPopup extends JDialog implements Observer {
	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel statusLabel;
	
	private static LoadingPopup page;
	private StopWatch watch = new StopWatch();
	
	private LoadingPopup() {
		setContentPane(contentPane);
		
		setSize(450, 100);
		setLocation(getCenterLocation(getSize()));
		setVisible(false);
		
		addFont();
		progressBar.setStringPainted(true);
		
		setAlwaysOnTop(true);
	}
	
	public static LoadingPopup getInstance() {
		if (page == null) page = new LoadingPopup();
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
		statusLabel.setText(status);
		statusLabel.setForeground(new Color(61, 225, 81));
	}
	
	public void showPage() {
		try {
			setVisible(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			Thread.sleep(1200);
			
			watch.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void hidePage(boolean isError) {
		watch.stop();
		try {
			if (!isError) statusLabel.setText("Finish in " + watch);
			Thread.sleep(1300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setVisible(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setProgressValue(0);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			setProgressLabel((String) arg);
		} else {
			setProgressValue(Integer.parseInt(String.valueOf(arg)));
		}
	}
}
