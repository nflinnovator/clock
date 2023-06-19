package clock.adapters.output;

import clock.domain.CountdownTimerState;
import clock.domain.CountdownTimerStateChangeListener;
import clock.ui.viewmodels.CountdownTimerViewModel;

public class CountdownTimerStateChangeNotifier implements CountdownTimerStateChangeListener {

	private final CountdownTimerViewModel viewModel;

	public CountdownTimerStateChangeNotifier(CountdownTimerViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		viewModel.update(newState);
	}

}
