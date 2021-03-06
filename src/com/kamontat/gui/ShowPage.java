package com.kamontat.gui;

import com.kamontat.code.constant.FileExtension;
import com.kamontat.code.model.DatabaseAPI;
import com.kamontat.code.model.LocationModel;
import com.kamontat.code.model.ExcelModel;
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
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.kamontat.code.model.DatabaseAPI.idList;
import static com.kamontat.code.menu.MenuItem.backMenu;
import static com.kamontat.code.menu.MenuItem.exitMenu;
import static com.kamontat.code.window.Display.getCenterPage;
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
	
	private Frame parent;
	
	public ShowPage(Frame parent) {
		super(parent, "Show Page");
		this.parent = parent;
		setModal(true);
		
		setContentPane(contentPane);
		
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
				page.run();
			});
		}
		
		itemList[j] = new JMenuItem("Add");
		itemList[j++].addActionListener(e1 -> {
			dispose();
			EnterPage page = new EnterPage(parent);
			page.run();
		});
		
		itemList[j] = new JMenuItem("Remove");
		itemList[j].addActionListener(e1 -> {
			int index = DatabaseAPI.getDatabase(this).search_index_local(list.getSelectedValue());
			removeIDList(index);
		});
		
		return itemList;
	}
	
	private void assignSearching() {
		searchingField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}
			
			private void update() {
				ArrayList<IDNumber> tempList = filter();
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				model = new DefaultListModel<IDNumber>();
				tempList.forEach(model::addElement);
				list.setModel(model);
				
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				countLabel.setText(String.format("(%03d)", model.size()));
			}
			
			private ArrayList<IDNumber> filter() {
				ArrayList<IDNumber> tempIDList = new ArrayList<>();
				String filterText = searchingField.getText();
				if (EnterPage.isAllNumberIn(filterText)) {
					searchingField.setBackground(Color.WHITE);
					
					Predicate<IDNumber> filter = id -> id.isContain(filterText);
					Comparator<IDNumber> sortBy = (o1, o2) -> o1.getId().compareTo(o2.getId());
					Consumer<IDNumber> addNewList = tempIDList::add;
					
					idList.stream().filter(filter).sorted(sortBy).forEach(addNewList);
					
					return tempIDList;
				} else {
					searchingField.setBackground(Color.RED);
					return idList;
				}
			}
		});
	}
	
	/**
	 * disable searching if id more than 1000
	 */
	private void disableSearch() {
		// disable searching if id more than 500000
		if (model.size() > 500000) {
			searchingField.setEnabled(false);
			searchingField.setToolTipText("Search can't use, if id more than 1000");
		} else {
			searchingField.setEnabled(true);
			searchingField.setToolTipText("");
		}
	}
	
	public void removeIDList(int index) {
		model.remove(list.getSelectedIndex());
		DatabaseAPI.getDatabase(this).removeID(idList.get(index));
		
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
		
		actions.add(addMenu());
		actions.add(clearMenu());
		actions.addSeparator();
		actions.add(exportMenu(FileExtension.EXCEL));
		actions.add(exportMenu(FileExtension.EXCEL_NEW));
		actions.addSeparator();
		actions.add(backMenu(this));
		actions.add(exitMenu());
		
		menu.add(actions);
		setJMenuBar(menu);
	}
	
	private JMenuItem addMenu() {
		JMenuItem add = new JMenuItem("Add ID");
		add.addActionListener(e -> {
			dispose();
			EnterPage page = new EnterPage(parent);
			page.run();
		});
		return add;
	}
	
	private JMenuItem exportMenu(FileExtension t) {
		JMenuItem exportExcel = new JMenuItem("Export (" + t.extension + ")");
		exportExcel.addActionListener(e -> new ExcelModel(this).createExcelFile(t)); /* export action */
		return exportExcel;
	}
	
	private JMenuItem clearMenu() {
		JMenuItem clear = new JMenuItem("Clear History");
		clear.addActionListener(e -> {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			model.removeAllElements();
			DatabaseAPI.getDatabase(this).clearAll();
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			countLabel.setText(String.format("(%03d)", model.size()));
		}); /* clear action */
		return clear;
	}
	
	public void run() {
		pack();
		setLocation(getCenterPage(parent, this));
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
