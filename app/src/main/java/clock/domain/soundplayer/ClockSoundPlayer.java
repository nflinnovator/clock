package clock.domain.soundplayer;

import clock.domain.Observable;

public class ClockSoundPlayer extends Observable<SoundPlayerState> implements SoundPlayer {

	@Override
	public void play(boolean isTicking) {
		state = SoundPlayerState.play(isTicking);
		notifyStateChange();
	}

}
