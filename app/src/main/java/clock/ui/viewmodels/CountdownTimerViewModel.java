package clock.ui.viewmodels;

import static clock.domain.CountdownTimerStatus.INITIALIZED;
import static clock.domain.CountdownTimerStatus.STARTED;
import static clock.domain.CountdownTimerStatus.RUNNING;
import static clock.domain.CountdownTimerStatus.PAUSED;
import static clock.domain.CountdownTimerStatus.STOPPED;

import clock.adapters.CountdownTimerEvent;
import clock.adapters.CountdownTimerEventAnnouncer;
import clock.domain.CountdownTimerState;
import clock.domain.CountdownTimerStateChangeListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public class CountdownTimerViewModel implements CountdownTimerStateChangeListener {

	private static final String[] COUNTDOWN_TIMER_STATUSES = { "INITIALIZED", "STARTED", "RUNNING", "PAUSED",
			"STOPPED" };
	public static final String[] START_BUTTON_DISPLAYS = { "Start", "Pause", "Pause", "Resume", "Restart" };

	private CountdownTimerState currentState;

	private StringProperty statusProperty = new SimpleStringProperty();
	private StringProperty valueProperty = new SimpleStringProperty();
	private StringProperty runCountProperty = new SimpleStringProperty();
	private StringProperty startButtonDisplayProperty = new SimpleStringProperty();
	private BooleanProperty isStopButtonDisableBooleanProperty = new SimpleBooleanProperty(Boolean.TRUE);

	private CountdownTimerEventAnnouncer announcer;

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		currentState = newState;
		updateUserInterface();
		announce();
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

	public void addIsStopButtonDisableChangeListener(ChangeListener<Boolean> valueChangeListener) {
		isStopButtonDisableBooleanProperty.addListener(valueChangeListener);
	}

	public void addCountdownTimerEventAnnouncer(CountdownTimerEventAnnouncer announcer) {
		this.announcer = announcer;
	}

	private void updateUserInterface() {
		updateStatus();
		updateValue();
		updateRunCount();
		updateStartButtonDisplay();
		updateStopButtonDisability();
	}

	private void announce() {
		if (STARTED.equals(currentState.status())) {
			announcer.announce(CountdownTimerEvent.RUN);
		} else if (0 == currentState.value() && RUNNING.equals(currentState.status())) {
			announcer.announce(CountdownTimerEvent.STOP);
		}
	}

	private void updateStatus() {
		final String status;
		switch (currentState.status()) {
		case INITIALIZED: {
			status = COUNTDOWN_TIMER_STATUSES[INITIALIZED.ordinal()];
			break;
		}
		case STARTED: {
			status = COUNTDOWN_TIMER_STATUSES[STARTED.ordinal()];
			break;
		}
		case RUNNING: {
			status = COUNTDOWN_TIMER_STATUSES[RUNNING.ordinal()];
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
		case INITIALIZED: {
			startButtonDisplay = START_BUTTON_DISPLAYS[INITIALIZED.ordinal()];
			break;
		}
		case STARTED: {
			startButtonDisplay = START_BUTTON_DISPLAYS[STARTED.ordinal()];
			break;
		}
		case RUNNING: {
			startButtonDisplay = START_BUTTON_DISPLAYS[STARTED.ordinal()];
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
		final Boolean isStopButtonDisable;
		switch (currentState.status()) {
		case INITIALIZED: {
			isStopButtonDisable = Boolean.TRUE;
			break;
		}
		case STARTED: {
			isStopButtonDisable = Boolean.FALSE;
			break;
		}
		case RUNNING: {
			isStopButtonDisable = Boolean.FALSE;
			break;
		}
		case PAUSED: {
			isStopButtonDisable = Boolean.FALSE;
			break;
		}
		case STOPPED: {
			isStopButtonDisable = Boolean.TRUE;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + currentState.status());
		}
		isStopButtonDisableBooleanProperty.setValue(isStopButtonDisable);
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
		return isStopButtonDisableBooleanProperty.getValue();
	}

}
