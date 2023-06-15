package clock.ui.viewmodels;

import static clock.domain.CountdownTimerStatus.OFF;
import static clock.domain.CountdownTimerStatus.ON;
import static clock.domain.CountdownTimerStatus.PAUSED;
import static clock.domain.CountdownTimerStatus.STOPPED;

import clock.domain.CountdownTimerListener;
import clock.domain.CountdownTimerState;

public class CountdownTimerViewModel implements CountdownTimerListener {

	private static final String[] COUNTDOWN_TIMER_STATUSES = { "OFF", "ON", "PAUSED", "STOPPED" };
	public static final String[] START_BUTTON_DISPLAYS = { "Start", "Pause", "Resume", "Restart" };

	private CountdownTimerState currentState;

	private String status;
	private String value;
	private String runCount;
	private String startButtonDisplay;
	private boolean stopButtonDisability;

	@Override
	public void countdownTimerStateChanged(CountdownTimerState newState) {
		currentState = newState;
		updateUserInterface();
	}

	private void updateUserInterface() {
		updateStatus();
		updateValue();
		updateRunCount();
		updateStartButtonDisplay();
		updateStopButtonDisability();
	}

	private void updateStatus() {
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
	}

	private void updateValue() {
		value = String.valueOf(currentState.currentValue());
	}

	private void updateRunCount() {
		runCount = String.valueOf(currentState.runCount());
	}

	private void updateStartButtonDisplay() {
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
	}

	private void updateStopButtonDisability() {
		if (OFF.equals(currentState.status()) || STOPPED.equals(currentState.status())) {
			stopButtonDisability = true;
		}
	}

	public String status() {
		return status;
	}

	public String value() {
		return value;
	}

	public String runCount() {
		return runCount;
	}

	public String startButtonDisplay() {
		return startButtonDisplay;
	}

	public Boolean stopButtonDisability() {
		return stopButtonDisability;
	}

}
