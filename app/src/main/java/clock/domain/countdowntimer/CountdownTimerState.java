package clock.domain.countdowntimer;

import static clock.domain.countdowntimer.CountdownTimerStatus.INITIALIZED;
import static clock.domain.countdowntimer.CountdownTimerStatus.PAUSED;
import static clock.domain.countdowntimer.CountdownTimerStatus.RESTARTED;
import static clock.domain.countdowntimer.CountdownTimerStatus.RESUMED;
import static clock.domain.countdowntimer.CountdownTimerStatus.RUNNING;
import static clock.domain.countdowntimer.CountdownTimerStatus.STARTED;
import static clock.domain.countdowntimer.CountdownTimerStatus.STOPPED;

public record CountdownTimerState(Integer value, Integer runCount, CountdownTimerStatus status) {
	
	private static final Integer STOP_VALUE = 0;

	public static CountdownTimerState initialize(Integer initialValue,Integer runCount) {
		return new CountdownTimerState(initialValue, runCount, INITIALIZED);
	}
	
	public CountdownTimerState started() {
		return new CountdownTimerState(value, runCount, STARTED);
	}
	
	public CountdownTimerState running(Integer value,Integer runCount) {
		return new CountdownTimerState(value, runCount, RUNNING);
	}

	public CountdownTimerState paused() {
		return new CountdownTimerState(value, runCount, PAUSED);
	}
	
	public CountdownTimerState resumed() {
		return new CountdownTimerState(value, runCount, RESUMED);
	}

	public CountdownTimerState stopped() {
		return new CountdownTimerState(0, runCount, STOPPED);
	}
	
	public CountdownTimerState restarted(Integer initialValue) {
		return new CountdownTimerState(initialValue, runCount, RESTARTED);
	}
	
	Boolean canRun() {
		return hasNotTimedout() && status.isRunnable();
	}
	
	private Boolean hasNotTimedout() {
		return value >= STOP_VALUE;
	}

	Boolean isLastRound() {
		return value == STOP_VALUE;
	}

}
