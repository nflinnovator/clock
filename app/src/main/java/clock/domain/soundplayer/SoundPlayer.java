package clock.domain.soundplayer;

public interface SoundPlayer {
	void initialize();
    void tick();
    void beep();
    void addSoundPlayerStateChangeListener(SoundPlayerStateChangeListener listener);
}
