package com.kamontat.code.font;

import java.awt.*;
import java.io.File;

import static com.kamontat.code.window.Display.dir;

/**
 * @author kamontat
 * @since 19/8/59 - 17:00
 */
public class FontBook {
	public static Font getFontMain() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(dir.getPath() + "/fontbook/PTMono.ttc")).deriveFont(Font.BOLD, 42f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Font getFontButton() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(dir.getPath() + "/fontbook/Caudex-Regular.ttf")).deriveFont(24f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Font getFontLabel() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(dir.getPath() + "/fontbook/BigshotOne.ttf")).deriveFont(22f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Font getFontTextField() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(dir.getPath() + "/fontbook/AlmendraSC-Regular.ttf")).deriveFont(22f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Font getFontList() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(dir.getPath() + "/fontbook/CharlemagneStd-Bold.otf")).deriveFont(26f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Font getDigitalFont() {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(dir.getPath() + "/fontbook/DigitalNumbers-Regular.ttf")).deriveFont(25f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
