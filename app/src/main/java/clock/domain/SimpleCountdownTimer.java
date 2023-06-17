package clock.domain;

import java.util.concurrent.Executor;

public class SimpleCountdownTimer implements CountdownTimer {

	private static final Integer STOP_VALUE = 0;

	private final Executor executor;
	private final CountdownTimerStateChangeListener listener;

	private Integer initialValue,value;
	private Integer runCount;
	private CountdownTimerState state;

	public SimpleCountdownTimer(Executor executor, CountdownTimerStateChangeListener listener) {
		this.executor = executor;
		this.listener = listener;
	}

	@Override
	public void initialize(Integer initialValue) {
		this.initialValue = initialValue;
		runCount = 0;
		state = CountdownTimerState.init(initialValue);
		notifyStateChange();
	}

	@Override
	public void start() {
		runCount++;
		value = runCount > 1 ? initialValue +1 : initialValue;
		state = state.on(value, runCount);
		executor.execute(new CountdownTimerRunner());
	}

	@Override
	public void pause() {
		state = state.paused(value);
		notifyStateChange();
	}

	@Override
	public void resume() {
		state = state.on(value);
		executor.execute(new CountdownTimerRunner());
	}

	@Override
	public void stop() {
		state = state.stopped();
		notifyStateChange();
	}

	public CountdownTimerState getCurrentState() {
		return state;
	}

	private void notifyStateChange() {
		listener.countdownTimerStateChanged(state);
	}

	private class CountdownTimerRunner implements Runnable {

		@Override
		public void run() {
			while (canRun()) {
				pauseForOneSecond();
				countdown();
				state = state.on(value);
				notifyStateChange();
			}
			if (hasExpired()) {
				state = state.stopped();
				notifyStateChange();
			}
		}

		private Boolean canRun() {
			return (hasNotTimedout() && isRunnable());
		}

		private Boolean hasNotTimedout() {
			return value > STOP_VALUE;
		}

		private Boolean isRunnable() {
			return !(CountdownTimerStatus.PAUSED.equals(state.status())
					|| CountdownTimerStatus.STOPPED.equals(state.status()));
		}

		private Boolean hasExpired() {
			return value == STOP_VALUE;
		}

		private void countdown() {
			--value;
		}

		private void pauseForOneSecond() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
