package clock;

import static clock.CountdownTimerAcceptanceTests.COUNTDOWN_TIMER_STOP_VALUE;

import java.util.concurrent.CountDownLatch;

class ApplicationRunner {

	private final ApplicationDriver driver = new ApplicationDriver();

	void setup() throws Exception {
		if (driver != null)
			driver.setup();
	}

	void launches() {
		final var applicationTestThread = new Thread("Application Test Thread") {
			@Override
			public void run() {
				try {
					driver.startApplication();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		applicationTestThread.setDaemon(true);
		applicationTestThread.start();
	}

	void showsCountdownTimerWithValue(Integer value) {
		driver.showCountdownTimerWithValue(value);
	}
	
	void showsCountdownTimerStatus(String status) {
		driver.showCountdownTimerStatus(status);
	}
	
	void startsCountdownTimer() {
		driver.startCountdownTimer();
	}
	
    void hasShownCountdownTimerDecrementingEverySecondFor(Integer value) throws InterruptedException {
    	final var signal = new CountDownLatch(value);
    	signal.countDown();
    	driver.showCountdownTimerWithValue(COUNTDOWN_TIMER_STOP_VALUE);
	}

	void close() throws Exception {
		if (driver != null)
			driver.dispose();
	}
}
