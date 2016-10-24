package com.kamontat.gui;

import com.kamontat.code.constant.Status;
import com.kamontat.code.font.FontBook;
import com.kamontat.code.object.IDNumber;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.kamontat.code.database.Database.*;
import static com.kamontat.code.window.Display.getCenterLocation;
import static com.kamontat.gui.MainPage.backMenu;
import static com.kamontat.gui.MainPage.exitMenu;

public class EnterPage extends JDialog {
	private JPanel contentPane;
	private JButton okBtn;
	private JButton cancelBtn;
	private JTextField textField;
	private JLabel totalNumberLabel;
	private JLabel messageLabel;
	private JLabel label1;
	
	private IDNumber number = new IDNumber();
	
	public EnterPage() {
		setContentPane(contentPane);
		setModal(true);
		createMenuBar();
		addFont();
		
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
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		assignIDList();
		
		idList.add(number);
		insertToFile(number);
		setMessage("Collect ID (Saved)", new Color(0, 122, 255));
		textField.selectAll();
		
		pack();
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	private void onCancel() {
		dispose();
	}
	
	private void warn() {
		if (textField.getText().equals("")) {
			setMessage("Enter (enter some id)", new Color(0, 0, 0));
		} else if (isAllNumberIn(textField.getText())) {
			// update label
			totalNumberLabel.setText(String.format("(%02d)", textField.getText().length()));
			
			number = new IDNumber(textField.getText());
			
			if (number.getStatus() != Status.OK) {
				okBtn.setEnabled(false);
				setMessage(number.getStatus().toString(), number.getStatus().getColor());
			} else {
				// id haven't 13 character
				if (new IDNumber(textField.getText()).isDuplicate()) {
					okBtn.setEnabled(false);
					setMessage("Error (Duplicate ID)", new Color(255, 0, 0));
				} else {
					okBtn.setEnabled(true);
					setMessage("OK (Good ID)", new Color(0, 200, 0));
				}
			}
			// have some String too. :(
		} else {
			okBtn.setEnabled(false);
			setMessage("Error (Have Alphabet)", new Color(255, 0, 0));
		}
		
		pack();
	}
	
	public static boolean isAllNumberIn(String input) {
		// check every char in input String
		for (int i = 0; i < input.length(); i++) {
			char aChar = input.charAt(i);
			if (!Character.isDigit(aChar)) return false;
		}
		return true;
	}
	
	private void setMessage(String message, Color color) {
		messageLabel.setText(message);
		messageLabel.setForeground(color);
	}
	
	private void addFont() {
		label1.setFont(FontBook.getFontLabel());
		messageLabel.setFont(FontBook.getFontLabel());
		
		totalNumberLabel.setFont(FontBook.getDigitalFont());
		
		textField.setFont(FontBook.getFontTextField());
		
		okBtn.setFont(FontBook.getFontButton());
		cancelBtn.setFont(FontBook.getFontButton());
	}
	
	/**
	 * create new menu bar with unit item inside it.
	 */
	private void createMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu actions = new JMenu("Action");
		
		actions.add(showMenu());
		actions.addSeparator();
		actions.add(backMenu(this));
		actions.add(exitMenu());
		
		menu.add(actions);
		setJMenuBar(menu);
	}
	
	private JMenuItem showMenu() {
		JMenuItem add = new JMenuItem("Show all ID");
		add.addActionListener(e -> {
			dispose();
			ShowPage page = new ShowPage();
			page.run(getCenterLocation(page.getSize()));
		});
		return add;
	}
	
	public void run(Point point) {
		pack();
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
