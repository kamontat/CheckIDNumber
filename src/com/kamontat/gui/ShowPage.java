package com.kamontat.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.kamontat.gui.MainPage.idList;

public class ShowPage extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JList<String> list;

	public ShowPage() {
		setContentPane(contentPane);
		setModal(true);
		pack();


		assignList();

		buttonOK.addActionListener(e -> onOK());

		buttonCancel.addActionListener(e -> onCancel());

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onOK() {
		// add your code here
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}

	private void assignList() {
		list.setListData(idList.toArray(new String[idList.size()]));
	}

	public void run(Point point) {
		setMinimumSize(new Dimension(0, 0));
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {
		ShowPage dialog = new ShowPage();
		dialog.pack();
		dialog.setVisible(true);
		System.exit(0);
	}
}
