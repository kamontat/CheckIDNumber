package com.kamontat.gui;

import com.kamontat.code.constant.Status;
import com.kamontat.code.file.Location;
import com.kamontat.code.font.FontBook;
import com.kamontat.code.object.IDNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
	private JLabel update_atLabel;

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
		if (id.getStatus() != Status.OK) {
			// impossible
			setStatus(id.getStatus().toString(), new Color(255, 0, 0));
		} else {
			typeLabel.setText(id.getType());
			provinceLabel.setText(Location.getProvinceAndDistrict(id)[0]);
			districtLabel.setText(Location.getProvinceAndDistrict(id)[1]);
			numBCLabel.setText(id.getIDBC());
			orderLabel.setText(id.getIDOrder());
			update_atLabel.setText("update_at " + id.getTime().toLocalDate() + ", " + id.getTime().toLocalTime());

			Color color = Color.BLACK;
			if (Location.getType() == com.kamontat.code.constant.Type.NO_DISTRICT) {
				color = new Color(255, 228, 0);
			} else if (Location.getType() == com.kamontat.code.constant.Type.NO_EVERYTHING) {
				color = new Color(255, 168, 0);
			} else if (Location.getType() == com.kamontat.code.constant.Type.OK) {
				color = new Color(0, 249, 255);
			}
			setStatus(Location.getType().toString(), color);

			exPack(this);
		}
	}

	private void setStatus(String message, Color color) {
		statusLabel.setText(message);
		statusLabel.setForeground(color);
	}

	public void run(Point point) {
		pack();
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
