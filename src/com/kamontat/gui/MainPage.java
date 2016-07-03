package com.kamontat.gui;

import com.kamontat.main.Main;

import javax.swing.*;
import java.awt.*;

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
		enterBtn.addActionListener(e -> {
			EnterNumberPage page = new EnterNumberPage();
			page.run(Main.getCenterLocation(page.getSize()));
		});
	}

	public void run(Point point) {
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
