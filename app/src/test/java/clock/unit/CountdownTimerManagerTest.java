package clock.unit;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.domain.countdowntimer.CountdownTimer;
import clock.domain.countdowntimer.CountdownTimerManager;
import clock.domain.countdowntimer.DefaultCountdownTimerManager;

@DisplayName("CountdownTimerManager Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountdownTimerManagerTest {

	private final Mockery context = new Mockery();
	private final CountdownTimer countdownTimer = context.mock(CountdownTimer.class);
	private final CountdownTimerManager countdownTimerManager = new DefaultCountdownTimerManager(countdownTimer);

	@Test
	@Order(1)
	void initializeCountdownTimerWhenItReceivesOnInitializeMessage() {
		
		final var initialValue = 1;

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).initialize(initialValue);
			}
		});

		countdownTimerManager.initialize(initialValue);

		context.assertIsSatisfied();

	}
	
	@Test
	@Order(2)
	void runCountdownTimerWhenItReceivesOnStartMessage() {

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).countdown();
			}
		});

		countdownTimerManager.start();

		context.assertIsSatisfied();

	}
	
	@Test
	@Order(3)
	void pauseCountdownTimerWhenItReceivesOnPauseMessage() {

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).pause();
			}
		});

		countdownTimerManager.pause();

		context.assertIsSatisfied();

	}
	
	@Test
	@Order(4)
	void runCountdownTimerWhenItReceivesOnResumeMessage() {

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).countdown();
			}
		});

		countdownTimerManager.resume();

		context.assertIsSatisfied();

	}
	
	@Test
	@Order(5)
	void stopCountdownTimerWhenItReceivesOnStopMessage() {

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).stop();
			}
		});

		countdownTimerManager.stop();

		context.assertIsSatisfied();

	}

}
