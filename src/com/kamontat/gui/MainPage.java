package com.kamontat.gui;

import com.kamontat.main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.kamontat.code.excelFile.createExcelFile;
import static com.kamontat.main.Main.assignIDList;

/**
 * @author kamontat
 * @since 3/7/59 - 21:29
 */
public class MainPage extends JFrame {
	private JButton enterBtn;
	private JButton showBtn;
	private JPanel contentPane;

	public MainPage() {
		setContentPane(contentPane);
		pack();

		assignIDList();

		createMenuBar();

		enterBtn.addActionListener(e -> {
			EnterPage page = new EnterPage();
			page.run(Main.getCenterLocation(page.getSize()));
		});

		showBtn.addActionListener(e -> {
			ShowPage page = new ShowPage();
			page.run(Main.getCenterLocation(page.getSize()));
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> System.exit(0), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}


	/**
	 * create new menu bar with unit item inside it.
	 */
	private void createMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu units = new JMenu("Action");

		JMenuItem exportExcel = new JMenuItem("Export (.xls)");
		exportExcel.addActionListener(e -> createExcelFile()); /* export action */

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e -> System.exit(0)); /* exit action */

		units.add(exportExcel);
		units.addSeparator();
		units.add(exit);


		menu.add(units);
		setJMenuBar(menu);
	}

	public void run(Point point) {
		pack();
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
