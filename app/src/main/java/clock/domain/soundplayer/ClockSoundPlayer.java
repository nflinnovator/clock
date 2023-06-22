package clock.domain.soundplayer;

public class ClockSoundPlayer implements SoundPlayer {

	private SoundPlayerStateChangeListener listener;

	private Integer tickCount;
	private SoundPlayerState state;

	@Override
	public void initialize() {
		tickCount = 0;
		state = SoundPlayerState.initialize(tickCount);
		notifyStateChange();
	}

	@Override
	public void tick() {
		tickCount++;
		state = state.tick(tickCount);
		notifyStateChange();
	}

	@Override
	public void beep() {
		state = state.beep();
		notifyStateChange();
	}
	
	@Override
	public void addSoundPlayerStateChangeListener(SoundPlayerStateChangeListener listener) {
		this.listener = listener;
	}

	private void notifyStateChange() {
		listener.soundPlayerStateChanged(state);
	}

}
