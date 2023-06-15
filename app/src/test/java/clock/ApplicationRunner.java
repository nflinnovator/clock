package clock;

import static clock.CountdownTimerAcceptanceTests.COUNTDOWN_TIMER_INITIAL_VALUE;
import static clock.CountdownTimerAcceptanceTests.COUNTDOWN_TIMER_STOP_VALUE;

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
	
	void pause() {
		driver.sleep(500); 
	}
	
	void showsCountdownTimerWithValues(Integer currentValue,Integer runCount,String status) {
		driver.showsCountdownTimerWithValues(currentValue,runCount,status);
	}

	void showsCountdownTimerWithValue(Integer value) {
		driver.showCountdownTimerWithValue(value);
	}
	
	void showsCountdownTimerWithRunCount(Integer runCount) {
		driver.showCountdownTimerWithRunCount(runCount);
	}
	
	void showsCountdownTimerWithStatus(String status) {
		driver.showCountdownTimerWithStatus(status);
	}
	
	void startsCountdownTimer() {
		driver.startCountdownTimer();
	}
	
	void pausesCountdownTimer() {
		driver.pausesCountdownTimer();
	}
	
	void resumesCountdownTimer() {
		driver.resumesCountdownTimer();
	}
	
	void stopsCountdownTimer() {
		driver.stopsCountdownTimer();
	}
	
	void hasDisabledStopButton() {
		driver.hasDisabledStopButton();
	}
	
    void hasShownCountdownTimerDecrementingEverySecondFor(Integer value){
    	driver.sleep(value*1000);
    	driver.showCountdownTimerWithValue(COUNTDOWN_TIMER_STOP_VALUE);
	}
    
    void showsStartButtonWithText(String text){
    	driver.showsStartButtonWithText(text);
    }
    
    void showsCountdownTimerHasPaused() {
    	driver.showCountdownTimerWithValue(COUNTDOWN_TIMER_INITIAL_VALUE - 1);
    	driver.sleep(2000);
    	driver.showCountdownTimerWithValue(COUNTDOWN_TIMER_INITIAL_VALUE - 1);
    }

	void close() throws Exception {
		if (driver != null)
			driver.dispose();
	}
}
