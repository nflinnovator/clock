/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package clock;

import static clock.domain.CountdownTimer.COUNTDOWN_TIMER_DURATION_INITIAL_VALUE_IN_SECONDS;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@DisplayName("Countdown Timer Acceptance (End to End) Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountdownTimerAcceptanceTests {

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_ON = "ON";
	
	private final ApplicationRunner application = new ApplicationRunner();

	@BeforeEach
	void setupApplication() throws Exception {
		application.setup();
	}

	@Test
	@Order(1)
	void onceOpenedDisplaysAFrozenCountdownTimerWithInitialValue() {
		application.launches();
		application.showsCountdownTimerWithValue(COUNTDOWN_TIMER_DURATION_INITIAL_VALUE_IN_SECONDS);
		application.showsCountdownTimerStatus(STATUS_OFF);
	}
	
	@Test
	@Order(2)
	void startCountdownTimerAndItUpdatesCountdownTimerStatus() {
		application.launches();
		application.startsCountdownTimer();
		application.showsCountdownTimerStatus(STATUS_ON);
	}
	
	/*
	@Test
	@Order(3)
	void startCountdownTimerAndItStartsDecrementingEverySecond() throws InterruptedException {
		application.launches();
		application.startsCountdownTimer();
		application.showsCountdownTimerStatus(STATUS_ON);
		application.hasShownCountdownTimerDecrementingEverySecondFor(COUNTDOWN_TIMER_DURATION_DEFAULT_VALUE_IN_SECONDS);
		application.showsCountdownTimerStatus(STATUS_OFF);
		application.showsCountdownTimerWithValue(COUNTDOWN_TIMER_FINAL_VALUE);
	}*/

	@AfterEach
	void closeApplication() throws Exception {
		application.close();
	}

}
