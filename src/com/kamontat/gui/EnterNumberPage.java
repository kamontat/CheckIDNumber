package com.kamontat.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class EnterNumberPage extends JDialog {
	private JPanel contentPane;
	private JButton okBtn;
	private JButton cancelBtn;
	private JTextField textField;
	private JLabel totalNumberLabel;

	public EnterNumberPage() {
		setContentPane(contentPane);
		setModal(true);
		pack();

		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				warn();
			}
		});

		okBtn.addActionListener(e -> onOK());

		cancelBtn.addActionListener(e -> onCancel());

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onOK() {
		// get dir
		File dir = Paths.get("").toAbsolutePath().toFile();
		if (dir.isDirectory()) {
			File textFile = new File(dir.getPath() + "/output.txt");
			try {
				FileWriter writer = new FileWriter(textFile, true);

				textFile.createNewFile();
				writer.write(textField.getText() + "\n");

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done");
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}

	private void warn() {
		if (isAllNumberIn(textField.getText())) {
			// have only Integer or Double. :)
			okBtn.setEnabled(true);
		} else {
			// have some String too. :(
			okBtn.setEnabled(false);
		}
	}

	private Boolean isAllNumberIn(String input) {
		// update label
		totalNumberLabel.setText("Enter ID Number (" + input.length() + ")");
		// check every char in input String
		for (int i = 0; i < input.length(); i++) {
			char aChar = input.charAt(i);

			if (!Character.isDigit(aChar)) return false;
		}
		return input.length() == 13;
	}

	public void run(Point point) {
		setMinimumSize(new Dimension(0, 0));
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
