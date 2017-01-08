package com.kamontat.code.menu;

import javax.swing.*;
import java.awt.*;

import static com.kamontat.config.Config.version;

/**
 * this class is from create new JMenuItem
 * in each method they have own action to do, so please choose the correct or fit your action
 *
 * @author kamontat
 * @version 1.0
 * @since 10/29/2016 AD - 11:13 PM
 */
public class MenuItem {
	public static JMenuItem backMenu(Window page) {
		JMenuItem exit = new JMenuItem("Back");
		exit.addActionListener(e -> page.dispose()); /* back action */
		return exit;
	}
	
	public static JMenuItem exitMenu() {
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e -> System.exit(0)); /* exit action */
		return exit;
	}
	
	public static JMenuItem about() {
		String content = "This program is for collection thailand id-number\n";
		String text = String.format("and if you have any error, feel free to contact me by mail \"kamontat_c@hotmail.com\"\nCurrent version is %s", version);
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(e -> JOptionPane.showMessageDialog(null, content + text, "About Me", JOptionPane.INFORMATION_MESSAGE));
		return about;
	}
}
