package clock.domain;

import static clock.domain.CountdownTimerStatus.INITIALIZED;
import static clock.domain.CountdownTimerStatus.STARTED;
import static clock.domain.CountdownTimerStatus.RUNNING;
import static clock.domain.CountdownTimerStatus.PAUSED;
import static clock.domain.CountdownTimerStatus.RESUMED;
import static clock.domain.CountdownTimerStatus.STOPPED;
import static clock.domain.CountdownTimerStatus.RESTARTED;

public record CountdownTimerState(Integer value, Integer runCount, CountdownTimerStatus status) {

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

}
