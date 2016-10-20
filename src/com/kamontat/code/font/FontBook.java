package com.kamontat.code.font;

import com.kamontat.main.Main;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

/**
 * @author kamontat
 * @version 1.2
 * @since 19/8/59 - 17:00
 */
public class FontBook {
	/**
	 * using <b>PTMono.ttc</b> font size 42
	 *
	 * @return font
	 */
	public static Font getFontMain() {
		try {
			InputStream stream = Main.class.getResourceAsStream("/resources/fontbook/PTMono.ttc");
			return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(Font.BOLD, 42f);
		} catch (Exception e) {
			String a = e.getMessage();
			JOptionPane.showMessageDialog(null, "cannot load font " + a, "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * using <b>Caudex-Regular.ttf</b> font size 24
	 *
	 * @return font
	 */
	public static Font getFontButton() {
		try {
			InputStream stream = Main.class.getResourceAsStream("/resources/fontbook/Caudex-Regular.ttf");
			return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(24f);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "cannot load font", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * using <b>BigshotOne.ttf</b> font size 22
	 *
	 * @return font
	 */
	public static Font getFontLabel() {
		try {
			InputStream stream = Main.class.getResourceAsStream("/resources/fontbook/BigshotOne.ttf");
			return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(22f);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "cannot load font", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * using <b>BigshotOne.ttf</b> font by parems size
	 *
	 * @param size
	 * 		size of font
	 * @return font
	 */
	public static Font getFontLabel(float size) {
		try {
			InputStream stream = Main.class.getResourceAsStream("/resources/fontbook/BigshotOne.ttf");
			return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(size);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "cannot load font", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * using <b>AlmendraSC-Regular.ttf</b> font size 22
	 *
	 * @return font
	 */
	public static Font getFontTextField() {
		try {
			InputStream stream = Main.class.getResourceAsStream("/resources/fontbook/AlmendraSC-Regular.ttf");
			return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(22f);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "cannot load font", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * using <b>CharlemagneStd-Bold.otf</b> font size 26
	 *
	 * @return font
	 */
	public static Font getFontList() {
		try {
			InputStream stream = Main.class.getResourceAsStream("/resources/fontbook/CharlemagneStd-Bold.otf");
			return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(26f);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "cannot load font", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * using <b>DigitalNumbers-Regular.ttf</b> font size 25
	 *
	 * @return font
	 */
	public static Font getDigitalFont() {
		try {
			InputStream stream = Main.class.getResourceAsStream("/resources/fontbook/DigitalNumbers-Regular.ttf");
			return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(25f);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "cannot load font", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * using <b>waan-free_regular.ttf</b> font
	 *
	 * @param size
	 * 		create by <code>size</code>
	 * @return font
	 */
	public static Font getThaiFont(float size) {
		try {
			InputStream stream = Main.class.getResourceAsStream("/resources/fontbook/waan-free_regular.ttf");
			return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(size);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "cannot load font", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return null;
	}
}
