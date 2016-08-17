package com.kamontat.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

import static com.kamontat.gui.MainPage.exitMenu;
import static com.kamontat.gui.MainPage.exportMenu;
import static com.kamontat.main.Main.idList;
import static com.kamontat.main.Main.updateTextFile;

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
		createMenuBar();

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
		warn();

		if (!isDuplicate(textField.getText(), idList) && okBtn.isEnabled()) {
			idList.add(textField.getText());
			updateTextFile();
			setMessage("Correct ID (Saved)", new Color(0, 122, 255));
			textField.selectAll();
		}
		pack();
	}

	private void onCancel() {
		dispose();
	}

	private void warn() {
		if (isAllNumberIn(textField.getText())) {
			if (textField.getText().length() == 13) {
				if (!isDuplicate(textField.getText(), idList)) {
					if (checkIDCorrection(textField.getText())) {
						okBtn.setEnabled(true);
						setMessage("OK (Good ID)", new Color(0, 200, 0));
					} else {
						// 13th digit is not match with id theorem
						okBtn.setEnabled(false);
						setMessage("Error (13th Digit Wrong)", new Color(195, 0, 255));
					}
				} else {
					// id have duplicate with the old one
					okBtn.setEnabled(false);
					setMessage("Error (Duplicate ID)", new Color(255, 0, 0));
				}
			} else {
				// id haven't 13 character
				okBtn.setEnabled(false);
				setMessage("Warning (not equal 13)", new Color(255, 189, 0));
			}
		} else {
			// have some String too. :(
			okBtn.setEnabled(false);
			setMessage("Error (Have Alphabet)", new Color(255, 0, 0));
		}

		pack();
	}

	private boolean isAllNumberIn(String input) {
		// update label
		totalNumberLabel.setText(String.format("(%02d)", input.length()));
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

	private boolean checkIDCorrection(String id) {
		int total = 0;
		for (int i = 1; i <= 12; i++) {
			int digit = Integer.parseInt(id.substring(i - 1, i));
			total += (14 - i) * digit;
		}
		total = (total % 11);
		int lastDigit = Integer.parseInt(id.substring(id.length() - 1, id.length()));
		if (total <= 1) {
			if (lastDigit == 1 - total) {
				return true;
			}
		} else if (total > 1) {
			if (lastDigit == 11 - total) {
				return true;
			}
		}
		return false;
	}

	private void setMessage(String message, Color color) {
		messageLabel.setText(message);
		messageLabel.setForeground(color);
	}

	/**
	 * create new menu bar with unit item inside it.
	 */
	private void createMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu units = new JMenu("Action");

		units.add(exportMenu());
		units.addSeparator();
		units.add(exitMenu());

		menu.add(units);
		setJMenuBar(menu);
	}

	public void run(Point point) {
		pack();
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
