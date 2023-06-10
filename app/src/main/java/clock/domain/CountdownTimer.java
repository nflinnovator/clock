package clock.domain;

import java.util.concurrent.Executor;

public class CountdownTimer implements UserEventListener {

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
		state = new CountdownTimerState(duration,runCount, false);
	}

	@Override
	public void onStart() {
		runCount++;
		state = new CountdownTimerState(duration,runCount, true);
		listener.startCountdownTimer();
		executor.execute(new CountdownRunner());
	}

	public CountdownTimerState getCurrentState() {
		return state;
	}
	
	private class CountdownRunner implements Runnable{
		
		private Integer currentValue = runCount == 1 ? state.currentValue() : state.currentValue() + 1;

		@Override
		public void run() {
			while (canRun()) {
				countdown();
				state = new CountdownTimerState(currentValue,runCount, true);
				listener.updateCountdownTimer();
				if(isNotLastRound()) {
					pauseForOneSecond();
				}else {
					state = new CountdownTimerState(currentValue,runCount, false);      
					listener.timeoutCountdownTimer();
				}
				
			}
		}

		private Boolean canRun() {
			return currentValue > STOP_VALUE ? Boolean.TRUE : Boolean.FALSE;
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
