package clock.adapters.output;

import clock.domain.Observer;
import javafx.application.Platform;

public class JavaFXThreadForStateObserver<E> implements Observer<E> {

	private final Observer<E> stateChangeObserver;

	public JavaFXThreadForStateObserver(Observer<E> stateChangeObserver) {
		this.stateChangeObserver = stateChangeObserver;
	}

	@Override
	public void onStateChange(E newState) {
		Platform.runLater(() -> stateChangeObserver.onStateChange(newState));
	}

}
