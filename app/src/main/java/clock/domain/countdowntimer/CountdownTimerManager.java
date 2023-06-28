package clock.domain.countdowntimer;

import clock.domain.Initializable;

public interface CountdownTimerManager extends Initializable<Integer> {
	void start();

	void pause();

	void resume();

	void stop();
}
