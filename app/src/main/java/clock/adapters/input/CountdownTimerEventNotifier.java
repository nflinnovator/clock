package clock.adapters.input;

import clock.domain.CountdownTimer;

public class CountdownTimerEventNotifier implements CountdownTimerEventSender {
	private final CountdownTimer countdownTimer;

	public CountdownTimerEventNotifier(CountdownTimer countdownTimer) {
		this.countdownTimer = countdownTimer;
	}

	@Override
	public void onInitialize(Integer initialValue) {
		countdownTimer.initialize(initialValue);
	}

	@Override
	public void onStart() {
		countdownTimer.start();
	}

	@Override
	public void onRun() {
		countdownTimer.run();
	}

	@Override
	public void onPause() {
		countdownTimer.pause();
	}

	@Override
	public void onResume() {
		countdownTimer.resume();
	}

	@Override
	public void onStop() {
		countdownTimer.stop();
	}

	@Override
	public void onRestart() {
		countdownTimer.restart();
	}

}
