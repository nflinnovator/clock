package clock.domain;

import java.util.concurrent.Executor;

public class CountdownTimer implements UserEventListener {

	public enum CountdownTimerStatus {
		OFF, ON, PAUSED, STOPPED
	}

	private static final Integer STOP_VALUE = 0;

	private final Integer duration;
	private Integer runCount;
	private CountdownTimerState state;
	private final Executor executor;
	private final CountdownTimerListener listener;

	public CountdownTimer(Integer duration, Executor executor, CountdownTimerListener listener) {
		this.duration = duration;
		this.executor = executor;
		this.listener = listener;
		runCount = 0;
		state = new CountdownTimerState(duration, runCount, CountdownTimerStatus.OFF);
	}

	@Override
	public void onStart() {
		runCount++;
		state = new CountdownTimerState(duration, runCount, CountdownTimerStatus.ON);
		listener.startCountdownTimer();
		executor.execute(new CountdownRunner());
	}

	@Override
	public void onPause() {
		state = new CountdownTimerState(state.currentValue(), runCount, CountdownTimerStatus.PAUSED);
		listener.pauseCountdownTimer();
	}

	public CountdownTimerState getCurrentState() {
		return state;
	}

	private class CountdownRunner implements Runnable {

		private Integer currentValue = runCount == 1 ? state.currentValue() : state.currentValue() + 1;

		@Override
		public void run() {
			while (canRun()) {
				countdown();
				state = new CountdownTimerState(currentValue, runCount, CountdownTimerStatus.ON);
				listener.updateCountdownTimer();
				if (isNotLastRound()) {
					pauseForOneSecond();
				} else {
					state = new CountdownTimerState(currentValue, runCount, CountdownTimerStatus.STOPPED);
					listener.timeoutCountdownTimer();
				}

			}
		}

		private Boolean canRun() {
			return (currentValue > STOP_VALUE ? Boolean.TRUE : Boolean.FALSE)
					&& (!state.status().equals(CountdownTimerStatus.PAUSED));
		}

		private Boolean isNotLastRound() {
			final var lastRound = 0;
			return currentValue > lastRound ? Boolean.TRUE : Boolean.FALSE;
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
