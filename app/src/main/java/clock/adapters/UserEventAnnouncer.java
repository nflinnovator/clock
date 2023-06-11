package clock.adapters;

import clock.domain.UserEventListener;

public class UserEventAnnouncer {
	
	public enum TimerEvent{
		START,PAUSE,RESUME,STOP;
	}
	
	private final UserEventListener listener;
	
	public UserEventAnnouncer(UserEventListener listener) {
	  this.listener = listener;
	}

	
	public void announce(TimerEvent event) {
		switch (event) {
		case START: {
		     listener.onStart();
		     break;
		}
		case PAUSE: {
		     listener.onPause();
		     break;
		}
		case RESUME: {
		     listener.onResume();
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

}
