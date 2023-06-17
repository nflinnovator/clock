package clock.adapters;

import clock.domain.CountdownTimer;

public class CountdownTimerEventAnnouncer {
	private final CountdownTimer listener;

	public CountdownTimerEventAnnouncer(CountdownTimer listener) {
		this.listener = listener;
	}

	public void announce(CountdownTimerEvent event) {
		switch (event) {
		case START: {
			listener.start();
			break;
		}
		case RUN: {
			listener.run();
			break;
		}
		case PAUSE: {
			listener.pause();
			break;
		}
		case RESUME: {
			listener.resume();
			break;
		}
		case STOP: {
			listener.stop();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + event);
		}
	}

	public void announce(CountdownTimerEvent event, Integer currentValue) {
		switch (event) {
		case INITIALIZE: {
			listener.initialize(currentValue);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + event);
		}
	}

}
