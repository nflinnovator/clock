package clock.domain.watch;

import java.time.LocalTime;

public interface Watch {
	
	abstract void start(LocalTime currentTime);
	abstract void stop();

}
