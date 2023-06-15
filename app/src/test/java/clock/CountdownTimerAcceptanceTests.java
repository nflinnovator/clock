/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package clock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@DisplayName("Countdown Timer Acceptance (End to End) Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountdownTimerAcceptanceTests {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_ON = "ON";
	public static final String STATUS_PAUSE = "PAUSED";
	public static final String STATUS_STOPPED = "STOPPED"; 
	public static final String OFF_BUTTON_TEXT = "Start";
	public static final String ON_BUTTON_TEXT = "Pause";
	public static final String PAUSED_BUTTON_TEXT = "Resume";
	public static final String STOPPED_BUTTON_TEXT = "Restart";
	public static final Integer RUN_COUNT = 0;
	public static final Integer COUNTDOWN_TIMER_INITIAL_VALUE = 5; 
	public static final Integer COUNTDOWN_TIMER_STOP_VALUE = 0;
	 
	private final ApplicationRunner application = new ApplicationRunner();

	@BeforeEach
	void setupApplication() throws Exception {
		application.setup();
	}

	@Test
	@Order(1)
	void onceOpenedDisplaysAFrozenCountdownTimerWithInitialValues() {
		application.launches();
		application.showsCountdownTimerWithValues(COUNTDOWN_TIMER_INITIAL_VALUE,RUN_COUNT,STATUS_OFF);
	} 
	
	@Test
	@Order(2)
	void startCountdownTimerAndItUpdatesCountdownTimerStatus() {
		application.launches();
		application.showsCountdownTimerWithValues(COUNTDOWN_TIMER_INITIAL_VALUE,RUN_COUNT,STATUS_OFF);
		application.showsStartButtonWithText(OFF_BUTTON_TEXT);
		application.startsCountdownTimer();
		application.showsCountdownTimerWithStatus(STATUS_ON);
		application.showsStartButtonWithText(ON_BUTTON_TEXT);
		application.showsCountdownTimerWithRunCount(RUN_COUNT + 1);
	}
	
	@Test
	@Order(3)
	void startCountdownTimerForTheFirstTimeAndItStartsDecrementingEverySecond(){
		application.launches();
		application.startsCountdownTimer();
		application.hasShownCountdownTimerDecrementingEverySecondFor(COUNTDOWN_TIMER_INITIAL_VALUE);
		application.showsCountdownTimerWithValues(COUNTDOWN_TIMER_STOP_VALUE,RUN_COUNT+1,STATUS_STOPPED);
		application.showsStartButtonWithText(STOPPED_BUTTON_TEXT);
	}
	
	@Test
	@Order(4)
	void startCountdownTimerNotForTheFirstTimeAndItStartsWithTheRightInitialValue() throws InterruptedException {
		application.launches();
		application.startsCountdownTimer();
		application.showsCountdownTimerWithRunCount(RUN_COUNT + 1);
		application.hasShownCountdownTimerDecrementingEverySecondFor(COUNTDOWN_TIMER_INITIAL_VALUE);
		application.showsCountdownTimerWithValues(COUNTDOWN_TIMER_STOP_VALUE,RUN_COUNT+1,STATUS_STOPPED);
		application.pause();
		application.startsCountdownTimer();
		application.showsCountdownTimerWithValues(COUNTDOWN_TIMER_INITIAL_VALUE,RUN_COUNT+2,STATUS_ON);
	}
	
	@Test
	@Order(5)
	void countdownTimerPausesWhenPauseButtonIsClicked() throws InterruptedException {
		application.launches();
		application.startsCountdownTimer();
		application.pausesCountdownTimer();
		application.showsCountdownTimerWithStatus(STATUS_PAUSE);
		application.showsStartButtonWithText(PAUSED_BUTTON_TEXT);
		application.showsCountdownTimerHasPaused();
	}
	
	@Test
	@Order(6)
	void countdownTimerResumesWhenResumeButtonIsClicked() throws InterruptedException {
		application.launches();
		application.startsCountdownTimer();
		application.pausesCountdownTimer();
		application.showsCountdownTimerHasPaused();
		application.resumesCountdownTimer();
		application.showsCountdownTimerWithStatus(STATUS_ON);
		application.showsStartButtonWithText(ON_BUTTON_TEXT);
		application.hasShownCountdownTimerDecrementingEverySecondFor(COUNTDOWN_TIMER_INITIAL_VALUE-1);
	}
	
	@Test
	@Order(7)
	void countdownTimerStopsWhenStopButtonIsClicked() throws InterruptedException {
		application.launches();
		application.startsCountdownTimer();
		application.stopsCountdownTimer();
		application.showsCountdownTimerWithValues(COUNTDOWN_TIMER_STOP_VALUE,RUN_COUNT+1,STATUS_STOPPED);
		application.showsStartButtonWithText(STOPPED_BUTTON_TEXT);
	}
	
	/*
	@Test
	@Order(8)
	void stopCountdownTimerOnlyWhenItHasStarted() throws InterruptedException {
		application.launches();
		application.hasDisabledStopButton();
		application.startsCountdownTimer();
		application.stopsCountdownTimer();
	}*/
	
	// Launch the app with CLI arguments

	@AfterEach
	void closeApplication() throws Exception {
		application.close();
	}

}
