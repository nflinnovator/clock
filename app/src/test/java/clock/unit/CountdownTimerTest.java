package clock.unit;

import static clock.domain.countdowntimer.CountdownTimerStatus.INITIALIZED;
import static clock.domain.countdowntimer.CountdownTimerStatus.PAUSED;
import static clock.domain.countdowntimer.CountdownTimerStatus.RESTARTED;
import static clock.domain.countdowntimer.CountdownTimerStatus.RESUMED;
import static clock.domain.countdowntimer.CountdownTimerStatus.RUNNING;
import static clock.domain.countdowntimer.CountdownTimerStatus.STARTED;
import static clock.domain.countdowntimer.CountdownTimerStatus.STOPPED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.lib.concurrent.DeterministicExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.domain.countdowntimer.CountdownTimerState;
import clock.domain.countdowntimer.CountdownTimerStateChangeListener;
import clock.domain.countdowntimer.CountdownTimerStatus;
import clock.domain.countdowntimer.DefaultCountdownTimer;
import clock.domain.soundplayer.SoundPlayer;

@DisplayName("Countdown Timer Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountdownTimerTest {

	private final Mockery context = new Mockery();
	private final DeterministicExecutor executor = new DeterministicExecutor();
	private final CountdownTimerStateChangeListener listener = context.mock(CountdownTimerStateChangeListener.class);
	private final SoundPlayer soundPlayer = context.mock(SoundPlayer.class);
	private final DefaultCountdownTimer countdownTimer = new DefaultCountdownTimer(executor, soundPlayer);
	private final static Integer INITIAL_VALUE = 1;
	private final States state = context.states("state").startsAs("Initialized");

	@BeforeEach
	void addListener() {
		countdownTimer.addCountdownTimerStateChangeListener(listener);
	}

	@Test
	@Order(1)
	void notifiesInitializationWhenCountdownTimerInitializes() {

		context.checking(new Expectations() {
			{
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				oneOf(soundPlayer).initialize();
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);

		context.assertIsSatisfied();

		assertThat(currentValue(), equalTo(INITIAL_VALUE));
		assertThat(runCount(), equalTo(0));
	}

	@Test
	@Order(2)
	void notifiesStartWhenCountdownTimerStarts() {
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
				ignoring(soundPlayer).initialize();
			}
		});
	}

	@Test
	@Order(3)
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
				ignoring(soundPlayer).initialize();
				ignoring(soundPlayer).tick();
			}
		});
	}

	@Test
	@Order(4)
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
				ignoring(soundPlayer).initialize();
				ignoring(soundPlayer).tick();
			}
		});
	}

	@Test
	@Order(5)
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
				ignoring(soundPlayer).initialize();
				ignoring(soundPlayer).tick();
				ignoring(soundPlayer).beep();
			}
		});
	}

	@Test
	@Order(6)
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
				ignoring(soundPlayer).initialize();
				ignoring(soundPlayer).tick();
				ignoring(soundPlayer).beep();
			}
		});
	}
	
	@Test
	@Order(7)
	void notifiesSoundPlayerToInitializeWhenItInitializes() {

		context.checking(new Expectations() {
			{
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				oneOf(soundPlayer).initialize();
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);

		context.assertIsSatisfied();
	}

	@Test
	@Order(8)
	void notifiesTickWhenCountdownTimerRuns() {

		context.checking(new Expectations() {
			{
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				exactly(INITIAL_VALUE + 1).of(soundPlayer).tick();
				oneOf(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
				ignoring(soundPlayer).initialize();
				ignoring(soundPlayer).beep();
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.start();
		executor.runUntilIdle();

		context.assertIsSatisfied();
	}

	@Test
	@Order(9)
	void notifiesSoundPlayerToTickWhenItStarts() {

		context.checking(new Expectations() {
			{
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
				allowing(soundPlayer).initialize();
				exactly(INITIAL_VALUE + 1).of(soundPlayer).tick();
				allowing(soundPlayer).beep();
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.start();
		executor.runUntilIdle();

		context.assertIsSatisfied();
	}

	@Test
	@Order(10)
	void notifiesSoundPlayerToBeepWhenItTimesOut() {

		context.checking(new Expectations() {
			{
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(INITIALIZED)));
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STARTED)));
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(RUNNING)));
				ignoring(listener).countdownTimerStateChanged(with(aCountdownTimerThatIs(STOPPED)));
				allowing(soundPlayer).initialize();
				allowing(soundPlayer).tick();
				oneOf(soundPlayer).beep();
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.start();
		executor.runUntilIdle();

		context.assertIsSatisfied();
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
