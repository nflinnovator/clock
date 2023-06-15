package clock.unit;

import static clock.domain.CountdownTimerStatus.OFF;
import static clock.domain.CountdownTimerStatus.ON;
import static clock.domain.CountdownTimerStatus.PAUSED;
import static clock.domain.CountdownTimerStatus.STOPPED;
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

import clock.domain.SimpleCountdownTimer;
import clock.domain.CountdownTimerListener;
import clock.domain.CountdownTimerState;
import clock.domain.CountdownTimerStatus;

@DisplayName("Countdown Timer Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountdownTimerTest {

	private final Mockery context = new Mockery();
	private final DeterministicExecutor executor = new DeterministicExecutor();
	private final CountdownTimerListener listener = context.mock(CountdownTimerListener.class);
	private final SimpleCountdownTimer timer = new SimpleCountdownTimer(executor, listener);
	private final static Integer INITIAL_VALUE = 1;
	private final States state = context.states("state").startsAs("Off"); 

	@Test
	@Order(1)
	void notifiesInitializationWhenCountdownTimerInitializes() {

		context.checking(new Expectations() {
			{
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(OFF)));
			}
		});

		timer.onInit(INITIAL_VALUE);

		context.assertIsSatisfied();

		assertThat(currentValue(), equalTo(INITIAL_VALUE));
		assertThat(runCount(), equalTo(0));
	}

	@Test 
	@Order(2)
	void notifiesRunningWhenCountdownTimerHasStarted() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(OFF)));
				atLeast(1).of(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(ON)));
				then(state.is("On"));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
				when(state.is("On"));
			}
		});

		timer.onInit(INITIAL_VALUE);
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
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(OFF)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(ON)));
				then(state.is("On"));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
				when(state.is("On"));
			}
		});

		timer.onInit(INITIAL_VALUE);
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
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(OFF)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(ON)));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(PAUSED)));
			}
		});

		final var pauseValue = 1;

		timer.onInit(INITIAL_VALUE);
		timer.onStart();
		timer.onPause(pauseValue);

		context.assertIsSatisfied();

		assertThat(runCount(), equalTo(1));
		assertThat(currentValue(), equalTo(pauseValue));
	}

	@Test
	@Order(5)
	void sendsNptificationWhenReceivesCountdownTimerResumedEvent() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(OFF)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(PAUSED)));
				then(state.is("Paused"));
				atLeast(1).of(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(ON)));
				when(state.is("Paused"));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
				then(state.is("Stopped"));
			}
		});

		final var pauseAndResumeValue = 1;

		timer.onInit(INITIAL_VALUE);
		timer.onStart();
		timer.onPause(pauseAndResumeValue);
		timer.onResume(pauseAndResumeValue);
		executor.runUntilIdle();

		context.assertIsSatisfied();

		assertThat(status(), equalTo(STOPPED));
	}

	@Test
	@Order(6)
	void sendsNptificationWhenReceivesCountdownTimerStoppedEvent() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(OFF)));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
				then(state.is("Stopped"));
			}
		});

		timer.onInit(INITIAL_VALUE);
		timer.onStart();
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
