package clock.unit;

import static clock.domain.CountdownTimerStatus.INITIALIZED;
import static clock.domain.CountdownTimerStatus.STARTED;
import static clock.domain.CountdownTimerStatus.RUNNING;
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

import clock.domain.CountdownTimerState;
import clock.domain.CountdownTimerStateChangeListener;
import clock.domain.CountdownTimerStatus;
import clock.domain.SimpleCountdownTimer;

@DisplayName("Countdown Timer Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountdownTimerTest {

	private final Mockery context = new Mockery();
	private final DeterministicExecutor executor = new DeterministicExecutor();
	private final CountdownTimerStateChangeListener listener = context.mock(CountdownTimerStateChangeListener.class);
	private final SimpleCountdownTimer countdownTimer = new SimpleCountdownTimer(executor, listener);
	private final static Integer INITIAL_VALUE = 2;
	private final static Integer STOP_VALUE = 0;
	private final States state = context.states("state").startsAs("Initialized");

	@Test
	@Order(1)
	void notifiesInitializationWhenCountdownTimerInitializes() {

		context.checking(new Expectations() {
			{
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);

		context.assertIsSatisfied();

		assertThat(currentValue(), equalTo(INITIAL_VALUE));
		assertThat(runCount(), equalTo(0));
	}

	@Test
	@Order(2)
	void notifiesStartgWhenCountdownTimerStarts() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				then(state.is("Initialized"));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				when(state.is("Initialized"));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.start();

		context.assertIsSatisfied();

		assertThat(currentValue(), equalTo(INITIAL_VALUE));
		assertThat(runCount(), equalTo(0));
	}

	@Test
	@Order(3)
	void notifiesRunningWhenCountdownTimerRuns() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				then(state.is("Started"));
				exactly(INITIAL_VALUE + 1).of(listener)
						.countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				when(state.is("Started"));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.start();
		countdownTimer.run();
		executor.runUntilIdle();

		context.assertIsSatisfied();

		assertThat(currentValue(), equalTo(STOP_VALUE));
		assertThat(runCount(), equalTo(1));
	}

	@Test
	@Order(4)
	void notifiesPauseWhenCountdownTimerPauses() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				then(state.is("Running"));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(PAUSED)));
				when(state.is("Running"));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.start();
		countdownTimer.run();
		executor.runUntilIdle();
		countdownTimer.pause();

		context.assertIsSatisfied();

		assertThat(runCount(), equalTo(1));
	}

	@Test
	@Order(5)
	void notifiesResumeWhenCountdownTimerResumes() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(PAUSED)));
				then(state.is("Paused"));
				atLeast(1).of(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				when(state.is("Paused"));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.pause();
		countdownTimer.resume();
		executor.runUntilIdle();

		context.assertIsSatisfied();
	}

	@Test
	@Order(6)
	void notifiesStopWhenCountdownTimerStops() {

		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				then(state.is("Running"));
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
				when(state.is("Running"));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.start();
		countdownTimer.run();
		executor.runUntilIdle();
		countdownTimer.stop();

		context.assertIsSatisfied();

		assertThat(runCount(), equalTo(1));
		assertThat(currentValue(), equalTo(0));
	}

	private Integer currentValue() {
		return countdownTimer.getCurrentState().value();
	}

	private Integer runCount() {
		return countdownTimer.getCurrentState().runCount();
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
