package com.kamontat.gui;

import com.kamontat.code.constant.Status;
import com.kamontat.code.file.Provinces;
import com.kamontat.code.font.FontBook;
import com.kamontat.code.object.IDNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InformationPage extends JDialog {
	private ShowPage page;

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

	public InformationPage(IDNumber id) {
		setContentPane(contentPane);
		setModal(true);
		addFont();
		setInformation(id);

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOK();
			}
		});

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
		page.run(this.getLocation());
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
	}

	private void setInformation(IDNumber id) {
		typeLabel.setText(id.getType());
		provinceLabel.setText(Provinces.getProvinceAndDistrict(id)[0]);
		districtLabel.setText(Provinces.getProvinceAndDistrict(id)[1]);
		numBCLabel.setText(id.getIDBC());
		orderLabel.setText(id.getIDOrder());

		if (id.getStatusMessage() == Status.NOT_CREATE) {
			// impossible
			setStatus("Not Create Yet!", new Color(255, 0, 0));
		} else if (id.getStatusMessage() == Status.OUT_LENGTH) {
			// impossible
			setStatus("NOT 13 Digit!?", new Color(255, 0, 0));
		} else if (id.getStatusMessage() == Status.UNCORRECTED) {
			// impossible
			setStatus("NOT Real ID Number", new Color(255, 0, 0));
		} else if (Provinces.getType() == com.kamontat.code.constant.Type.NO_DISTRICT) {
			setStatus("NO district", new Color(255, 228, 0));
		} else if (Provinces.getType() == com.kamontat.code.constant.Type.NO_EVERYTHING) {
			setStatus("NO province and district", new Color(255, 168, 0));
		} else if (Provinces.getType() == com.kamontat.code.constant.Type.OK && id.getStatusMessage() == Status.OK) {
			setStatus("Prefect ID!", new Color(0, 249, 255));
		}
	}

	private void setStatus(String message, Color color) {
		statusLabel.setText(message);
		statusLabel.setForeground(color);
	}

	public void keepPage(ShowPage page) {
		this.page = page;
	}

	public void run(Point point) {
		pack();
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
