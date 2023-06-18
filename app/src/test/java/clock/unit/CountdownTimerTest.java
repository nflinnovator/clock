package clock.unit;

import static clock.domain.CountdownTimerStatus.INITIALIZED;
import static clock.domain.CountdownTimerStatus.STARTED;
import static clock.domain.CountdownTimerStatus.RUNNING;
import static clock.domain.CountdownTimerStatus.PAUSED;
import static clock.domain.CountdownTimerStatus.RESUMED;
import static clock.domain.CountdownTimerStatus.STOPPED;
import static clock.domain.CountdownTimerStatus.RESTARTED;
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
	private final static Integer INITIAL_VALUE = 1;
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
		expectCountdownTimerToBeInitialized();
		context.checking(new Expectations() {
			{
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

	private void expectCountdownTimerToBeInitialized() {
		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				then(state.is("Initialized"));
			}
		});
	}

	@Test
	@Order(3)
	void notifiesRunningWhenCountdownTimerRuns() {
		expectCountdownTimerToBeStarted();
		context.checking(new Expectations() {
			{
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

	private void expectCountdownTimerToBeStarted() {
		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				then(state.is("Started"));
			}
		});
	}

	@Test
	@Order(4)
	void notifiesPauseWhenCountdownTimerPauses() {
		expectCountdownTimerToBeRunning();
		context.checking(new Expectations() {
			{
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

	private void expectCountdownTimerToBeRunning() {
		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				then(state.is("Running"));
			}
		});
	}

	@Test
	@Order(5)
	void notifiesResumeWhenCountdownTimerResumes() {
		expectCountdownTimerToBePaused();
		context.checking(new Expectations() {
			{
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RESUMED)));
				when(state.is("Paused"));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.start();
		countdownTimer.run();
		executor.runUntilIdle();
		countdownTimer.pause();
		countdownTimer.resume();

		context.assertIsSatisfied();
	}

	private void expectCountdownTimerToBePaused() {
		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(PAUSED)));
				then(state.is("Paused"));
			}
		});
	}

	@Test
	@Order(6)
	void notifiesStopWhenCountdownTimerStops() {
		expectCountdownTimerToRunUntilItTimesOut();
		context.checking(new Expectations() {
			{
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

	private void expectCountdownTimerToRunUntilItTimesOut() {
		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				then(state.is("Running"));
			}
		});
	}

	@Test
	@Order(7)
	void notifiesRestartWhenCountdownTimerRestarts() {
		expectCountdownTimerToRunUntilItStops();
		context.checking(new Expectations() {
			{
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RESTARTED)));
				when(state.is("Stopped"));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.start();
		countdownTimer.run();
		executor.runUntilIdle();
		countdownTimer.stop();
		countdownTimer.restart();

		context.assertIsSatisfied();

		assertThat(runCount(), equalTo(1));
		assertThat(currentValue(), equalTo(INITIAL_VALUE));
	}

	private void expectCountdownTimerToRunUntilItStops() {
		context.checking(new Expectations() {
			{
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				allowing(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
				then(state.is("Stopped"));
			}
		});
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
