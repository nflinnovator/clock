package clock.adapters.input;

import java.time.LocalTime;

import clock.domain.watch.Watch;
import clock.domain.watch.WatchEventSender;

public class WatchEventNotifier implements WatchEventSender{
	
	private final Watch watch;
	
	public WatchEventNotifier(Watch watch) {
		this.watch = watch;
	}

	@Override
	public void onStart(LocalTime currentTime) {
		watch.start(currentTime);
	}

	@Override
	public void onStop() {
		watch.stop();
	}

}
