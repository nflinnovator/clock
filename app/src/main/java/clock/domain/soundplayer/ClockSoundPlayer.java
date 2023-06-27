package clock.domain.soundplayer;

import clock.shared.Observable;

public class ClockSoundPlayer extends Observable<SoundPlayerState> implements SoundPlayer {

	@Override
	public void play(boolean isTicking) {
		state = SoundPlayerState.play(isTicking);
		notifyStateChange();
	}

}
