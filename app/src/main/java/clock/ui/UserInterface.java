package clock.ui;

import clock.adapters.UserEventAnnouncer;
import clock.adapters.UserEventAnnouncer.TimerEvent;
import clock.domain.CountdownTimer;
import clock.domain.CountdownTimer.CountdownTimerStatus;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserInterface {

	public static final String[] TIMER_STATUSES = { "OFF", "ON", "PAUSED", "STOPPED" };
	public static final String[] START_BUTTON_TEXTS = { "Start", "Pause", "Resume", "Restart" };

	public final static String TIMER_LABEL_ID = "timerLabelId";
	public final static String RUN_COUNT_LABEL_ID = "runCountLabelId";
	public final static String STATUS_LABEL_ID = "statusLabelId";
	public final static String START_BUTTON_ID = "startButtonId";

	private static final String APPLICATION_NAME = "Clock Application";

	private Label statusLabel, timerLabel, runCountLabel;
	private Button startAndPauseTimerButton;
	private Pane root;

	public UserInterface(Stage stage, CountdownTimer timer) {
		root = new VBox();
		timerLabel = createLabel(String.valueOf(timer.getCurrentState().currentValue()), TIMER_LABEL_ID);
		runCountLabel = createLabel(String.valueOf(timer.getCurrentState().runCount()), RUN_COUNT_LABEL_ID);
		statusLabel = createLabel(displayStatus(timer.getCurrentState().status()), STATUS_LABEL_ID);
		startAndPauseTimerButton = createButton(displayStartButtonText(timer.getCurrentState().status()),
				START_BUTTON_ID);
		startAndPauseTimerButton.setOnAction(new EventHandler<ActionEvent>() {
			final UserEventAnnouncer announcer = new UserEventAnnouncer(timer);

			@Override
			public void handle(ActionEvent event) {
				if (displayStartButtonText(CountdownTimerStatus.OFF).equals(((Button) event.getSource()).getText())) {
					announcer.announce(TimerEvent.START);
				} else if (displayStartButtonText(CountdownTimerStatus.ON)
						.equals(((Button) event.getSource()).getText())) {
					announcer.announce(TimerEvent.PAUSE);
				} else if (displayStartButtonText(CountdownTimerStatus.PAUSED)
						.equals(((Button) event.getSource()).getText())) {
					announcer.announce(TimerEvent.RESUME);
				} else if (displayStartButtonText(CountdownTimerStatus.STOPPED)
						.equals(((Button) event.getSource()).getText())) {
					announcer.announce(TimerEvent.START);
				} else {
					throw new RuntimeException("Unexpected value");
				}
			}
		});
		addToRoot(statusLabel, runCountLabel, timerLabel, startAndPauseTimerButton);
		final var scene = new Scene(root, 300, 400);
		stage.setScene(scene);
		stage.setTitle(APPLICATION_NAME);
		stage.show();
	}

	public void update(CountdownTimer timer) {
		updateStartAndPauseButtonText(timer.getCurrentState().status());
		updateStatusLabel(timer.getCurrentState().status());
		updateTimerLabel(timer.getCurrentState().currentValue());
		updateRunCountLabel(timer.getCurrentState().runCount());
	}

	private void updateStartAndPauseButtonText(CountdownTimerStatus status) {
		startAndPauseTimerButton.setText(displayStartButtonText(status));
	}

	private void updateStatusLabel(CountdownTimerStatus status) {
		statusLabel.setText(displayStatus(status));
	}

	private void updateTimerLabel(Integer currentValue) {
		timerLabel.setText(String.valueOf(currentValue));
	}

	private void updateRunCountLabel(Integer runCount) {
		runCountLabel.setText(String.valueOf(runCount));
	}

	private String displayStatus(CountdownTimerStatus status) {
		switch (status) {
		case OFF: {
			return TIMER_STATUSES[CountdownTimerStatus.OFF.ordinal()];
		}
		case ON: {
			return TIMER_STATUSES[CountdownTimerStatus.ON.ordinal()];
		}
		case PAUSED: {
			return TIMER_STATUSES[CountdownTimerStatus.PAUSED.ordinal()];
		}
		case STOPPED: {
			return TIMER_STATUSES[CountdownTimerStatus.STOPPED.ordinal()];
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + status);
		}
	}

	private String displayStartButtonText(CountdownTimerStatus status) {
		switch (status) {
		case OFF: {
			return START_BUTTON_TEXTS[CountdownTimerStatus.OFF.ordinal()];
		}
		case ON: {
			return START_BUTTON_TEXTS[CountdownTimerStatus.ON.ordinal()];
		}
		case PAUSED: {
			return START_BUTTON_TEXTS[CountdownTimerStatus.PAUSED.ordinal()];
		}
		case STOPPED: {
			return START_BUTTON_TEXTS[CountdownTimerStatus.STOPPED.ordinal()];
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + status);
		}
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
