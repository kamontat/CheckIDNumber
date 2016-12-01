package com.kamontat.code.menu;

import com.kamontat.code.database.DatabaseAPI;
import com.kamontat.code.file.ExcelFile;

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
	private static ExcelFile file = ExcelFile.getFile();
	
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
	
	
	public static JMenuItem exportMenuXLS() {
		JMenuItem exportExcel = new JMenuItem("Export (.xls)");
		exportExcel.addActionListener(e -> file.createExcelFile(".xls")); /* export action */
		return exportExcel;
	}
	
	public static JMenuItem exportMenuXLSX() {
		JMenuItem exportExcel = new JMenuItem("Export (.xlsx)");
		exportExcel.addActionListener(e -> file.createExcelFile(".xlsx")); /* export action */
		return exportExcel;
	}
	
	
	public static JMenuItem about() {
		String text = String.format("If you have any error, feel free to contact me by mail \"kamontat_c@hotmail.com\"\nThis current version is (%s)", version);
		JMenuItem about = new JMenuItem("about");
		about.addActionListener(e -> JOptionPane.showMessageDialog(null, text, "About Me", JOptionPane.INFORMATION_MESSAGE));
		return about;
	}
	
	public static JMenuItem status() {
		JMenuItem stat;
		
		if (DatabaseAPI.isExist()) {
			int localSize = DatabaseAPI.getLocalSize();
			int dbSize = DatabaseAPI.getDatabaseSize();
			if (localSize > dbSize) {
				stat = new JMenuItem("Save id into Database");
			} else if (localSize < dbSize) {
				stat = new JMenuItem("Save id into Local");
			} else {
				stat = new JMenuItem("GOOD");
			}
		} else {
			stat = new JMenuItem("Lost");
		}
		return stat;
	}
	
	public static JMenuItem fileCount() {
		return new JMenuItem(String.format("Database have: %,03d ID", DatabaseAPI.getDatabaseSize()));
	}
	
	public static JMenuItem localCount() {
		return new JMenuItem(String.format("Local    have: %,03d ID", DatabaseAPI.getLocalSize()));
	}
	
}
