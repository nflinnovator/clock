package clock.sound;

import clock.domain.ResourceUtil;
import clock.domain.StateHolder;
import clock.domain.soundplayer.SoundPlayerState;
import javafx.scene.media.AudioClip;

public class JavaFxSoundPlayer {

	private final StateHolder<SoundPlayerState> stateHolder;

	public JavaFxSoundPlayer(StateHolder<SoundPlayerState> stateHolder) {
		this.stateHolder = stateHolder;
		play();
	}

	private void play() {
		final var tickSound = new AudioClip(ResourceUtil.getResourceURL("tick_sound.mp3").toExternalForm());
		final var beepSound = new AudioClip(ResourceUtil.getResourceURL("beep_sound.mp3").toExternalForm());

		stateHolder.addStateChangeListener((observable, oldValue, newValue) -> {
			if (newValue.isTicking())
				tickSound.play();
			else
				beepSound.play();
		});
	}

}
