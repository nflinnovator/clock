package clock.domain;

import static clock.domain.CountdownTimerStatus.INITIALIZED;
import static clock.domain.CountdownTimerStatus.STARTED;
import static clock.domain.CountdownTimerStatus.RUNNING;
import static clock.domain.CountdownTimerStatus.PAUSED;
import static clock.domain.CountdownTimerStatus.STOPPED;

public record CountdownTimerState(Integer value, Integer runCount, CountdownTimerStatus status) {

	public static CountdownTimerState init(Integer initialValue,Integer runCount) {
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
		return new CountdownTimerState(value, runCount, RUNNING);
	}

	public CountdownTimerState stopped() {
		return new CountdownTimerState(0, runCount, STOPPED);
	}

}
