package clock.adapters;

import static clock.domain.CountdownTimerStatus.RESTARTED;
import static clock.domain.CountdownTimerStatus.RESUMED;
import static clock.domain.CountdownTimerStatus.RUNNING;
import static clock.domain.CountdownTimerStatus.STARTED;

import clock.domain.CountdownTimerState;
import clock.domain.CountdownTimerStateChangeListener;
import clock.ui.viewmodels.CountdownTimerViewModel;

public class CountdownTimerStateChangeNotifier implements CountdownTimerStateChangeListener {

	private final CountdownTimerViewModel viewModel;

	private CountdownTimerState currentState;
	private CountdownTimerEventAnnouncer announcer;

	public CountdownTimerStateChangeNotifier(CountdownTimerViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		currentState = newState;
		viewModel.update(currentState);
		announce();
	}

	public void addCountdownTimerEventAnnouncer(CountdownTimerEventAnnouncer announcer) {
		this.announcer = announcer;
	}

	private void announce() {
		if (STARTED.equals(currentState.status()) || RESTARTED.equals(currentState.status())
				|| RESUMED.equals(currentState.status())) {
			announcer.announce(CountdownTimerEvent.RUN);
		} else if (0 == currentState.value() && RUNNING.equals(currentState.status())) {
			announcer.announce(CountdownTimerEvent.STOP);
		}
	}

}
