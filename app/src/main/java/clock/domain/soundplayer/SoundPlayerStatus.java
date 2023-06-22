package clock.domain.soundplayer;

public enum SoundPlayerStatus {
	INITIALIZED, TICK, BEEP {
		@Override
		public Boolean hasBeeped() {
			return Boolean.TRUE;
		}
	};

	public Boolean hasBeeped() {
		return Boolean.FALSE;
	}
}
