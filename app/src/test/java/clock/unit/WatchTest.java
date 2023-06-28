package clock.unit;

import static org.hamcrest.Matchers.equalTo;

import java.time.LocalTime;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.concurrent.DeterministicExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.domain.Observer;
import clock.domain.soundplayer.SoundPlayer;
import clock.domain.watch.DefaultWatch;
import clock.domain.watch.WatchState;

@DisplayName("Watch Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WatchTest {

	private final Mockery context = new Mockery();
	@SuppressWarnings("unchecked")
	private final Observer<WatchState> observer = (Observer<WatchState>) context.mock(Observer.class);
	private final SoundPlayer soundPlayer = context.mock(SoundPlayer.class);
	private final DeterministicExecutor executor = new DeterministicExecutor();
	private final DefaultWatch watch = new DefaultWatch(executor, soundPlayer);
	private final static LocalTime CURRENT_TIME = LocalTime.of(10, 30, 20);

	@BeforeEach
	void addObserver() {
		watch.addObserver(observer);
	}

	@Test
	@Order(1)
	void startsWhenItReceivesStartCommand() {

		context.checking(new Expectations() {
			{
				oneOf(observer).onStateChange(new WatchState(true, CURRENT_TIME.getHour(), CURRENT_TIME.getMinute(),
						CURRENT_TIME.getSecond()));
				atLeast(1).of(observer).onStateChange(with(aWatchThatIsRunning(true)));
			}
		});

		watch.start(CURRENT_TIME);
		executor.runUntilIdle();

		context.assertIsSatisfied();

	}

	@Test
	@Order(1)
	void stopsWhenItReceivesStopCommand() {

		context.checking(new Expectations() {
			{
				oneOf(observer).onStateChange(with(aWatchThatIsRunning(false)));
			}
		});

		watch.stop();

		context.assertIsSatisfied();

	}

	@AfterEach
	void removeObserver() {
		watch.removeObserver(observer);
	}

	private Matcher<WatchState> aWatchThatIsRunning(boolean isRunning) {
		return new FeatureMatcher<WatchState, Boolean>(equalTo(isRunning), "a watch that is ", "was") {
			@Override
			protected Boolean featureValueOf(WatchState actual) {
				return actual.isStarted();
			}
		};
	}

}
