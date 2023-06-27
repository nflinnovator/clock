package clock.adapters.output;

import clock.domain.countdowntimer.CountdownTimerState;
import clock.shared.Observer;
import javafx.application.Platform;

public class JavaFXThreadForCountdownTimerObserver implements Observer<CountdownTimerState> {

	private final Observer<CountdownTimerState> stateChangeObserver;

	public JavaFXThreadForCountdownTimerObserver(Observer<CountdownTimerState> stateChangeObserver) {
		this.stateChangeObserver = stateChangeObserver;
	}

	@Override
	public void onStateChange(CountdownTimerState newState) {
		Platform.runLater(() -> stateChangeObserver.onStateChange(newState));
	}

}
