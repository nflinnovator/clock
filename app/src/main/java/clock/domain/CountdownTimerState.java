package clock.domain;

import static clock.domain.CountdownTimerStatus.OFF;
import static clock.domain.CountdownTimerStatus.ON;
import static clock.domain.CountdownTimerStatus.PAUSED;
import static clock.domain.CountdownTimerStatus.STOPPED;

public record CountdownTimerState(Integer value, Integer runCount, CountdownTimerStatus status) {

	public static CountdownTimerState init(Integer initialValue) {
		return new CountdownTimerState(initialValue, 0, OFF);
	}

	public CountdownTimerState on(Integer value, Integer runCount) {
		return new CountdownTimerState(value, runCount, ON);
	}
	
	public CountdownTimerState on(Integer value) {
		return new CountdownTimerState(value, runCount, ON);
	}

	public CountdownTimerState paused(Integer value) {
		return new CountdownTimerState(value, runCount, PAUSED);
	}

	public CountdownTimerState stopped() {
		return new CountdownTimerState(0, runCount, STOPPED);
	}

}
