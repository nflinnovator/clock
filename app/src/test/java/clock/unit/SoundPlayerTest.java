package clock.unit;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.domain.soundplayer.ClockSoundPlayer;
import clock.domain.soundplayer.SoundPlayerState;
import clock.shared.Observer;

@DisplayName("CountdownTimerSoundPlayer Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SoundPlayerTest {

	private final Mockery context = new Mockery();
	@SuppressWarnings("unchecked")
	private final Observer<SoundPlayerState> observer = (Observer<SoundPlayerState>) context.mock(Observer.class);
	private final ClockSoundPlayer soundPlayer = new ClockSoundPlayer();

	@BeforeEach
	void addObserver() {
		soundPlayer.addObserver(observer);
	}

	@Test
	@Order(1)
	void notifiesTickWhenItPlays() {

		context.checking(new Expectations() {
			{
				oneOf(observer).onStateChange(with(aTickSound(true)));
			}
		});

		soundPlayer.play(true);

		context.assertIsSatisfied();

	}

	@Test
	@Order(2)
	void notifiesBeepWhenItPlays() {

		context.checking(new Expectations() {
			{
				oneOf(observer).onStateChange(with(aTickSound(false)));
			}
		});

		soundPlayer.play(false);

		context.assertIsSatisfied();

	}

	@AfterEach
	void removeObserver() {
		soundPlayer.removeObserver(observer);
	}

	private Matcher<SoundPlayerState> aTickSound(boolean isTicking) {
		return new FeatureMatcher<SoundPlayerState, Boolean>(equalTo(isTicking), "countdown timer that is ", "was") {
			@Override
			protected Boolean featureValueOf(SoundPlayerState actual) {
				return actual.isTicking();
			}
		};
	}

}
