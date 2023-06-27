package clock.ui.views;

import clock.domain.countdowntimer.CountdownTimerEventSender;
import clock.domain.countdowntimer.CountdownTimerState;
import clock.shared.StateHolder;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CountdownTimerView extends VBox {

	public final static String TIMER_LABEL_ID = "timerLabelId";
	public final static String RUN_COUNT_LABEL_ID = "runCountLabelId";
	public final static String STATUS_LABEL_ID = "statusLabelId";
	public final static String CONTROL_BUTTON_ID = "controlButtonId";
	public final static String STOP_BUTTON_ID = "stopButtonId";

	private final StateHolder<CountdownTimerState> stateHolder;
	private final CountdownTimerEventSender eventSender;

	private Label statusLabel, timerLabel, runCountLabel;
	private Button controlCountdownTimerButton, stopCountdownTimerButton;

	public CountdownTimerView(StateHolder<CountdownTimerState> stateHolder, CountdownTimerEventSender eventSender) {
		this.stateHolder = stateHolder;
		this.eventSender = eventSender;
		buildView();
		addStateChangeListener();
	}

	private void buildView() {
		statusLabel = createLabel(initialState().statusText(), STATUS_LABEL_ID);
		timerLabel = createLabel(String.valueOf(initialState().value()), TIMER_LABEL_ID);
		runCountLabel = createLabel(String.valueOf(initialState().runCount()), RUN_COUNT_LABEL_ID);
		controlCountdownTimerButton = createButton(initialState().controlButtonText(), CONTROL_BUTTON_ID);
		controlCountdownTimerButton.setOnAction((e) -> initialState().status().onControlButtonClick(eventSender));
		stopCountdownTimerButton = createButton(initialState().stopButtonText(), STOP_BUTTON_ID);
		stopCountdownTimerButton.setDisable(!initialState().isStopButtonClickable());
		stopCountdownTimerButton.setOnAction((e) -> initialState().status().onStopButtonClick(eventSender));
		addToRoot(statusLabel, runCountLabel, timerLabel, controlCountdownTimerButton, stopCountdownTimerButton);
	}
	
	private void addStateChangeListener() {
		stateHolder.addStateChangeListener((observable, oldValue, newValue) -> {
			updateView(newValue);
		});
	}

	private Label createLabel(String text, String id) {
		final var newLabel = new Label(text);
		newLabel.setId(id);
		return newLabel;
	}

	private Button createButton(String text, String id) {
		final var newButton = new Button(text);
		newButton.setId(id);
		return newButton;
	}

	private CountdownTimerState initialState() {
		return stateHolder.getState();
	}

	private boolean addToRoot(Node... nodes) {
		return getChildren().addAll(nodes);
	}

	private void updateView(CountdownTimerState currentState) {
		timerLabel.setText(String.valueOf(currentState.value()));
		statusLabel.setText(currentState.statusText());
		runCountLabel.setText(String.valueOf(currentState.runCount()));
		controlCountdownTimerButton.setText(currentState.controlButtonText());
		stopCountdownTimerButton.setText(currentState.stopButtonText());
		stopCountdownTimerButton.setDisable(!currentState.isStopButtonClickable());
	}

}
