package clock.adapters;

import clock.domain.CountdownTimer;

public class TimerEventAnnouncer {
	private final CountdownTimer listener;

	public TimerEventAnnouncer(CountdownTimer listener) {
		this.listener = listener;
	}

	public void announce(TimerEvent event) {
		switch (event) {
		case START: {
			listener.onStart();
			break; 
		}
		case STOP: {
			listener.onStop();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + event);
		}
	}

	public void announce(TimerEvent event, Integer currentValue) {
		switch (event) {
		case PAUSE: {
			listener.onPause(currentValue);
			break;
		}
		case RESUME: {
			listener.onResume(currentValue);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + event);
		}
	}

}
