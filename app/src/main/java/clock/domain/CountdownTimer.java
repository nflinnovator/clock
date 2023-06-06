package clock.domain;

public class CountdownTimer implements UserEventListener{
	
	public static final Integer COUNTDOWN_TIMER_DURATION_INITIAL_VALUE_IN_SECONDS = 59;
	
	private CountdownTimerState state;
	private final CountdownTimerListener listener;
	
	public CountdownTimer(CountdownTimerListener listener) {
		state = new CountdownTimerState(COUNTDOWN_TIMER_DURATION_INITIAL_VALUE_IN_SECONDS, false);
		this.listener = listener;
	}

	@Override
	public void onTimerStartedEvent() {
		state = new CountdownTimerState(COUNTDOWN_TIMER_DURATION_INITIAL_VALUE_IN_SECONDS, true);
		listener.countdownTimerStarted();
	}
	
	public CountdownTimerState getCurrentState() {
		return state;
	}

}
