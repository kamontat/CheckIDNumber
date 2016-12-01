package com.kamontat.gui.popup;

import com.kamontat.code.font.FontBook;
import com.kamontat.code.watch.StopWatch;

import javax.swing.*;

import java.awt.*;
import java.util.*;

import static com.kamontat.code.window.Display.getCenterLocation;

public class LoadingPopup extends JFrame implements Observer {
	private JProgressBar progressBar;
	private JLabel statusLabel;
	
	private static LoadingPopup page;
	private StopWatch watch = new StopWatch();
	
	private LoadingPopup() {
		super("Loading...");
		Panel container = new Panel(new BorderLayout());
		
		statusLabel = new JLabel("Status");
		statusLabel.setForeground(new Color(0, 0, 0));
		statusLabel.setVerticalAlignment(SwingConstants.CENTER);
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		
		container.add(progressBar, BorderLayout.NORTH);
		container.add(statusLabel, BorderLayout.CENTER);
		
		setContentPane(container);
		
		setSize(450, 100);
		setLocation(getCenterLocation(getSize()));
		setVisible(false);
		
		addFont();
		
		setAlwaysOnTop(true);
	}
	
	public static LoadingPopup getInstance() {
		if (page == null) page = new LoadingPopup();
		return page;
	}
	
	private void addFont() {
		statusLabel.setFont(FontBook.getFontLabel());
	}
	
	private void setProgressValue(int value) {
		progressBar.setValue(value);
		progressBar.setString("-" + progressBar.getValue() + "-");
	}
	
	private void setProgressLabel(String status, Color color) {
		statusLabel.setText(status);
		statusLabel.setForeground(color);
	}
	
	public void showPage(int progressSize) {
		try {
			setVisible(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			progressBar.setMaximum(progressSize);
			
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
		setProgressLabel("Status", new Color(0, 0, 0));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			setProgressLabel((String) arg, new Color(0, 122, 255));
		} else {
			setProgressValue(Integer.parseInt(String.valueOf(arg)));
		}
	}
}
