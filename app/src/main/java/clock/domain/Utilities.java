package clock.domain;

public class Utilities {
	
	public static void pauseForASecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
