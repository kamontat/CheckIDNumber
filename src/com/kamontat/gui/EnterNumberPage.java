package com.kamontat.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class EnterNumberPage extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField textField;

	public EnterNumberPage() {
		setContentPane(contentPane);
		setModal(true);
		pack();

		buttonOK.addActionListener(e -> onOK());

		buttonCancel.addActionListener(e -> onCancel());

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onOK() {
		System.out.println(textField.getText());
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}

	private void run(Point point) {
		setMinimumSize(new Dimension(0, 0));
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
