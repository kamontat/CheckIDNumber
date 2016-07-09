package com.kamontat.gui;

import com.kamontat.main.Main;

import javax.swing.*;
import java.awt.*;

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

		enterBtn.addActionListener(e -> {
			EnterPage page = new EnterPage();
			page.run(Main.getCenterLocation(page.getSize()));
		});

		showBtn.addActionListener(e -> {
			ShowPage page = new ShowPage();
			page.run(Main.getCenterLocation(page.getSize()));
		});
	}

	public void run(Point point) {
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
