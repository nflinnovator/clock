package clock.domain;

import java.util.EventListener;

public interface CountdownTimerListener extends EventListener {
	void startCountdownTimer();

	void updateCountdownTimer();
	
	void timeoutCountdownTimer();
	
	void pauseCountdownTimer();
	
	void resumeCountdownTimer();
}
