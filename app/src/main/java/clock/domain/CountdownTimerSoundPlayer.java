package clock.domain;

import clock.shared.ResourceUtil;
import javafx.scene.media.AudioClip;

public class CountdownTimerSoundPlayer implements SoundPlayer{
	
	AudioClip tickClip = new AudioClip(ResourceUtil.getResourceURL("tick_sound.mp3").toExternalForm());
	AudioClip beepClip = new AudioClip(ResourceUtil.getResourceURL("beep_sound.mp3").toExternalForm());

	@Override
	public void tick() {
		tickClip.play();
	}

	@Override
	public void stopTick() {
		tickClip.stop();
	}

	@Override
	public void beep() {
		beepClip.play();
	}

}
