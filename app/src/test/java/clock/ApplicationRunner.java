package clock;

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
	
	/*
    void hasShownCountdownTimerDecrementingEverySecondFor(Integer value) throws InterruptedException {
    	final var signal = new CountDownLatch(value);
    	signal.countDown();
    	for(int i = 0; i <= value; i++) {
    		driver.showCountdownTimerWithValue(value-i);
			signal.await(1,TimeUnit.SECONDS);
		}
	}*/

	void close() throws Exception {
		if (driver != null)
			driver.dispose();
	}
}
