package test;

import com.kamontat.code.constant.Status;
import com.kamontat.code.database.Database;
import com.kamontat.code.object.IDNumber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author kamontat
 * @since 17/8/59 - 21:12
 */
public class assignID {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter: ");
		int num = input.nextInt();

		File file = Database.textFile;
		Random random = new Random(System.nanoTime());

		try {
			FileWriter writer = new FileWriter(file);

			for (int i = 0; i < num; i++) {
				IDNumber number = new IDNumber(String.valueOf(Math.abs(random.nextLong())).substring(0, 12));
				while (number.getStatusMessage() == Status.UNCORRECTED || number.getStatusMessage() == Status.OUT_LENGTH) {
					number = new IDNumber(String.valueOf(Math.abs(random.nextLong())).substring(0, 12));
					number.setId(number.getId() + lastDigit(number.getId()));
				}

				String timeText = String.format("%02d-%02d-%02dT%02d:%02d:%02d", random(1980, 2016), random(1, 12), random(1, 28), random(0, 23), random(1, 59), random(1, 59));

				LocalDateTime dataTime = LocalDateTime.parse(timeText);

				number.setTime(dataTime);

				writer.write(number.saveFormat() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

	private static int lastDigit(String id) {
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

	private static int random(int start, int stop) {
		Random rand = new Random(System.nanoTime());
		return (int) Math.round(Math.abs((start + (rand.nextInt(stop - start)))));
	}
}