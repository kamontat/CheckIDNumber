package com.kamontat.code.file;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author kamontat
 * @version 1.0
 * @since 12/20/2016 AD - 9:00 PM
 */
public class FileChooser {
	private static FileChooser file;
	
	private JFileChooser fileChooser;
	
	public static FileChooser get() {
		if (file == null) file = new FileChooser();
		return file;
	}
	
	private FileChooser() {
		fileChooser = new JFileChooser();
		addFilter();
	}
	
	private void addFilter() {
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new ExcelFileFilter(fileChooser));
	}
	
	public File open(Component c) {
		int returnVal = fileChooser.showOpenDialog(c);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
	public File save(Component c) {
		int returnVal = fileChooser.showSaveDialog(c);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				file.createNewFile();
				return file;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
