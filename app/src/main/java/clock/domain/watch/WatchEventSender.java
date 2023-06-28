package clock.domain.watch;

import java.time.LocalTime;

public interface WatchEventSender {
	
	abstract void onStart(LocalTime currentTime);
	abstract void onStop();
	
}
