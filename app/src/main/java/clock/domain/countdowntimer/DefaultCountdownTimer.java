package clock.domain.countdowntimer;

import java.util.concurrent.Executor;

import clock.domain.soundplayer.SoundPlayer;
import clock.shared.Observable;

public class DefaultCountdownTimer extends Observable<CountdownTimerState> implements CountdownTimer {

	private final Executor executor;
	private final SoundPlayer soundPlayer;

	public DefaultCountdownTimer(Executor executor, SoundPlayer soundPlayer) {
		this.executor = executor;
		this.soundPlayer = soundPlayer;
	}

	@Override
	public void initialize(Integer initialValue) {
		state = CountdownTimerState.initialize(initialValue);
		notifyStateChange();
	}

	@Override
	public void countdown() {
		state = state.started();
		notifyStateChange();
		executor.execute(() -> run());
	}

	@Override
	public void pause() {
		state = state.paused();
		notifyStateChange();
	}

	@Override
	public void stop() {
		state = state.stopped();
		notifyStateChange();
	}

	private void run() {
		while (isRunnable()) {
			state = state.running();
			notifyStateChange();
			soundPlayer.play(true);
			if (hasExpired()) {
				state = state.stopped();
				notifyStateChange();
				soundPlayer.play(false);
			} else {
				pauseForASecond();
			}
		}
	}

	private boolean isRunnable() {
		return state.allowsToCountdown();
	}

	private boolean hasExpired() {
		return state.hasExpired();
	}

	private void pauseForASecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
