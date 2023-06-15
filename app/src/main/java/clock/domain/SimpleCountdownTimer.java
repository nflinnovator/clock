package clock.domain;

import java.util.concurrent.Executor;

public class SimpleCountdownTimer implements CountdownTimer {

	private static final Integer STOP_VALUE = 0;

	private final Executor executor;
	private final CountdownTimerListener listener;

	private Integer initialValue;
	private Integer runCount;
	private CountdownTimerState state;

	public SimpleCountdownTimer(Executor executor, CountdownTimerListener listener) {
		this.executor = executor;
		this.listener = listener;
	}

	@Override
	public void onInit(Integer initialValue) {
		this.initialValue = initialValue;
		runCount = 0;
		state = CountdownTimerState.init(initialValue);
		notifyStateChange();
	}

	@Override
	public void onStart() {
		runCount++;
		state = state.on(initialValue, runCount);
		executor.execute(new CountdownRunner(initialValue));
	}

	@Override
	public void onPause(Integer pauseValue) {
		state = state.paused(pauseValue);
		notifyStateChange();
	}

	@Override
	public void onResume(Integer resumeValue) {
		state = state.on(resumeValue);
		notifyStateChange();
		executor.execute(new CountdownRunner(resumeValue + 1));
	}

	@Override
	public void onStop() {
		state = state.stopped();
		notifyStateChange();
	}

	public CountdownTimerState getCurrentState() {
		return state;
	}

	private void notifyStateChange() {
		listener.countdownTimerStateChanged(state);
	}

	private class CountdownRunner implements Runnable {

		private Integer currentValue;

		CountdownRunner(Integer currentValue) {
			this.currentValue = currentValue;
		}

		@Override
		public void run() {
			while (canRun()) {
				countdown();
				state = state.on(currentValue);
				notifyStateChange();
				pauseForOneSecond();
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
			return currentValue > STOP_VALUE;
		}

		private Boolean isRunnable() {
			return !(CountdownTimerStatus.PAUSED.equals(state.status())
					|| CountdownTimerStatus.STOPPED.equals(state.status()));
		}

		private Boolean hasExpired() {
			return currentValue == STOP_VALUE;
		}

		private void countdown() {
			--currentValue;
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
