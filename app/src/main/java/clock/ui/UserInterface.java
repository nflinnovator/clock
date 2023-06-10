package clock.ui;

import clock.adapters.UserEventHandler;
import clock.domain.CountdownTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserInterface {

	public final static String TIMER_LABEL_ID = "timerLabelId";
	public final static String RUN_COUNT_LABEL_ID = "runCountLabelId";
	public final static String STATUS_LABEL_ID = "statusLabelId";
	public final static String START_TIMER_BUTTON_ID = "startTimerButtonId";

	private static final String APPLICATION_NAME = "Clock Application";

	private Label statusLabel, timerLabel,runCountLabel;
	private Button startTimerButton;
	private Pane root;

	public UserInterface(Stage stage, CountdownTimer timer) {
		root = new VBox();
		timerLabel = createLabel(String.valueOf(timer.getCurrentState().currentValue()), TIMER_LABEL_ID);
		runCountLabel = createLabel(String.valueOf(timer.getCurrentState().runCount()), RUN_COUNT_LABEL_ID);
		statusLabel = createLabel(displayStatus(timer.getCurrentState().status()), STATUS_LABEL_ID);
		startTimerButton = createButton("Start Timer", START_TIMER_BUTTON_ID);
		startTimerButton.setOnAction(new UserEventHandler(timer));
		addToRoot(statusLabel, runCountLabel,timerLabel, startTimerButton);
		final var scene = new Scene(root, 300, 400);
		stage.setScene(scene);
		stage.setTitle(APPLICATION_NAME);
		stage.show();
	}
	
	public void update(CountdownTimer timer) {
		updateStatusLabel(timer.getCurrentState().status());
		updateTimerLabel(timer.getCurrentState().currentValue());
		updateRunCountLabel(timer.getCurrentState().runCount());
	}

	private void updateStatusLabel(boolean status) {
		statusLabel.setText(displayStatus(status));
	}
	
	private void updateTimerLabel(Integer currentValue) {
		timerLabel.setText(String.valueOf(currentValue));
	}
	
	private void updateRunCountLabel(Integer runCount) {
		runCountLabel.setText(String.valueOf(runCount));
	}

	private String displayStatus(boolean status) {
		return status == false ? "OFF" : "ON";
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
		return root.getChildren().addAll(nodes);
	}

}
