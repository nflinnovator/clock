package clock.adapters.output;

import clock.domain.CountdownTimerStateChangeListener;
import clock.domain.CountdownTimerState;
import javafx.application.Platform;

public class JavaFXThreadForCountdownTimerListener implements CountdownTimerStateChangeListener{
	
	private final CountdownTimerStateChangeListener viewModel;
	
	public JavaFXThreadForCountdownTimerListener(CountdownTimerStateChangeListener viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		Platform.runLater(() -> viewModel.countdownTimerStateChanged(newState));
	}

}
