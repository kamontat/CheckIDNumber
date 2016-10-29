package com.kamontat.gui;

import com.kamontat.code.database.LocationModel;
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

import static com.kamontat.code.database.Database.*;
import static com.kamontat.code.menu.MenuItem.*;
import static com.kamontat.code.window.Display.getCenterLocation;
import static com.kamontat.gui.MainPage.exPack;

public class ShowPage extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JList<IDNumber> list;
	private JTextField searchingField;
	private JLabel countLabel;
	private JLabel label1;
	
	private DefaultListModel<IDNumber> model = new DefaultListModel<>();
	
	public ShowPage() {
		setContentPane(contentPane);
		setModal(true);
		
		assignList();
		createMenuBar();
		addFont();
		
		exPack(this);
		
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
		idList.forEach(model::addElement);
		
		list.setModel(model);
		
		JMenuItem[] items = assignPopupList();
		
		assignSearching();
		
		disableSearch();
		
		countLabel.setText(String.format("(%03d)", model.size()));
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				// right button be click
				if (e.getButton() == 3) {
					JPopupMenu menu = new JPopupMenu();
					Arrays.stream(items).forEach(menu::add);
					
					if (list.getSelectedIndex() != -1) {
						Point selectedPoint = list.indexToLocation(list.getSelectedIndex());
						
						menu.show(list, (int) selectedPoint.getX(), (int) selectedPoint.getY());
					}
				}
			}
		});
	}
	
	private JMenuItem[] assignPopupList() {
		int i, j = 0;
		if (LocationModel.readable) i = 3;
		else i = 2;
		
		JMenuItem[] itemList = new JMenuItem[i];
		
		
		if (LocationModel.readable) {
			itemList[j] = new JMenuItem("Information");
			itemList[j++].addActionListener(e1 -> {
				InformationPage page = new InformationPage(this, list.getSelectedValue());
				page.run(getCenterLocation(page.getSize()));
			});
		}
		
		itemList[j] = new JMenuItem("Add");
		itemList[j++].addActionListener(e1 -> {
			dispose();
			EnterPage page = new EnterPage();
			page.run(this.getLocation());
		});
		
		itemList[j] = new JMenuItem("Remove");
		itemList[j].addActionListener(e1 -> {
			
			int index = searchingIDList(list.getSelectedValue());
			remove(index);
		});
		
		return itemList;
	}
	
	private void assignSearching() {
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
				long start = System.currentTimeMillis();
				String filter = searchingField.getText();
				if (EnterPage.isAllNumberIn(filter)) {
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					searchingField.setBackground(Color.WHITE);
					for (IDNumber s : idList) {
						int index = model.indexOf(s);
						if (!s.isContain(filter)) {
							if (index != -1) {
								model.remove(index);
							}
						} else {
							if (index == -1) {
								model.addElement(s);
							}
						}
					}
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					countLabel.setText(String.format("(%03d)", model.size()));
				} else {
					searchingField.setBackground(Color.RED);
				}
				System.out.println("searching time: " + (System.currentTimeMillis() - start));
			}
		});
	}
	
	/**
	 * disable searching if id more than 1000
	 */
	private void disableSearch() {
		// disable searching if id more than 50000
		if (model.size() > 50000) {
			searchingField.setEnabled(false);
			searchingField.setToolTipText("Search can't use, if id more than 1000");
		} else {
			searchingField.setEnabled(true);
			searchingField.setToolTipText("");
		}
	}
	
	public void removeIDList(int index) {
		idList.remove(index);
		model.remove(list.getSelectedIndex());
		updateTextFile();
		disableSearch();
		countLabel.setText(String.format("(%03d)", model.size()));
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
		
		actions.add(addMenu(this));
		actions.add(clearMenu());
		actions.addSeparator();
		actions.add(exportMenuXLS());
		actions.add(exportMenuXLSX());
		actions.addSeparator();
		actions.add(backMenu(this));
		actions.add(exitMenu());
		
		menu.add(actions);
		setJMenuBar(menu);
	}
	
	private JMenuItem clearMenu() {
		JMenuItem clear = new JMenuItem("Clear History");
		clear.addActionListener(e -> {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			idList.removeAll(idList);
			model.removeAllElements();
			clearFile();
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
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
