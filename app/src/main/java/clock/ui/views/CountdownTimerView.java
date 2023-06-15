package clock.ui.views;

import clock.adapters.TimerEventAnnouncer;
import clock.domain.CountdownTimer;
import clock.ui.controllers.CountdownTimerController;
import clock.ui.viewmodels.CountdownTimerViewModel;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CountdownTimerView extends VBox {

	public final static String TIMER_LABEL_ID = "timerLabelId";
	public final static String RUN_COUNT_LABEL_ID = "runCountLabelId";
	public final static String STATUS_LABEL_ID = "statusLabelId";
	public final static String START_BUTTON_ID = "startButtonId";
	public final static String STOP_BUTTON_ID = "stopButtonId";

	private final CountdownTimerViewModel viewModel;

	private Label statusLabel, timerLabel, runCountLabel;
	private Button startTimerButton, stopTimerButton;
	private CountdownTimer countdownTimer;

	public CountdownTimerView(CountdownTimerViewModel viewModel) {
		this.viewModel = viewModel;
	}

	public void buildView() {
		timerLabel = createLabel(value(), TIMER_LABEL_ID);
		runCountLabel = createLabel(runCount(), RUN_COUNT_LABEL_ID);
		statusLabel = createLabel(status(), STATUS_LABEL_ID);
		startTimerButton = createButton(startButtonDisplay(), START_BUTTON_ID);
		startTimerButton.setOnAction(
				new CountdownTimerController(currentTimerValue(), new TimerEventAnnouncer(countdownTimer)));
		stopTimerButton = createButton("Stop", STOP_BUTTON_ID);
		stopTimerButton.setDisable(stopButtonDisability());
		stopTimerButton.setOnAction(new CountdownTimerController(new TimerEventAnnouncer(countdownTimer)));
		addToRoot(statusLabel, runCountLabel, timerLabel, startTimerButton, stopTimerButton);
	}

	public void addCountdownTimerEventListener(CountdownTimer countdownTimer) {
		this.countdownTimer = countdownTimer;
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

	private Integer currentTimerValue() {
		return Integer.parseInt(timerLabel.getText());
	}

	private String status() {
		return viewModel.status();
	}

	private String value() {
		return viewModel.value();
	}

	private String runCount() {
		return viewModel.runCount();
	}

	private String startButtonDisplay() {
		return viewModel.startButtonDisplay();
	}

	private Boolean stopButtonDisability() {
		return viewModel.stopButtonDisability();
	}

}
