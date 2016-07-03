package com.kamontat.main;

import com.kamontat.gui.MainPage;

import java.awt.*;

public class Main {
	/**
	 * this variable is display size, <br>using by said <code>"display.getWidth"</code> and <code>"display.getHeight"</code>
	 */
	public static DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

	public static void main(String[] args) {
		MainPage page = new MainPage();
		try {
			page.run(getCenterLocation(page.getSize()));
		} catch (Exception e) {
			page.run(new Point(0, 0));
		}
	}

	public static Point getCenterLocation(Dimension pageSize) {
		return new Point((int) ((display.getWidth() / 2) - (pageSize.getWidth() / 2)), (int) ((display.getHeight() / 2) - (pageSize.getHeight() / 2)));
	}
}