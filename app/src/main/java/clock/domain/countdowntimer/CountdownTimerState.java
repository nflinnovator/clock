package clock.domain.countdowntimer;

import static clock.domain.countdowntimer.CountdownTimerStatus.INITIALIZED;
import static clock.domain.countdowntimer.CountdownTimerStatus.STARTED;
import static clock.domain.countdowntimer.CountdownTimerStatus.PAUSED;
import static clock.domain.countdowntimer.CountdownTimerStatus.RUNNING;
import static clock.domain.countdowntimer.CountdownTimerStatus.STOPPED;

public record CountdownTimerState(CountdownTimerStatus status,Integer initialValue,Integer value, Integer runCount) {

	static CountdownTimerState initialize(Integer initialValue) {
		return new CountdownTimerState(INITIALIZED,initialValue,initialValue,0);
	}
	
	CountdownTimerState started() {
		if(isPaused()) {
			return new CountdownTimerState(STARTED,initialValue,value+1,runCount);
		}
		return new CountdownTimerState(STARTED,initialValue,initialValue+1,runCount+1);
	}
	
	CountdownTimerState running() {
		return new CountdownTimerState(RUNNING,initialValue,value-1,runCount);
	}
	
	CountdownTimerState paused() {
		return new CountdownTimerState(PAUSED,initialValue,value,runCount);
	}
	
	CountdownTimerState stopped() {
		return new CountdownTimerState(STOPPED,initialValue,0,runCount);
	}
	
	boolean allowsToCountdown() {
		return status.canRun();
	}
	
	boolean hasExpired() {
		return value == 0;
	}
	
	public String statusText() {
		return status.status();
	}
	
	public String controlButtonText() {
		return status.controlButtonText();
	}
	
	public String stopButtonText() {
		return status.stopButtonText();
	}
	
	public boolean isStopButtonClickable() {
		return status.isStopButtonClickable();
	}
	
	private boolean isPaused() {
		return status.isPaused();
	}

}
