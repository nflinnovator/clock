package clock.domain.countdowntimer;

public class DefaultCountdownTimerManager implements CountdownTimerManager {

	private final CountdownTimer countdownTimer;

	public DefaultCountdownTimerManager(CountdownTimer countdownTimer) {
		this.countdownTimer = countdownTimer;
	}

	@Override
	public void initialize(Integer initialValue) {
		countdownTimer.initialize(initialValue);
	}

	@Override
	public void start() {
		countdownTimer.countdown();
	}

	@Override
	public void pause() {
		countdownTimer.pause();
	}

	@Override
	public void resume() {
		countdownTimer.countdown();
	}

	@Override
	public void stop() {
		countdownTimer.stop();
	}

}
