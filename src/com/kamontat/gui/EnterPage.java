package com.kamontat.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.kamontat.gui.MainPage.*;

public class EnterPage extends JDialog {
	private JPanel contentPane;
	private JButton okBtn;
	private JButton cancelBtn;
	private JTextField textField;
	private JLabel totalNumberLabel;
	private JLabel messageLabel;

	public EnterPage() {
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
		// call onOK() on ENTER
		contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onOK() {
		try {
			if (!isDuplicate(textField.getText(), idList)) {
				FileWriter writer = new FileWriter(textFile, true);
				writer.write(textField.getText() + "\n");
				writer.close();
				setMessage("Correct ID (Saved)", new Color(0, 255, 0));
				// rewrite new list in program whe program update
				assignIDList();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		pack();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}

	private void warn() {
		if (isAllNumberIn(textField.getText())) {
			if (textField.getText().length() == 13) {
				if (!isDuplicate(textField.getText(), idList)) {
					// have only Integer or Double. :)
					okBtn.setEnabled(true);
					setMessage("OK (Good ID)", new Color(0, 200, 0));
				} else {
					// id mustn't duplicate with the old one
					okBtn.setEnabled(false);
					setMessage("Error (Duplicate ID)", new Color(255, 0, 0));
				}
			} else {
				// id must have 13 character
				okBtn.setEnabled(false);
				setMessage("Warning (not equal 13)", new Color(255, 170, 0));
			}
		} else {
			// have some String too. :(
			okBtn.setEnabled(false);
			setMessage("Error (Have Alphabet)", new Color(255, 0, 0));
		}
	}

	private boolean isAllNumberIn(String input) {
		// update label
		totalNumberLabel.setText("Enter ID Number (" + input.length() + ")");
		// check every char in input String
		for (int i = 0; i < input.length(); i++) {
			char aChar = input.charAt(i);

			if (!Character.isDigit(aChar)) return false;
		}
		return true;
	}

	private boolean isDuplicate(String text, ArrayList<String> list) {
		for (String aString : list) {
			if (aString.equals(text)) return true;
		}
		return false;
	}

	private void setMessage(String message, Color color) {
		messageLabel.setText(message);
		messageLabel.setForeground(color);
	}

	public void run(Point point) {
		setMinimumSize(new Dimension(0, 0));
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
