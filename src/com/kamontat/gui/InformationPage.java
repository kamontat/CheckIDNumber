package com.kamontat.gui;

import com.kamontat.code.constant.Status;
import com.kamontat.code.model.DatabaseAPI;
import com.kamontat.code.font.FontBook;
import com.kamontat.code.object.IDNumber;
import com.kamontat.code.window.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.kamontat.gui.MainPage.exPack;

public class InformationPage extends JDialog {
	private JPanel contentPane;
	private JButton okBtn;
	
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	
	private JLabel typeLabel;
	private JLabel provinceLabel;
	private JLabel districtLabel;
	private JLabel numBCLabel;
	private JLabel orderLabel;
	private JLabel statusLabel;
	private JLabel createAtLabel;
	private JButton deleteBtn;
	
	private ShowPage parent;
	
	public InformationPage(ShowPage showPage, IDNumber id) {
		super(showPage, "Information Page");
		setModal(true);
		
		parent = showPage;
		
		setContentPane(contentPane);
		addFont();
		setInformation(id);
		
		okBtn.addActionListener(e -> onOK());
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onOK();
			}
		});
		
		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	private void onOK() {
		dispose();
	}
	
	private void addFont() {
		statusLabel.setFont(FontBook.getFontLabel());
		
		label1.setFont(FontBook.getFontLabel());
		label2.setFont(FontBook.getFontLabel());
		label3.setFont(FontBook.getFontLabel());
		label4.setFont(FontBook.getFontLabel());
		
		typeLabel.setFont(FontBook.getThaiFont(43f));
		provinceLabel.setFont(FontBook.getThaiFont(43f));
		districtLabel.setFont(FontBook.getThaiFont(43f));
		
		numBCLabel.setFont(FontBook.getDigitalFont());
		orderLabel.setFont(FontBook.getDigitalFont());
		
		okBtn.setFont(FontBook.getFontButton());
		deleteBtn.setFont(FontBook.getFontButton());
	}
	
	private void setInformation(IDNumber id) {
		if (id.getStatus() != Status.OK) {
			setStatus(id.getStatus().toString(), id.getStatus().getColor());
			deleteBtn.setVisible(true);
			deleteBtn.addActionListener(e -> {
				int index = DatabaseAPI.getDatabase(null).search_index_local(id);
				parent.removeIDList(index);
				dispose();
			});
			
			exPack(this);
		} else {
			typeLabel.setText(id.getIDGenre());
			provinceLabel.setText(id.getLocation().getProvince());
			districtLabel.setText(id.getLocation().getDistrict());
			numBCLabel.setText(id.getIDBC());
			orderLabel.setText(id.getIDOrder());
			createAtLabel.setText("create_at " + id.getCreateAt_string());
			
			Color color = Color.BLACK;
			if (id.getLocation().getType() == com.kamontat.code.constant.Type.NO_DISTRICT) {
				color = new Color(255, 228, 0);
			} else if (id.getLocation().getType() == com.kamontat.code.constant.Type.NO_EVERYTHING) {
				color = new Color(255, 168, 0);
			} else if (id.getLocation().getType() == com.kamontat.code.constant.Type.OK) {
				color = new Color(0, 249, 255);
			}
			setStatus(id.getLocation().getType().toString(), color);
			
			exPack(this);
		}
	}
	
	private void setStatus(String message, Color color) {
		statusLabel.setText(message);
		statusLabel.setForeground(color);
	}
	
	public void run() {
		pack();
		setLocation(Display.getCenterPage(parent, this));
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
