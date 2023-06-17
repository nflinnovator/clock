package clock.adapters;

import clock.domain.CountdownTimerStateChangeListener;
import clock.domain.CountdownTimerState;
import javafx.application.Platform;

public class CountdownTimerStateChangeNotifier implements CountdownTimerStateChangeListener{
	
	private final CountdownTimerStateChangeListener viewModel;
	
	public CountdownTimerStateChangeNotifier(CountdownTimerStateChangeListener viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		Platform.runLater(() -> viewModel.countdownTimerStateChanged(newState));
	}

}
