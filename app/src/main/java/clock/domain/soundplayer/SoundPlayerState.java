package clock.domain.soundplayer;

import static clock.domain.soundplayer.SoundPlayerStatus.INITIALIZED;
import static clock.domain.soundplayer.SoundPlayerStatus.TICK;
import static clock.domain.soundplayer.SoundPlayerStatus.BEEP;

public record SoundPlayerState(Integer tickCount, SoundPlayerStatus status) {

	public static SoundPlayerState initialize(Integer tickCount) {
		return new SoundPlayerState(tickCount, INITIALIZED);
	}

	public SoundPlayerState tick(Integer tickCount) {
		return new SoundPlayerState(tickCount, TICK);
	}

	public SoundPlayerState beep() {
		return new SoundPlayerState(tickCount, BEEP);
	}

}
