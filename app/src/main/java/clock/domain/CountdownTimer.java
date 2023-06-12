package clock.domain;

import java.util.concurrent.Executor;

public class CountdownTimer implements UserEventListener {

	public enum CountdownTimerStatus {
		OFF, ON, PAUSED, STOPPED
	}

	private static final Integer STOP_VALUE = 0;

	private final Integer duration;
	private Integer runCount = 0;
	private CountdownTimerState state;
	private final Executor executor;
	private final CountdownTimerListener listener;

	public CountdownTimer(Integer duration, Executor executor, CountdownTimerListener listener) {
		this.duration = duration;
		this.executor = executor;
		this.listener = listener;
		state = new CountdownTimerState(duration, runCount, CountdownTimerStatus.OFF);
	}

	@Override
	public void onStart() {
		runCount++;
		final var initialValue = initializeWithCorrectDuration();
		state = new CountdownTimerState(initialValue, runCount, CountdownTimerStatus.ON);
		executor.execute(new CountdownRunner(initialValue));
	}
	
	private Integer initializeWithCorrectDuration() {
		return runCount > 1 ? duration + 1 : duration;
	}

	@Override
	public void onPause() {
		state = new CountdownTimerState(state.currentValue(), runCount, CountdownTimerStatus.PAUSED);
		listener.countdownTimerStateChanged(state);
	}

	@Override
	public void onResume() {
		state = new CountdownTimerState(state.currentValue(), runCount, CountdownTimerStatus.ON);
		listener.countdownTimerStateChanged(state);
		executor.execute(new CountdownRunner(state.currentValue()+1));
	}

	@Override
	public void onStop() {
		state = new CountdownTimerState(STOP_VALUE, runCount, CountdownTimerStatus.STOPPED);
		listener.countdownTimerStateChanged(state);
	}

	public CountdownTimerState getCurrentState() {
		return state;
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
				state = new CountdownTimerState(currentValue, runCount, CountdownTimerStatus.ON);
				listener.countdownTimerStateChanged(state);
				pauseForOneSecond();
			}
			if (hasExpired()) {
				state = new CountdownTimerState(STOP_VALUE, runCount, CountdownTimerStatus.STOPPED);
				listener.countdownTimerStateChanged(state);
			}
		}

		private Boolean canRun() {
			return (currentValue > STOP_VALUE && !(CountdownTimerStatus.PAUSED.equals(state.status())
					|| CountdownTimerStatus.STOPPED.equals(state.status())));
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
