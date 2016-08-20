package com.kamontat.gui;

import com.kamontat.code.font.FontBook;
import com.kamontat.code.object.IDNumber;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import static com.kamontat.code.file.TextFile.updateTextFile;
import static com.kamontat.code.window.Display.getCenterLocation;
import static com.kamontat.gui.MainPage.exitMenu;
import static com.kamontat.gui.MainPage.exportMenu;
import static com.kamontat.main.Main.idList;
import static com.kamontat.main.Main.searchingIDList;

public class ShowPage extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JList<IDNumber> list;
	private JTextField searchingField;
	private JLabel countLabel;
	private JLabel label1;

	private JMenuItem[] itemList = new JMenuItem[2];

	public ShowPage() {
		setContentPane(contentPane);
		setModal(true);

		assignList();
		createMenuBar();
		addFont();

		pack();

		buttonOK.addActionListener(e -> onOK());

		buttonCancel.addActionListener(e -> onCancel());

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onOK() {
		dispose();
	}

	private void onCancel() {
		dispose();
	}

	private void assignList() {

		DefaultListModel<IDNumber> model = new DefaultListModel<>();
		idList.forEach(model::addElement);

		list.setModel(model);

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				// right button be click
				if (e.getButton() == 3) {
					JPopupMenu menu = new JPopupMenu();
					Arrays.stream(itemList).forEach(menu::add);
					if (list.getSelectedIndex() != -1) {
						Point selectedPoint = list.indexToLocation(list.getSelectedIndex());

						menu.show(list, (int) selectedPoint.getX(), (int) selectedPoint.getY());
					}
				}
			}
		});

		assignPopupList((DefaultListModel<IDNumber>) list.getModel());

		assignSearching((DefaultListModel<IDNumber>) list.getModel());

		countLabel.setText(String.format("(%03d)", model.size()));
	}

	private void assignPopupList(DefaultListModel<IDNumber> model) {
		itemList[0] = new JMenuItem("Add");
		itemList[0].addActionListener(e1 -> {
			dispose();
			EnterPage page = new EnterPage();
			page.run(this.getLocation());
		});

		itemList[1] = new JMenuItem("Remove");
		itemList[1].addActionListener(e1 -> {

			int index = searchingIDList(list.getSelectedValue());

			idList.remove(index);
			model.remove(list.getSelectedIndex());
			updateTextFile();

			countLabel.setText(String.format("(%03d)", model.size()));
		});
	}

	private void assignSearching(DefaultListModel<IDNumber> model) {
		searchingField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filter();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filter();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filter();
			}

			private void filter() {
				String filter = searchingField.getText();
				for (IDNumber s : idList) {
					if (!s.getId().startsWith(filter)) {
						if (model.contains(s)) {
							model.removeElement(s);
						}
					} else {
						if (!model.contains(s)) {
							model.addElement(s);
						}
					}
				}
				countLabel.setText(String.format("(%03d)", model.size()));
			}
		});
	}

	private void addFont() {
		label1.setFont(FontBook.getFontLabel());

		countLabel.setFont(FontBook.getDigitalFont());

		searchingField.setFont(FontBook.getFontTextField());

		list.setFont(FontBook.getFontList());

		buttonOK.setFont(FontBook.getFontButton());
		buttonCancel.setFont(FontBook.getFontButton());
	}

	/**
	 * create new menu bar with unit item inside it.
	 */
	private void createMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu actions = new JMenu("Action");

		actions.add(addMenu());
		actions.add(clearMenu());
		actions.addSeparator();
		actions.add(exportMenu());
		actions.addSeparator();
		actions.add(exitMenu());

		menu.add(actions);
		setJMenuBar(menu);
	}

	private JMenuItem addMenu() {
		JMenuItem add = new JMenuItem("Add ID");
		add.addActionListener(e -> {
			dispose();
			EnterPage page = new EnterPage();
			page.run(getCenterLocation(page.getSize()));
		});
		return add;
	}

	private JMenuItem clearMenu() {
		JMenuItem clear = new JMenuItem("Clear History");
		clear.addActionListener(e -> {
			idList.removeAll(idList);

			DefaultListModel<IDNumber> model = (DefaultListModel<IDNumber>) list.getModel();
			model.removeAllElements();
			updateTextFile();

			countLabel.setText(String.format("(%03d)", model.size()));
		}); /* clear action */
		return clear;
	}

	public void run(Point point) {
		pack();
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
