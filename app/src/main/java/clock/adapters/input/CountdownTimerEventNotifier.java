package clock.adapters.input;

import clock.domain.countdowntimer.CountdownTimerManager;
import clock.domain.countdowntimer.CountdownTimerEventSender;

public class CountdownTimerEventNotifier implements CountdownTimerEventSender {
	private final CountdownTimerManager countdownTimerManager;

	public CountdownTimerEventNotifier(CountdownTimerManager countdownTimerManager) {
		this.countdownTimerManager = countdownTimerManager;
	}

	@Override
	public void onInitialize(Integer initialValue) {
		countdownTimerManager.initialize(initialValue);
	}

	@Override
	public void onStart() {
		countdownTimerManager.start();
	}

	@Override
	public void onPause() {
		countdownTimerManager.pause();
	}

	@Override
	public void onResume() {
		countdownTimerManager.resume();
	}

	@Override
	public void onStop() {
		countdownTimerManager.stop();
	}

}
