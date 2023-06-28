package clock.domain.watch;

import java.time.LocalTime;
import java.util.concurrent.Executor;

import clock.domain.Observable;
import clock.domain.Utilities;
import clock.domain.soundplayer.SoundPlayer;

public class DefaultWatch extends Observable<WatchState> implements Watch {

	private final Executor executor;
	private final SoundPlayer soundPlayer;

	public DefaultWatch(Executor executor, SoundPlayer soundPlayer) {
		this.executor = executor;
		this.soundPlayer = soundPlayer;
	}

	@Override
	public void start(LocalTime currentTime) {
		state = WatchState.started(currentTime);
		notifyStateChange();
		executor.execute(() -> run());
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
			waitForASecond();
		}
	}

	private boolean isRunnable() {
		return state.allowsToRun();
	}

	private void waitForASecond() {
		Utilities.pauseForASecond();
	}

}
