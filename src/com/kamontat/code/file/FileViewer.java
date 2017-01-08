package com.kamontat.code.file;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import java.io.File;

/**
 * @author kamontat
 * @version 1.0
 * @since 1/8/2017 AD - 6:01 PM
 */
public class FileViewer extends FileView {
	public FileViewer() {
		super();
		
	}
	
	@Override
	public String getName(File f) {
		return super.getName(f);
	}
	
	@Override
	public String getDescription(File f) {
		return super.getDescription(f);
	}
	
	@Override
	public String getTypeDescription(File f) {
		return super.getTypeDescription(f);
	}
	
	@Override
	public Icon getIcon(File f) {
		return super.getIcon(f);
	}
	
	@Override
	public Boolean isTraversable(File f) {
		return super.isTraversable(f);
	}
}
