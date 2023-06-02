package clock;

class ApplicationRunner {

	private final ApplicationDriver driver = new ApplicationDriver();

	void setup() throws Exception {
		if (driver != null)
			driver.setup();
	}

	void launch() {
		final var applicationTestThread = new Thread("Application Test Thread") {
			@Override
			public void run() {
				try {
					driver.launch(App.class, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		applicationTestThread.setDaemon(true);
		applicationTestThread.start();
	}

	void showsCountdownTimerWithDefaultValue() {
		driver.showsCountdownTimerWithDefaultValue();
	}

	void close() throws Exception {
		if (driver != null)
			driver.dispose();
	}
}
