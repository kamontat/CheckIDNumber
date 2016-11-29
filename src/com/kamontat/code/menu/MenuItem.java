package com.kamontat.code.menu;

import com.kamontat.code.database.DatabaseBackup;
import com.kamontat.gui.EnterPage;
import com.kamontat.gui.ShowPage;

import javax.swing.*;
import java.awt.*;

import static com.kamontat.code.database.DatabaseBackup.*;
import static com.kamontat.code.file.ExcelFile.createExcelFile;
import static com.kamontat.code.window.Display.getCenterLocation;
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
	public static JMenuItem addMenu(Window window) {
		JMenuItem add = new JMenuItem("Add ID");
		add.addActionListener(e -> {
			window.dispose();
			EnterPage page = new EnterPage();
			page.run(getCenterLocation(page.getSize()));
		});
		return add;
	}
	
	public static JMenuItem showMenu(Window window) {
		JMenuItem add = new JMenuItem("Show all ID");
		add.addActionListener(e -> {
			window.dispose();
			ShowPage page = new ShowPage();
			page.run(getCenterLocation(page.getSize()));
		});
		return add;
	}
	
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
	
	
	public static JMenuItem uploadMenu() {
		JMenuItem upload = new JMenuItem("upload (Local -> File)");
		upload.addActionListener(e -> updateTextFile()); /* upload action */
		return upload;
	}
	
	public static JMenuItem downloadMenu() {
		JMenuItem download = new JMenuItem("download (File -> Local)");
		download.addActionListener(e -> assignIDList()); /* download action */
		return download;
	}
	
	public static JMenuItem toMenu() {
		JMenuItem to;
		if (!DatabaseBackup.textFile.exists()) {
			to = new JMenuItem("Backup Data");
		} else {
			to = new JMenuItem("Go (To File)");
		}
		to.addActionListener(e -> openFolder()); /* export action */
		return to;
	}
	
	
	public static JMenuItem exportMenuXLS() {
		JMenuItem exportExcel = new JMenuItem("Export (.xls)");
		exportExcel.addActionListener(e -> createExcelFile(".xls")); /* export action */
		return exportExcel;
	}
	
	public static JMenuItem exportMenuXLSX() {
		JMenuItem exportExcel = new JMenuItem("Export (.xlsx)");
		exportExcel.addActionListener(e -> createExcelFile(".xlsx")); /* export action */
		return exportExcel;
	}
	
	
	public static JMenuItem about() {
		String text = String.format("If you have any error, feel free to contact me by mail \"kamontat_c@hotmail.com\"\nThis current version is (%s)", version);
		JMenuItem about = new JMenuItem("about");
		about.addActionListener(e -> JOptionPane.showMessageDialog(null, text, "About Me", JOptionPane.INFORMATION_MESSAGE));
		return about;
	}
	
	public static JMenuItem fileStatus() {
		JMenuItem stat;
		
		if (DatabaseBackup.textFile.exists()) {
			if (getLine() > idList.size()) {
				stat = new JMenuItem("Download (save id in file)");
			} else if (getLine() < idList.size()) {
				stat = new JMenuItem("Upload (save id in local)");
			} else {
				stat = new JMenuItem("GOOD");
			}
		} else {
			stat = new JMenuItem("Lost");
		}
		return stat;
	}
	
	public static JMenuItem fileCount() {
		return new JMenuItem(String.format("File  have: %03d ID", getLine()));
	}
	
	public static JMenuItem localCount() {
		return new JMenuItem(String.format("Local have: %03d ID", idList.size()));
	}
	
}
