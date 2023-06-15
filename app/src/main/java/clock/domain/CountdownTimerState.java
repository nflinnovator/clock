package clock.domain;

import static clock.domain.CountdownTimerStatus.OFF;
import static clock.domain.CountdownTimerStatus.ON;
import static clock.domain.CountdownTimerStatus.PAUSED;
import static clock.domain.CountdownTimerStatus.STOPPED;

public record CountdownTimerState(Integer currentValue, Integer runCount, CountdownTimerStatus status) {

	public static CountdownTimerState init(Integer duration) {
		return new CountdownTimerState(duration, 0, OFF);
	}

	public CountdownTimerState on(Integer duration, Integer runCount) {
		return new CountdownTimerState(duration, runCount, ON);
	}
	
	public CountdownTimerState on(Integer duration) {
		return new CountdownTimerState(duration, runCount, ON);
	}

	public CountdownTimerState paused(Integer duration) {
		return new CountdownTimerState(duration, runCount, PAUSED);
	}

	public CountdownTimerState stopped() {
		return new CountdownTimerState(0, runCount, STOPPED);
	}

}
