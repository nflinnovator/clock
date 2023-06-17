package clock.domain;

import static clock.domain.CountdownTimerStatus.STARTED;
import static clock.domain.CountdownTimerStatus.RUNNING;

import java.util.concurrent.Executor;

public class SimpleCountdownTimer implements CountdownTimer {

	private static final Integer STOP_VALUE = 0;

	private final Executor executor;
	private final CountdownTimerStateChangeListener listener;

	private Integer value;
	private Integer runCount;
	private CountdownTimerState state;

	public SimpleCountdownTimer(Executor executor, CountdownTimerStateChangeListener listener) {
		this.executor = executor;
		this.listener = listener;
	}

	@Override
	public void initialize(Integer initialValue) {
		value = initialValue;
		runCount = 0;
		state = CountdownTimerState.init(initialValue, runCount);
		notifyStateChange();
	}

	@Override
	public void start() {
		state = state.started();
		notifyStateChange();
	}

	@Override
	public void run() {
		runCount++;
		executor.execute(() -> countdown());
	}

	@Override
	public void pause() {
		state = state.paused();
		notifyStateChange();
	}

	@Override
	public void resume() {
		state = state.resumed();
		executor.execute(() -> countdown());
	}

	@Override
	public void stop() {
		state = state.stopped();
		notifyStateChange();
	}

	public CountdownTimerState getCurrentState() {
		return state;
	}

	private void countdown() {
		while (canRun()) {
			state = state.running(value, runCount);
			notifyStateChange();
			if (!isLastRound()) {
				pauseForOneSecond();
				value--;
			} else {
				value = -1;
			}
		}
	}

	private Boolean canRun() {
		return hasNotTimedout() && isRunnable();
	}

	private Boolean hasNotTimedout() {
		return value >= STOP_VALUE;
	}

	private Boolean isLastRound() {
		return value == STOP_VALUE;
	}

	private Boolean isRunnable() {
		return STARTED.equals(state.status()) || RUNNING.equals(state.status());
	}

	private void pauseForOneSecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void notifyStateChange() {
		listener.countdownTimerStateChanged(state);
	}
}
