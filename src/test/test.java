package test;

import com.kamontat.gui.popup.LoadingPopup;

import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/24/2016 AD - 2:40 PM
 */
public class test {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int a = 0;
		while (a != -99) {
			System.out.println("new: ");
			a = input.nextInt();
			LoadingPopup popup = new LoadingPopup();
			popup.showPage(100);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			popup.hidePage(false);
		}
	}
}
