package clock.unit;

import static clock.domain.countdowntimer.CountdownTimerStatus.INITIALIZED;
import static clock.domain.countdowntimer.CountdownTimerStatus.PAUSED;
import static clock.domain.countdowntimer.CountdownTimerStatus.RUNNING;
import static clock.domain.countdowntimer.CountdownTimerStatus.STARTED;
import static clock.domain.countdowntimer.CountdownTimerStatus.STOPPED;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.lib.concurrent.DeterministicExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.domain.Observer;
import clock.domain.countdowntimer.CountdownTimerState;
import clock.domain.countdowntimer.CountdownTimerStatus;
import clock.domain.countdowntimer.DefaultCountdownTimer;
import clock.domain.soundplayer.SoundPlayer;

@DisplayName("CountdownTimer Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountdownTimerTest {

	private final Mockery context = new Mockery();
	@SuppressWarnings("unchecked")
	private final Observer<CountdownTimerState> observer = (Observer<CountdownTimerState>) context.mock(Observer.class);
	private final SoundPlayer soundPlayer = context.mock(SoundPlayer.class);
	private final DeterministicExecutor executor = new DeterministicExecutor();
	private final DefaultCountdownTimer countdownTimer = new DefaultCountdownTimer(executor, soundPlayer);
	final Sequence countdownTimerSequence = context.sequence("countdown timer sequence");
	private final static Integer INITIAL_VALUE = 1;

	@BeforeEach
	void addObserver() {
		countdownTimer.addObserver(observer);
	}

	@Test
	@Order(1)
	void initializesWhenItReceivesInitializeCommand() {

		context.checking(new Expectations() {
			{
				oneOf(observer).onStateChange(new CountdownTimerState(INITIALIZED, INITIAL_VALUE, INITIAL_VALUE, 0));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);

		context.assertIsSatisfied();

	}

	@Test
	@Order(2)
	void runsWhenItReceivesStartCommand() {
		expectCountdownTimerToBeInitialized();
		ignoreAllSoundPlayerEvents();
		context.checking(new Expectations() {
			{
				oneOf(observer).onStateChange(new CountdownTimerState(STARTED, INITIAL_VALUE, INITIAL_VALUE + 1, 1));
				inSequence(countdownTimerSequence);
				exactly(INITIAL_VALUE + 1).of(observer).onStateChange(with(aRunningCountdownTimer()));
				inSequence(countdownTimerSequence);
				oneOf(observer).onStateChange(new CountdownTimerState(STOPPED, INITIAL_VALUE, 0, 1));
				inSequence(countdownTimerSequence);
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.countdown();
		executor.runUntilIdle();

		context.assertIsSatisfied();

	}

	@Test
	@Order(3)
	void pausesWhenItReceivesPauseCommand() {
		expectCountdownTimerToBeInitialized();
		context.checking(new Expectations() {
			{
				oneOf(observer).onStateChange(new CountdownTimerState(PAUSED, INITIAL_VALUE, INITIAL_VALUE, 0));
				never(observer).onStateChange(new CountdownTimerState(STARTED, INITIAL_VALUE, INITIAL_VALUE, 1));
				never(observer).onStateChange(with(aRunningCountdownTimer()));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.pause();

		context.assertIsSatisfied();

	}

	@Test
	@Order(4)
	void runsWhenItReceivesResumeCommand() {
		expectCountdownTimerToBePaused();
		ignoreAllSoundPlayerEvents();
		context.checking(new Expectations() {
			{
				oneOf(observer).onStateChange(new CountdownTimerState(STARTED, INITIAL_VALUE, INITIAL_VALUE + 1, 0));
				inSequence(countdownTimerSequence);
				exactly(INITIAL_VALUE + 1).of(observer).onStateChange(with(aRunningCountdownTimer()));
				inSequence(countdownTimerSequence);
				oneOf(observer).onStateChange(new CountdownTimerState(STOPPED, INITIAL_VALUE, 0, 0));
				inSequence(countdownTimerSequence);
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.pause();
		countdownTimer.countdown();
		executor.runUntilIdle();

		context.assertIsSatisfied();

	}

	@Test
	@Order(5)
	void stopsWhenItReceivesStopCommand() {
		expectCountdownTimerToBeInitialized();
		context.checking(new Expectations() {
			{
				oneOf(observer).onStateChange(new CountdownTimerState(STOPPED, INITIAL_VALUE, 0, 0));
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.stop();

		context.assertIsSatisfied();

	}

	@Test
	@Order(6)
	void playASoundWhenenerTheCountdownTimerCountsDown() {
		ignoreAllCountdownTimerEvents();
		context.checking(new Expectations() {
			{
				exactly(INITIAL_VALUE + 1).of(soundPlayer).play(true);
				oneOf(soundPlayer).play(false);
			}
		});

		countdownTimer.initialize(INITIAL_VALUE);
		countdownTimer.countdown();
		executor.runUntilIdle();

		context.assertIsSatisfied();

	}

	@AfterEach
	void removeObserver() {
		countdownTimer.removeObserver(observer);
	}

	private void expectCountdownTimerToBeInitialized() {
		context.checking(new Expectations() {
			{
				allowing(observer).onStateChange(new CountdownTimerState(INITIALIZED, INITIAL_VALUE, INITIAL_VALUE, 0));
			}
		});
	}

	private void expectCountdownTimerToBePaused() {
		context.checking(new Expectations() {
			{
				allowing(observer).onStateChange(new CountdownTimerState(INITIALIZED, INITIAL_VALUE, INITIAL_VALUE, 0));
				allowing(observer).onStateChange(new CountdownTimerState(PAUSED, INITIAL_VALUE, INITIAL_VALUE, 0));
			}
		});
	}

	private void ignoreAllCountdownTimerEvents() {
		context.checking(new Expectations() {
			{
				ignoring(observer).onStateChange(with(any(CountdownTimerState.class)));
			}
		});
	}

	private void ignoreAllSoundPlayerEvents() {
		context.checking(new Expectations() {
			{
				ignoring(soundPlayer).play(with(any(Boolean.class)));
			}
		});
	}

	private Matcher<CountdownTimerState> aRunningCountdownTimer() {
		return new FeatureMatcher<CountdownTimerState, CountdownTimerStatus>(equalTo(RUNNING),
				"countdown timer that is ", "was") {
			@Override
			protected CountdownTimerStatus featureValueOf(CountdownTimerState actual) {
				return actual.status();
			}
		};
	}

}
