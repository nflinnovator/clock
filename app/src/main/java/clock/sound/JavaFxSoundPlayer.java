package clock.sound;

import clock.shared.ResourceUtil;
import clock.stateholders.SoundPlayerStateHolder;
import javafx.scene.media.AudioClip;

public class JavaFxSoundPlayer {

	private final SoundPlayerStateHolder stateHolder;

	private AudioClip tickSound, beepSound;

	public JavaFxSoundPlayer(SoundPlayerStateHolder stateHolder) {
		this.stateHolder = stateHolder;
		play();
	}

	private void play() {
		tickSound = new AudioClip(ResourceUtil.getResourceURL("tick_sound.mp3").toExternalForm());
		beepSound = new AudioClip(ResourceUtil.getResourceURL("beep_sound.mp3").toExternalForm());

		stateHolder.addTickCountChangeListener((observable, oldValue, newValue) -> {
			tickSound.play();
		});
		stateHolder.addHasBeepedChangeListener((observable, oldValue, newValue) -> {
			if (newValue)
				beepSound.play();
		});
	}

}
