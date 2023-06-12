package clock.unit;

import static clock.domain.CountdownTimer.CountdownTimerStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.lib.concurrent.DeterministicExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.domain.CountdownTimer;
import clock.domain.CountdownTimer.CountdownTimerStatus;
import clock.domain.CountdownTimerListener;
import clock.domain.CountdownTimerState;

@DisplayName("Countdown Timer Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountdownTimerTest {

	private final Mockery context = new Mockery();
	private final Integer duration = 1;
	private final DeterministicExecutor executor = new DeterministicExecutor();
	private final CountdownTimerListener listener = context.mock(CountdownTimerListener.class);
	private final CountdownTimer timer = new CountdownTimer(duration, executor, listener);

	@Test
	@Order(1)
	void countdownTimerStartsInTheRightState() {
		assertThat(status(), equalTo(OFF));
		assertThat(currentValue(), equalTo(duration));
		assertThat(runCount(), equalTo(0));
	}

	@Test
	@Order(2)
	void notifiesRunningWhenCountdownTimerIsRunning() {

		context.checking(new Expectations() {
			{
				exactly(duration).of(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(ON)));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
			}
		});

		timer.onStart();
		executor.runUntilIdle();

		context.assertIsSatisfied();
		
		assertThat(runCount(), equalTo(1));
	}

	@Test
	@Order(3)
	void notifiesStoppedWhenCountdownTimerStops() {
		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(ON)));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
			}
		});

		timer.onStart();
		executor.runUntilIdle();

		context.assertIsSatisfied();

		assertThat(currentValue(), equalTo(0));
		assertThat(runCount(), equalTo(1));
	}

	@Test
	@Order(4)
	void notifiesPausedWhenReceivesCountdownTimerPausedEvent() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(ON)));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(PAUSED)));
			}
		});

		timer.onStart();
		timer.onPause();

		context.assertIsSatisfied();

		assertThat(runCount(), equalTo(1));
	}

	@Test
	@Order(5)
	void sendsNptificationWhenReceivesCountdownTimerResumedEvent() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(PAUSED)));
				atLeast(1).of(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(ON)));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
			}
		});

		timer.onPause();
		timer.onResume();
		executor.runUntilIdle();

		context.assertIsSatisfied();

		assertThat(status(), equalTo(STOPPED));
	}

	@Test
	@Order(6)
	void sendsNptificationWhenReceivesCountdownTimerStoppedEvent() {

		context.checking(new Expectations() {
			{
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
			}
		});

		timer.onStop();

		context.assertIsSatisfied();

		assertThat(status(), equalTo(STOPPED));
		assertThat(currentValue(), equalTo(0));
	}

	private CountdownTimerStatus status() {
		return timer.getCurrentState().status();
	}

	private Integer currentValue() {
		return timer.getCurrentState().currentValue();
	}

	private Integer runCount() {
		return timer.getCurrentState().runCount();
	}

	private Matcher<CountdownTimerState> aCountdownTimerThatIs(final CountdownTimerStatus status) {
		return new FeatureMatcher<CountdownTimerState, CountdownTimerStatus>(equalTo(status),
				"countdown timer that is ", "was") {
			@Override
			protected CountdownTimerStatus featureValueOf(CountdownTimerState actual) {
				return actual.status();
			}
		};
	}

}
