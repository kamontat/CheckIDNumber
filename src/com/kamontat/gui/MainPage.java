package com.kamontat.gui;

import com.kamontat.code.database.Database;
import com.kamontat.code.font.FontBook;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.kamontat.code.database.Database.openFolder;
import static com.kamontat.code.file.ExcelFile.createExcelFile;
import static com.kamontat.code.window.Display.getCenterLocation;

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

		pack();

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
	}

	private void addFont() {
		label1.setFont(FontBook.getFontMain());

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
				actions.add(toMenu());
				actions.addSeparator();
				actions.add(exportMenu());
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

		menu.add(actions);
		setJMenuBar(menu);
	}

	static JMenuItem exitMenu() {
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e -> System.exit(0)); /* exit action */
		return exit;
	}

	static JMenuItem exportMenu() {
		JMenuItem exportExcel = new JMenuItem("Export (.xls)");
		exportExcel.addActionListener(e -> createExcelFile()); /* export action */
		return exportExcel;
	}

	static JMenuItem toMenu() {
		JMenuItem to = null;
		if (!Database.textFile.exists()) {
			to = new JMenuItem("Backup Data");
		} else {
			to = new JMenuItem("To (file keeper)");
		}
		to.addActionListener(e -> openFolder()); /* export action */
		return to;
	}

	public void run(Point point) {
		pack();
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
