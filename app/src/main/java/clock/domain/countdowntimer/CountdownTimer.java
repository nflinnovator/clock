package clock.domain.countdowntimer;

import clock.domain.Initializable;

public interface CountdownTimer extends Initializable<Integer> {

	abstract void countdown();

	abstract void pause();

	abstract void stop();

}
