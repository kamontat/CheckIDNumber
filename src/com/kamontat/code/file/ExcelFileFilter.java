package com.kamontat.code.file;

import com.kamontat.code.constant.FileExtension;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author kamontat
 * @version 1.0
 * @since 1/8/2017 AD - 5:56 PM
 */
public class ExcelFileFilter extends FileFilter {
	private JFileChooser fileChooser;
	
	public ExcelFileFilter(JFileChooser fc) {
		fileChooser = fc;
	}
	
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			fileChooser.setApproveButtonText("Open");
			fileChooser.setApproveButtonToolTipText("Open directory");
			return true;
		}
		
		fileChooser.setApproveButtonText("Choose");
		fileChooser.setApproveButtonToolTipText("Choose file");
		FileExtension extension = FileExtension.getExtension(f);
		if (extension != null) {
			for (FileExtension t : FileExtension.values()) {
				if (extension == t) return true;
			}
		}
		return false;
	}
	
	@Override
	public String getDescription() {
		return "text file or excel file only";
	}
}
