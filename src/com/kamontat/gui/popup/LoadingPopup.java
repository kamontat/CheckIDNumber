package com.kamontat.gui.popup;

import com.kamontat.code.font.FontBook;
import com.kamontat.code.watch.StopWatch;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static com.kamontat.code.window.Display.getCenterPage;

public class LoadingPopup extends JDialog implements Observer {
	private JProgressBar progressBar;
	private JLabel statusLabel;
	
	private StopWatch watch = new StopWatch();
	
	public LoadingPopup(Window parent) {
		super(parent, "Loading..");
		
		setContentPane(setMainPanel());
		
		setSize(450, 100);
		setLocation(getCenterPage(parent, this));
		
		addFont();
		
		setAlwaysOnTop(true);
		setResizable(false);
	}
	
	private Panel setMainPanel() {
		Panel container = new Panel(new BorderLayout());
		
		statusLabel = new JLabel("Status");
		statusLabel.setForeground(new Color(0, 0, 0));
		statusLabel.setVerticalAlignment(SwingConstants.CENTER);
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		
		container.add(progressBar, BorderLayout.NORTH);
		container.add(statusLabel, BorderLayout.CENTER);
		
		return container;
	}
	
	private void addFont() {
		statusLabel.setFont(FontBook.getFontLabel());
	}
	
	private void setProgressValue(int value) {
		progressBar.setValue(value);
		progressBar.setString(progressBar.getValue() + "/" + progressBar.getMaximum());
	}
	
	private void setProgressLabel(String status, Color color) {
		statusLabel.setText(status);
		statusLabel.setForeground(color);
	}
	
	public void showPage(int progressSize) {
		setVisible(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		progressBar.setMaximum(progressSize);
		watch.start();
	}
	
	public void hidePage(boolean isError) {
		watch.stop();
		try {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if (!isError) setProgressLabel("Finish in " + watch, new Color(61, 225, 81));
			else setProgressLabel("Have some Error", new Color(255, 12, 0));
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dispose();
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
