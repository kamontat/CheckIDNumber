package com.kamontat.gui;

import com.kamontat.code.database.DatabaseAPI;
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
		super("Main Page");
		setContentPane(contentPane);
		
		addFont();
		
		createMenuBar();
		
		enterBtn.addActionListener(e -> {
			new EnterPage(this).run();
		});
		
		showBtn.addActionListener(e -> {
			new ShowPage(this).run();
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
		JMenu actions = new JMenu("More");
		actions.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				actions.removeAll();
				actions.add(about());
				actions.addSeparator();
				actions.add(testMenu(contentPane));
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
		
		JMenu status = new JMenu("Database");
		status.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				status.removeAll();
				status.add(status());
				status.add(dbCount());
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
	
	
	private JMenuItem status() {
		JMenuItem stat;
		
		if (DatabaseAPI.getDatabase(this).isExist()) {
			int localSize = DatabaseAPI.getDatabase(this).getLocalSize();
			int dbSize = DatabaseAPI.getDatabase(this).getDatabaseSize();
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
	
	private JMenuItem dbCount() {
		return new JMenuItem(String.format("Database have: %,03d ID", DatabaseAPI.getDatabase(this).getDatabaseSize()));
	}
	
	private JMenuItem localCount() {
		return new JMenuItem(String.format("Local    have: %,03d ID", DatabaseAPI.getDatabase(this).getLocalSize()));
	}
	
	static void exPack(Window frame) {
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
