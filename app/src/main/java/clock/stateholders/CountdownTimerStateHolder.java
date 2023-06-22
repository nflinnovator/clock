package clock.stateholders;

import clock.domain.countdowntimer.CountdownTimerEventSender;
import clock.domain.countdowntimer.CountdownTimerState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public final class CountdownTimerStateHolder {

	private CountdownTimerEventSender eventSender;
	private CountdownTimerState currentState;

	private StringProperty status = new SimpleStringProperty();
	private StringProperty value = new SimpleStringProperty();
	private StringProperty runCount = new SimpleStringProperty();
	private StringProperty controlButtonText = new SimpleStringProperty();
	private BooleanProperty isStopButtonClickable = new SimpleBooleanProperty();

	public final String getStatus() {
		return status.getValue();
	}

	public final String getValue() {
		return value.getValue();
	}

	public final String getRunCount() {
		return runCount.getValue();
	}

	public final String getControlButtonText() {
		return controlButtonText.getValue();
	}

	public final Boolean getIsStopButtonClickable() {
		return isStopButtonClickable.getValue();
	}

	public void addValueChangeListener(ChangeListener<String> valueChangeListener) {
		value.addListener(valueChangeListener);
	}

	public void addStatusChangeListener(ChangeListener<String> statusChangeListener) {
		status.addListener(statusChangeListener);
	}

	public void addRunCountChangeListener(ChangeListener<String> runCountChangeListener) {
		runCount.addListener(runCountChangeListener);
	}

	public void addControlButtonTextChangeListener(ChangeListener<String> controlButtonTextChangeListener) {
		controlButtonText.addListener(controlButtonTextChangeListener);
	}

	public void addIsStopButtonClickableChangeListener(ChangeListener<Boolean> isStopButtonClickableChangeListener) {
		isStopButtonClickable.addListener(isStopButtonClickableChangeListener);
	}

	public void update(CountdownTimerState newState) {
		currentState = newState;
		updateStatus();
		updateValue();
		updateRunCount();
		updateControlButtonText();
		updateIsStopButtonClickable();
	}

	public void setCountdownTimerEventSender(CountdownTimerEventSender eventSender) {
		this.eventSender = eventSender;
	}

	private void updateStatus() {
		status.set(currentState.status().status());
	}

	private void updateValue() {
		value.set(String.valueOf(currentState.value()));
	}

	private void updateControlButtonText() {
		controlButtonText.set(currentState.status().controlButtonText());
	}

	private void updateRunCount() {
		runCount.set(String.valueOf(currentState.runCount()));
	}

	private void updateIsStopButtonClickable() {
		isStopButtonClickable.set(currentState.status().isStopButtonClickable());
	}

	public void onControlButtonClick() {
		currentState.status().onControlButtonClick(eventSender);
	}

	public void onStopButtonClick() {
		currentState.status().onStopButtonClick(eventSender);
	}
}
