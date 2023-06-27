package clock.domain.countdowntimer;

import clock.shared.Initializable;

public interface CountdownTimer extends Initializable<Integer> {

	abstract void countdown();

	abstract void pause();

	abstract void stop();

}
