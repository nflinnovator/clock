package clock.adapters.output;

import clock.domain.countdowntimer.CountdownTimerState;
import clock.domain.countdowntimer.CountdownTimerStateChangeListener;
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
