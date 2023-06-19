package clock.ui.views;

import clock.ui.viewmodels.CountdownTimerViewModel;
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

	private final CountdownTimerViewModel viewModel;

	private Label statusLabel, timerLabel, runCountLabel;
	private Button controlCountdownTimerButton, stopCountdownTimerButton;

	public CountdownTimerView(CountdownTimerViewModel viewModel) {
		this.viewModel = viewModel;
		buildView();
	}

	private void buildView() {
		timerLabel = createLabel(value(), TIMER_LABEL_ID);
		runCountLabel = createLabel(runCount(), RUN_COUNT_LABEL_ID);
		statusLabel = createLabel(status(), STATUS_LABEL_ID);
		controlCountdownTimerButton = createButton(controlButtonText(), CONTROL_BUTTON_ID);
		controlCountdownTimerButton.setOnAction((e) -> viewModel.onControlButtonClick());
		stopCountdownTimerButton = createButton("Stop", STOP_BUTTON_ID);
		stopCountdownTimerButton.setDisable(isStopButtonClickable());
		stopCountdownTimerButton.setOnAction((e) -> viewModel.onStopButtonClick());
		addToRoot(statusLabel, runCountLabel, timerLabel, controlCountdownTimerButton, stopCountdownTimerButton);
		viewModel.addValueChangeListener((observable, oldValue, newValue) -> timerLabel.setText(newValue));
		viewModel.addStatusChangeListener((observable, oldValue, newValue) -> statusLabel.setText(newValue));
		viewModel.addRunCountChangeListener((observable, oldValue, newValue) -> runCountLabel.setText(newValue));
		viewModel.addControlButtonTextChangeListener(
				(observable, oldValue, newValue) -> controlCountdownTimerButton.setText(newValue));
		viewModel.addIsStopButtonClickableChangeListener(
				(observable, oldValue, newValue) -> stopCountdownTimerButton.setDisable(!newValue));
	}

	private Label createLabel(String value, String id) {
		final var newLabel = new Label(value);
		newLabel.setId(id);
		return newLabel;
	}

	private Button createButton(String text, String id) {
		final var newButton = new Button(text);
		newButton.setId(id);
		return newButton;
	}

	private boolean addToRoot(Node... nodes) {
		return getChildren().addAll(nodes);
	}

	private String status() {
		return viewModel.getStatus();
	}

	private String value() {
		return viewModel.getValue();
	}

	private String runCount() {
		return viewModel.getRunCount();
	}

	private String controlButtonText() {
		return viewModel.getControlButtonText();
	}

	private Boolean isStopButtonClickable() {
		return !viewModel.getIsStopButtonClickable();
	}

}
