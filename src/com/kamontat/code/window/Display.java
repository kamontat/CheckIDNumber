package com.kamontat.code.window;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

/**
 * @author kamontat
 * @since 17/8/59 - 23:54
 */
public class Display {
	/**
	 * this variable is display size, <br>using by said <code>"display.getWidth"</code> and <code>"display.getHeight"</code>
	 */
	private static DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

	/**
	 * get current dir
	 */
	public static File dir = Paths.get("").toAbsolutePath().toFile();

	public static Point getCenterLocation(Dimension pageSize) {
		return new Point((int) ((display.getWidth() / 2) - (pageSize.getWidth() / 2)), (int) ((display.getHeight() / 2) - (pageSize.getHeight() / 2)));
	}
}