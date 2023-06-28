package clock.domain.watch;

import java.time.LocalTime;

public record WatchState(Boolean isStarted, Integer hour, Integer minute, Integer second) {

	static WatchState started(LocalTime currentTime) {
		return new WatchState(true, currentTime.getHour(),  currentTime.getMinute(),
				currentTime.getSecond());
	}
	
	WatchState running() {
		if(second == 59) {
			if (minute == 59) {
				if (hour == 23) {
					return new WatchState(isStarted,0,0,0);
				}else {
					return new WatchState(isStarted,hour+1,0,0);
				}
			}else {
				return new WatchState(isStarted,hour,minute+1,0);
			}
		}else {
			return new WatchState(isStarted,hour,minute,second+1);
		}
		
	}
	
	WatchState stopped() {
		return new WatchState(false, hour, minute, second);
	}
	
	boolean allowsToRun() {
		return isStarted;
	}

}
