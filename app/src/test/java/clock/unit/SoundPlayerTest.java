package clock.unit;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.domain.soundplayer.ClockSoundPlayer;
import clock.domain.soundplayer.SoundPlayer;
import clock.domain.soundplayer.SoundPlayerState;
import clock.domain.soundplayer.SoundPlayerStateChangeListener;
import clock.domain.soundplayer.SoundPlayerStatus;

@DisplayName("CountdownTimerSoundPlayer Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SoundPlayerTest {

	private final Mockery context = new Mockery();
	private final SoundPlayerStateChangeListener listener = context.mock(SoundPlayerStateChangeListener.class);
	private final SoundPlayer soundPlayer = new ClockSoundPlayer();

	@BeforeEach
	void addListener() {
		soundPlayer.addSoundPlayerStateChangeListener(listener);
	}

	@Test
	@Order(1)
	void notifiesInitializationWhenItInitializes() {

		context.checking(new Expectations() {
			{
				oneOf(listener).soundPlayerStateChanged(new SoundPlayerState(0, SoundPlayerStatus.INITIALIZED));
			}
		});

		soundPlayer.initialize();

		context.assertIsSatisfied();

	}

	@Test
	@Order(2)
	void notifiesTickWhenItTicks() {

		context.checking(new Expectations() {
			{
				allowing(listener).soundPlayerStateChanged(new SoundPlayerState(0, SoundPlayerStatus.INITIALIZED));
				oneOf(listener).soundPlayerStateChanged(new SoundPlayerState(1, SoundPlayerStatus.TICK));
			}
		});

		soundPlayer.initialize();
		soundPlayer.tick();

		context.assertIsSatisfied();

	}

	@Test
	@Order(3)
	void notifiesBeepWhenItBeeps() {

		context.checking(new Expectations() {
			{
				allowing(listener).soundPlayerStateChanged(new SoundPlayerState(0, SoundPlayerStatus.INITIALIZED));
				oneOf(listener).soundPlayerStateChanged(new SoundPlayerState(0, SoundPlayerStatus.BEEP));
			}
		});

		soundPlayer.initialize();
		soundPlayer.beep();

		context.assertIsSatisfied();

	}

}
