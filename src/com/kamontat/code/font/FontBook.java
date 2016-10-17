package com.kamontat.code.font;

import com.kamontat.main.Main;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

/**
 * @author kamontat
 * @since 19/8/59 - 17:00
 */
public class FontBook {
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
