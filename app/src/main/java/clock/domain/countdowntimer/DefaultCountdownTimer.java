package clock.domain.countdowntimer;

import java.util.concurrent.Executor;

import clock.domain.soundplayer.SoundPlayer;

public class DefaultCountdownTimer implements CountdownTimer {

	private final Executor executor;
	private final SoundPlayer soundPlayer;

	private CountdownTimerStateChangeListener listener;

	private Integer initialValue;
	private Integer value;
	private Integer runCount;
	private Boolean isResumed;
	private CountdownTimerState state;

	public DefaultCountdownTimer(Executor executor, SoundPlayer soundPlayer) {
		this.executor = executor;
		this.soundPlayer = soundPlayer;
	}

	@Override
	public void initialize(Integer initialValue) {
		this.initialValue = initialValue;
		value = this.initialValue;
		runCount = 0;
		isResumed = Boolean.FALSE;
		state = CountdownTimerState.initialize(value, runCount);
		notifyStateChange();
		soundPlayer.initialize();
	}

	@Override
	public void start() {
		state = state.started();
		notifyStateChange();
		run();
	}

	@Override
	public void pause() {
		state = state.paused();
		notifyStateChange();
	}

	@Override
	public void resume() {
		isResumed = Boolean.TRUE;
		state = state.resumed();
		notifyStateChange();
		run();
	}

	@Override
	public void stop() {
		state = state.stopped();
		notifyStateChange();
		soundPlayer.beep();
	}

	@Override
	public void restart() {
		value = initialValue;
		isResumed = Boolean.FALSE;
		state = state.restarted(value);
		notifyStateChange();
		run();
	}
	
	@Override
	public void addCountdownTimerStateChangeListener(CountdownTimerStateChangeListener listener) {
		this.listener = listener;
	}

	public CountdownTimerState getCurrentState() {
		return state;
	}

	private void run() {
		if (!isResumed) {
			runCount++;
		}
		executor.execute(() -> countdown());
	}

	private void countdown() {
		while (state.canRun()) {
			state = state.running(value, runCount);
			notifyStateChange();
			soundPlayer.tick();
			if (!state.isLastRound()) {
				pauseForOneSecond();
				value--;
			} else {
				state = state.stopped();
				notifyStateChange();
				soundPlayer.beep();
				value = -1;
			}
		}
	}

	private void notifyStateChange() {
		listener.countdownTimerStateChanged(state);
	}

	private void pauseForOneSecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
