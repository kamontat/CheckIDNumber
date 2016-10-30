package com.kamontat.gui;

import com.kamontat.code.font.FontBook;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.kamontat.code.menu.MenuItem.*;
import static com.kamontat.code.window.Display.getCenterLocation;
import static com.kamontat.config.Config.version;

/**
 * @author kamontat
 * @since 3/7/59 - 21:29
 */
public class MainPage extends JFrame {
	private JButton enterBtn;
	private JButton showBtn;
	private JPanel contentPane;
	private JLabel label1;
	
	public MainPage() {
		setContentPane(contentPane);
		
		addFont();
		
		createMenuBar();
		
		enterBtn.addActionListener(e -> {
			EnterPage page = new EnterPage();
			page.run(getCenterLocation(page.getSize()));
		});
		
		showBtn.addActionListener(e -> {
			ShowPage page = new ShowPage();
			page.run(getCenterLocation(page.getSize()));
		});
		
		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> System.exit(0), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		
		exPack(this);
	}
	
	private void addFont() {
		label1.setFont(FontBook.getFontMain());
		label1.setToolTipText("Version: " + version);
		
		enterBtn.setFont(FontBook.getFontButton());
		showBtn.setFont(FontBook.getFontButton());
	}
	
	/**
	 * create new menu bar with unit item inside it.
	 */
	private void createMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu actions = new JMenu("Action");
		actions.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				actions.removeAll();
				actions.add(about());
				actions.addSeparator();
				actions.add(uploadMenu());
				actions.add(downloadMenu());
				actions.add(toMenu());
				actions.addSeparator();
				actions.add(exportMenuXLS());
				actions.add(exportMenuXLSX());
				actions.addSeparator();
				actions.add(exitMenu());
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
		
		JMenu status = new JMenu("File Status");
		status.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				status.removeAll();
				status.add(fileStatus());
				status.add(fileCount());
				status.add(localCount());
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
		
		menu.add(actions);
		menu.add(Box.createHorizontalGlue());
		menu.add(status);
		
		setJMenuBar(menu);
	}
	
	public static void exPack(Window frame) {
		frame.pack();
		frame.setLocation(getCenterLocation(frame.getSize()));
	}
	
	public void run(Point point) {
		exPack(this);
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
