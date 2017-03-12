package com.kamontat.code.test;

import com.kamontat.code.constant.Status;
import com.kamontat.code.model.DatabaseModel;
import com.kamontat.code.object.IDNumber;
import com.kamontat.gui.popup.LoadingPopup;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author kamontat
 * @since 17/8/59 - 21:12
 */
public class AssignID extends Observable {
	DatabaseModel database;
	
	public AssignID(DatabaseModel db) {
		this.database = db;
	}
	
	public void assign(int number) {
		// assign all variable
		LoadingPopup popup = new LoadingPopup(null);
		Random random = new Random(System.nanoTime());
		Arrays.sort(new int[]{0, 1});
		// END
		
		// set observer
		addObserver(popup);
		popup.showPage(number);
		// END
		
		// set observer changing
		setChanged();
		notifyObservers("Start assign temp id");
		// END
		
		for (int i = 0; i < number; i++) {
			// create id_number until it's correct
			
			// random id_number
			IDNumber id = new IDNumber(String.valueOf(Math.abs(random.nextLong())).substring(0, 12));
			while (id.getStatus() == Status.NOT_CORRECT || id.getStatus() == Status.OUT_LENGTH) {
				id = new IDNumber(String.valueOf(Math.abs(random.nextLong())).substring(0, 12));
				id.setId(id.getId() + lastDigit(id.getId()));
			}
			// END
			
			// random time and set
			String timeText = String.format("%02d-%02d-%02dT%02d:%02d:%02d", random(1980, 2016), random(1, 12), random(1, 28), random(0, 23), random(1, 59), random(1, 59));
			LocalDateTime dataTime = LocalDateTime.parse(timeText);
			id.setCreateAt(dataTime);
			// END
			
			// END
			
			// add into database
			database.add(id);
			// END
			
			// set observer changing
			setChanged();
			notifyObservers(i);
			// END
		}
		
		popup.hidePage(false);
		System.out.println("Done.");
	}
	
	/**
	 * this method will random number from 0-123456
	 */
	public void assign() {
		Random random = new Random(System.nanoTime());
		assign(random.nextInt() % 123456);
	}
	
	private int lastDigit(String id) {
		char[] splitID = id.toCharArray();
		int total = 0;
		
		for (int i = 1; i <= 12; i++) {
			int digit = Character.getNumericValue(splitID[i - 1]);
			total += (14 - i) * digit;
		}
		
		total = (total % 11);
		if (total <= 1) {
			return 1 - total;
		} else if (total > 1) {
			return 11 - total;
		}
		return 0;
	}
	
	private int random(int start, int stop) {
		Random rand = new Random(System.nanoTime());
		return (int) Math.round(Math.abs((start + (rand.nextInt(stop - start)))));
	}
}
