package clock.ui.viewmodels;

import static clock.domain.CountdownTimerStatus.RUNNING;

import clock.adapters.input.CountdownTimerEventSender;
import clock.domain.CountdownTimerState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public final class CountdownTimerViewModel {

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

	public void addStatusChangeListener(ChangeListener<String> valueChangeListener) {
		status.addListener(valueChangeListener);
	}

	public void addRunCountChangeListener(ChangeListener<String> valueChangeListener) {
		runCount.addListener(valueChangeListener);
	}

	public void addControlButtonTextChangeListener(ChangeListener<String> valueChangeListener) {
		controlButtonText.addListener(valueChangeListener);
	}

	public void addIsStopButtonClickableChangeListener(ChangeListener<Boolean> valueChangeListener) {
		isStopButtonClickable.addListener(valueChangeListener);
	}

	public void update(CountdownTimerState newState) {
		currentState = newState;
		updateStatus();
		updateValue();
		updateRunCount();
		updateControlButtonText();
		updateIsStopButtonClickable();
		maySendOnRunOrOnStop();// TO BE CLEANED UP
	}

	private void maySendOnRunOrOnStop() {
		maySendOnRun();
		maySendOnStop();
	}

	public void setCountdownTimerEventSender(CountdownTimerEventSender eventSender) {
		this.eventSender = eventSender;
	}

	private void maySendOnRun() {
		currentState.status().onRun(eventSender);
	}

	private void maySendOnStop() {
		if (0 == currentState.value() && RUNNING.equals(currentState.status())) {
			eventSender.onStop();
		}

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
