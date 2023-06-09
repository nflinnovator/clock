package clock.unit;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.adapters.input.CountdownTimerEventNotifier;
import clock.domain.countdowntimer.CountdownTimerManager;
import clock.domain.countdowntimer.CountdownTimerEventSender;

@DisplayName("CountdownTimerEventNotifier Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountdownTimerEventNotifierTest {
	private final Mockery context = new Mockery();
	private final CountdownTimerManager countdownTimer = context.mock(CountdownTimerManager.class);
	private final CountdownTimerEventSender eventNotifier = new CountdownTimerEventNotifier(countdownTimer);

	@Test
	@Order(1)
	void notifiesCountdownTimerInitializeWhenItReceivesOnInitializeMessage() {

		final var initialValue = 1;

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).initialize(initialValue);
			}
		});

		eventNotifier.onInitialize(initialValue);

		context.assertIsSatisfied();
	}

	@Test
	@Order(2)
	void notifiesCountdownTimerStartWhenItReceivesOnStartMessage() {

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).start();
			}
		});

		eventNotifier.onStart();

		context.assertIsSatisfied();
	}

	@Test
	@Order(3)
	void notifiesCountdownTimerPauseWhenItReceivesOnPauseMessage() {

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).pause();
			}
		});

		eventNotifier.onPause();

		context.assertIsSatisfied();
	}

	@Test
	@Order(4)
	void notifiesCountdownTimerResumeWhenItReceivesOnResumeMessage() {

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).resume();
			}
		});

		eventNotifier.onResume();

		context.assertIsSatisfied();
	}

	@Test
	@Order(5)
	void notifiesCountdownTimerStopWhenStopCountdownTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(countdownTimer).stop();
			}
		});

		eventNotifier.onStop();

		context.assertIsSatisfied();
	}

}
