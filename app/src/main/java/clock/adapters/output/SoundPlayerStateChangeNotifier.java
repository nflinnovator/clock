package clock.adapters.output;

import clock.domain.soundplayer.SoundPlayerState;
import clock.domain.soundplayer.SoundPlayerStateChangeListener;
import clock.stateholders.SoundPlayerStateHolder;

public class SoundPlayerStateChangeNotifier implements SoundPlayerStateChangeListener{
	
	private final SoundPlayerStateHolder stateHolder;
	
	public SoundPlayerStateChangeNotifier(SoundPlayerStateHolder stateHolder) {
		this.stateHolder = stateHolder;
	}

	@Override
	public void soundPlayerStateChanged(SoundPlayerState newState) {
		stateHolder.update(newState);
	}

}
