package com.kamontat.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import static com.kamontat.main.Main.idList;
import static com.kamontat.main.Main.updateTextFile;

public class ShowPage extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JList<String> list;
	private JTextField searchingField;
	private JLabel countLabel;

	private JMenuItem[] itemList = new JMenuItem[2];

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

		DefaultListModel<String> model = new DefaultListModel<>();
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
					Point selectedPoint = list.indexToLocation(list.getSelectedIndex());

					menu.show(list, (int) selectedPoint.getX(), (int) selectedPoint.getY());
				}
			}
		});

		assignPopupList((DefaultListModel<String>) list.getModel());

		countLabel.setText(String.format("(%d)", model.size()));

		assignSearching((DefaultListModel<String>) list.getModel());
	}

	private void assignPopupList(DefaultListModel<String> model) {
		itemList[0] = new JMenuItem("Add");
		itemList[0].addActionListener(e1 -> {
			dispose();
			EnterPage page = new EnterPage();
			page.run(this.getLocation());
		});

		itemList[1] = new JMenuItem("Remove");
		itemList[1].addActionListener(e1 -> {
			int index = list.getSelectedIndex();
			idList.remove(index);
			model.remove(index);
			updateTextFile();

			countLabel.setText(String.format("(%d)", model.size()));
		});
	}

	private void assignSearching(DefaultListModel<String> model) {
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
				for (String s : idList) {
					if (!s.startsWith(filter)) {
						if (model.contains(s)) {
							model.removeElement(s);
						}
					} else {
						if (!model.contains(s)) {
							model.addElement(s);
						}
					}
				}
				countLabel.setText(String.format("(%d)", model.size()));
			}
		});
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
