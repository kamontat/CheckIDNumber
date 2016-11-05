package test;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/24/2016 AD - 2:40 PM
 */
public class test {
	public static void main(String[] args) throws InterruptedException {
		Thread[] arr = new Thread[10];
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Thread() {
				@Override
				public void run() {
					super.run();
					for (int i = (int) Math.ceil(Math.random() * 10); i < Math.random() * 30; i++) {
						System.out.println(i);
					}
				}
			};
		}
		
		for (Thread thread : arr) {
			thread.run();
			thread.join();
		}
	}
}
