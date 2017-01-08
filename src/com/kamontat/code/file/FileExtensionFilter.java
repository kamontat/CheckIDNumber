package com.kamontat.code.file;

import com.kamontat.code.constant.FileExtension;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author kamontat
 * @version 1.0
 * @since 1/8/2017 AD - 10:52 PM
 */
public class FileExtensionFilter extends FileFilter {
	private JFileChooser fileChooser;
	// Known extension.
	private final FileExtension extension;
	
	public FileExtensionFilter(JFileChooser chooser, FileExtension extensions) {
		if (extensions == null) {
			throw new IllegalArgumentException("Extensions must be non-null and not empty");
		}
		
		fileChooser = chooser;
		this.extension = extensions;
	}
	
	@Override
	public boolean accept(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				fileChooser.setApproveButtonText("Open");
				fileChooser.setApproveButtonToolTipText("Open folder");
				return true;
			} else {
				fileChooser.setApproveButtonText("Choose");
				fileChooser.setApproveButtonToolTipText("Choose file (" + extension.extensionDot + ")");
				return FileExtension.getExtension(f) == extension;
			}
		}
		return false;
	}
	
	@Override
	public String getDescription() {
		return extension.description;
	}
	
	public FileExtension getExtension() {
		return extension;
	}
	
	public String toString() {
		return super.toString() + "[description=" + getDescription() + " extensions=" + getExtension() + "]";
	}
}
