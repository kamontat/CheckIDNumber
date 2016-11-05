package test;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/24/2016 AD - 2:40 PM
 */
public class test {
	public static void main(String[] args) throws InterruptedException {
		Thread[] arr = new Thread[3];
		
		arr[0] = new Thread() {
			@Override
			public void run() {
				super.run();
				for (int i = 0; i < 20; i++) {
					System.out.println(i);
				}
			}
		};
		
		arr[1] = new Thread() {
			@Override
			public void run() {
				super.run();
				for (int i = 20; i < 50; i++) {
					System.out.println(i);
				}
			}
		};
		
		arr[2] = new Thread() {
			@Override
			public void run() {
				super.run();
				for (int i = 50; i < 100; i++) {
					System.out.println(i);
				}
			}
		};
		
		
		for (Thread thread : arr) {
			thread.run();
		}
		
		for (Thread thread : arr) {
			thread.join();
		}
	}
}
