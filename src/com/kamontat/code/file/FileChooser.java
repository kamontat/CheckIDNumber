package com.kamontat.code.file;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

/**
 * @author kamontat
 * @version 1.0
 * @since 12/20/2016 AD - 9:00 PM
 */
public class FileChooser {
	private final static String txt = "txt";
	JFileChooser fileChooser;
	
	public FileChooser() {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || getExtension(f).equals(txt);
			}
			
			@Override
			public String getDescription() {
				return "Only text file (.txt)";
			}
		});
	}
	
	public void open(Component c) {
		int returnVal = fileChooser.showOpenDialog(c);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			// desination txt file
			fileChooser.setCurrentDirectory(file);
		}
		
	}
	
	public void save(Component c) {
		int returnVal = fileChooser.showSaveDialog(c);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			// desination txt file
			fileChooser.setCurrentDirectory(file);
			
		}
	}
	
	/*
	 * Get the extension of a file.
     */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}
}
