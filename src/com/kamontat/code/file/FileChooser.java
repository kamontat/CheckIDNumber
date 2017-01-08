package com.kamontat.code.file;

import com.kamontat.code.constant.FileExtension;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

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
		
		fileChooser.addChoosableFileFilter(new FileExtensionFilter(fileChooser, FileExtension.TEXT));
		fileChooser.addChoosableFileFilter(new FileExtensionFilter(fileChooser, FileExtension.EXCEL));
		fileChooser.addChoosableFileFilter(new FileExtensionFilter(fileChooser, FileExtension.EXCEL_NEW));
	}
	
	private File getSelectedFile(Component c) {
		int returnVal = fileChooser.showDialog(c, "Open");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
	public File open(Component c) {
		File f = getSelectedFile(c);
		return FileExtension.isValid(f) ? f: null;
	}
	
	public File save(Component c) {
		File f = getSelectedFile(c);
		if (!FileExtension.isHaveExt(f))
			f = new File(f.getAbsolutePath() + "." + ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0]);
		else if (!FileExtension.isValid(f)) return null;
		
		try {
			if (f.createNewFile()) return f;
			else return null;
		} catch (Exception ignored) {
			return null;
		}
	}
}
