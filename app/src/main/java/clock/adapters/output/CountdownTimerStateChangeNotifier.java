package clock.adapters.output;

import clock.domain.countdowntimer.CountdownTimerState;
import clock.domain.countdowntimer.CountdownTimerStateChangeListener;
import clock.stateholders.CountdownTimerStateHolder;

public class CountdownTimerStateChangeNotifier implements CountdownTimerStateChangeListener {

	private final CountdownTimerStateHolder viewModel;

	public CountdownTimerStateChangeNotifier(CountdownTimerStateHolder viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		viewModel.update(newState);
	}

}
