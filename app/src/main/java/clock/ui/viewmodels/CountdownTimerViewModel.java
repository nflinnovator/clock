package clock.ui.viewmodels;

import static clock.domain.CountdownTimerStatus.OFF;
import static clock.domain.CountdownTimerStatus.ON;
import static clock.domain.CountdownTimerStatus.PAUSED;
import static clock.domain.CountdownTimerStatus.STOPPED;

import clock.domain.CountdownTimerState;
import clock.domain.CountdownTimerStateChangeListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public class CountdownTimerViewModel implements CountdownTimerStateChangeListener {

	private static final String[] COUNTDOWN_TIMER_STATUSES = { "OFF", "ON", "PAUSED", "STOPPED" };
	public static final String[] START_BUTTON_DISPLAYS = { "Start", "Pause", "Resume", "Restart" };

	private CountdownTimerState currentState;

	private StringProperty statusProperty = new SimpleStringProperty();
	private StringProperty valueProperty = new SimpleStringProperty();
	private StringProperty runCountProperty = new SimpleStringProperty();
	private StringProperty startButtonDisplayProperty = new SimpleStringProperty();
	private BooleanProperty stopButtonDisabilityProperty = new SimpleBooleanProperty(Boolean.TRUE);

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		currentState = newState;
		updateUserInterface();
	}

	public void addValueChangeListener(ChangeListener<String> valueChangeListener) {
		valueProperty.addListener(valueChangeListener);
	}

	public void addStatusChangeListener(ChangeListener<String> valueChangeListener) {
		statusProperty.addListener(valueChangeListener);
	}

	public void addRunCountChangeListener(ChangeListener<String> valueChangeListener) {
		runCountProperty.addListener(valueChangeListener);
	}

	public void addStartButtonDisplayChangeListener(ChangeListener<String> valueChangeListener) {
		startButtonDisplayProperty.addListener(valueChangeListener);
	}

	public void addStopButtonDisabilityChangeListener(ChangeListener<Boolean> valueChangeListener) {
		stopButtonDisabilityProperty.addListener(valueChangeListener);
	}

	private void updateUserInterface() {
		updateStatus();
		updateValue();
		updateRunCount();
		updateStartButtonDisplay();
		updateStopButtonDisability();
	}

	private void updateStatus() {
		final String status;
		switch (currentState.status()) {
		case OFF: {
			status = COUNTDOWN_TIMER_STATUSES[OFF.ordinal()];
			break;
		}
		case ON: {
			status = COUNTDOWN_TIMER_STATUSES[ON.ordinal()];
			break;
		}
		case PAUSED: {
			status = COUNTDOWN_TIMER_STATUSES[PAUSED.ordinal()];
			break;
		}
		case STOPPED: {
			status = COUNTDOWN_TIMER_STATUSES[STOPPED.ordinal()];
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + currentState.status());
		}
		statusProperty.setValue(status);
	}

	private void updateValue() {
		valueProperty.setValue(String.valueOf(currentState.value()));
	}

	private void updateRunCount() {
		runCountProperty.setValue(String.valueOf(currentState.runCount()));
	}

	private void updateStartButtonDisplay() {
		final String startButtonDisplay;
		switch (currentState.status()) {
		case OFF: {
			startButtonDisplay = START_BUTTON_DISPLAYS[OFF.ordinal()];
			break;
		}
		case ON: {
			startButtonDisplay = START_BUTTON_DISPLAYS[ON.ordinal()];
			break;
		}
		case PAUSED: {
			startButtonDisplay = START_BUTTON_DISPLAYS[PAUSED.ordinal()];
			break;
		}
		case STOPPED: {
			startButtonDisplay = START_BUTTON_DISPLAYS[STOPPED.ordinal()];
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + currentState.status());
		}
		startButtonDisplayProperty.setValue(startButtonDisplay);
	}

	private void updateStopButtonDisability() {
		if (ON.equals(currentState.status()) || PAUSED.equals(currentState.status())) {
			stopButtonDisabilityProperty.setValue(Boolean.FALSE);
		} else {
			stopButtonDisabilityProperty.setValue(Boolean.TRUE);
		}
	}

	public String status() {
		return statusProperty.getValue();
	}

	public String value() {
		return valueProperty.getValue();
	}

	public String runCount() {
		return runCountProperty.getValue();
	}

	public String startButtonDisplay() {
		return startButtonDisplayProperty.getValue();
	}

	public Boolean stopButtonDisability() {
		return stopButtonDisabilityProperty.getValue();
	}

}
