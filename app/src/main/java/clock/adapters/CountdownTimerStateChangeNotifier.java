package clock.adapters;

import clock.domain.CountdownTimerListener;
import clock.domain.CountdownTimerState;
import javafx.application.Platform;

public class CountdownTimerStateChangeNotifier implements CountdownTimerListener{
	
	private final CountdownTimerListener viewModel;
	
	public CountdownTimerStateChangeNotifier(CountdownTimerListener viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		Platform.runLater(() -> viewModel.countdownTimerStateChanged(newState));
	}

}
