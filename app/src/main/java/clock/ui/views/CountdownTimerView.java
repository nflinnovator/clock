package clock.ui.views;

import clock.domain.StateHolder;
import clock.domain.countdowntimer.CountdownTimerEventSender;
import clock.domain.countdowntimer.CountdownTimerState;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CountdownTimerView extends View<CountdownTimerState>{

	public final static String TIMER_LABEL_ID = "timerLabelId";
	public final static String RUN_COUNT_LABEL_ID = "runCountLabelId";
	public final static String STATUS_LABEL_ID = "statusLabelId";
	public final static String CONTROL_BUTTON_ID = "controlButtonId";
	public final static String STOP_BUTTON_ID = "stopButtonId";

	private final CountdownTimerEventSender eventSender;

	private Label statusLabel, timerLabel, runCountLabel;
	private Button controlCountdownTimerButton, stopCountdownTimerButton;

	public CountdownTimerView(StateHolder<CountdownTimerState> stateHolder, CountdownTimerEventSender eventSender) {
		super(stateHolder);
		this.eventSender = eventSender;
	}

	@Override
	protected void buildView() {
		statusLabel = ViewUtilities.createLabel(initialState().statusText(), STATUS_LABEL_ID);
		timerLabel = ViewUtilities.createLabel(String.valueOf(initialState().value()), TIMER_LABEL_ID);
		runCountLabel = ViewUtilities.createLabel(String.valueOf(initialState().runCount()), RUN_COUNT_LABEL_ID);
		controlCountdownTimerButton = ViewUtilities.createButton(initialState().controlButtonText(), CONTROL_BUTTON_ID);
		controlCountdownTimerButton.setOnAction((e) -> initialState().status().onControlButtonClick(eventSender));
		stopCountdownTimerButton = ViewUtilities.createButton(initialState().stopButtonText(), STOP_BUTTON_ID);
		stopCountdownTimerButton.setDisable(!initialState().isStopButtonClickable());
		stopCountdownTimerButton.setOnAction((e) -> initialState().status().onStopButtonClick(eventSender));
		ViewUtilities.addToRoot(container, statusLabel, runCountLabel, timerLabel, controlCountdownTimerButton,
				stopCountdownTimerButton);
	}

	@Override
	protected void updateView(CountdownTimerState currentState) {
		timerLabel.setText(String.valueOf(currentState.value()));
		statusLabel.setText(currentState.statusText());
		runCountLabel.setText(String.valueOf(currentState.runCount()));
		controlCountdownTimerButton.setText(currentState.controlButtonText());
		stopCountdownTimerButton.setText(currentState.stopButtonText());
		stopCountdownTimerButton.setDisable(!currentState.isStopButtonClickable());
	}

}
