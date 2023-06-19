package clock.adapters.output;

import clock.domain.CountdownTimerStateChangeListener;
import clock.domain.CountdownTimerState;
import javafx.application.Platform;

public class JavaFXThreadForCountdownTimerListener implements CountdownTimerStateChangeListener{
	
	private final CountdownTimerStateChangeListener stateChangeListener;
	
	public JavaFXThreadForCountdownTimerListener(CountdownTimerStateChangeListener stateChangeListener) {
		this.stateChangeListener = stateChangeListener;
	}

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		Platform.runLater(() -> stateChangeListener.countdownTimerStateChanged(newState));
	}

}
