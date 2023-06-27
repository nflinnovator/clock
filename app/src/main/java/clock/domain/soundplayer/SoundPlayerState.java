package clock.domain.soundplayer;

public record SoundPlayerState(boolean isTicking) {

	public static SoundPlayerState play(boolean isTicking) {
		return new SoundPlayerState(isTicking);
	}
}
