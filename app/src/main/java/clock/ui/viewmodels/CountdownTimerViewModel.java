package clock.ui.viewmodels;

import clock.domain.CountdownTimerState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

public final class CountdownTimerViewModel {

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

	public void update(CountdownTimerState currentState) {
		updateStatus(currentState);
		updateValue(currentState);
		updateRunCount(currentState);
		updateControlButtonText(currentState);
		updateIsStopButtonClickable(currentState);
	}

	private void updateStatus(CountdownTimerState currentState) {
		this.status.set(currentState.status().status());
	}

	private void updateValue(CountdownTimerState currentState) {
		this.value.set(String.valueOf(currentState.value()));
	}

	private void updateRunCount(CountdownTimerState currentState) {
		this.runCount.set(String.valueOf(currentState.runCount()));
	}

	private void updateControlButtonText(CountdownTimerState currentState) {
		this.controlButtonText.set(currentState.status().controlButtonText());
	}

	private void updateIsStopButtonClickable(CountdownTimerState currentState) {
		this.isStopButtonClickable.set(currentState.status().isStopButtonClickable());
	}
}
