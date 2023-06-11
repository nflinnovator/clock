package clock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import clock.domain.CountdownTimer;
import clock.domain.CountdownTimerListener;
import clock.ui.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ClockApplication extends Application implements CountdownTimerListener {

	public static final Integer TIMER_INITIAL_VALUE = 5;
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private final CountdownTimer timer = new CountdownTimer(TIMER_INITIAL_VALUE, executor, this);
	private UserInterface userInterface;

	public static void main(String... args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		userInterface = new UserInterface(primaryStage, timer);
	}

	@Override
	public void startCountdownTimer() {
		Platform.runLater(() -> updateUserInterface());
	}

	@Override
	public void updateCountdownTimer() {
		Platform.runLater(() -> updateUserInterface());
	}
	
	@Override
	public void timeoutCountdownTimer() {
		Platform.runLater(() -> updateUserInterface());
	}
	
	@Override
	public void pauseCountdownTimer() {
		Platform.runLater(() -> updateUserInterface());
	}

	private void updateUserInterface() {
		userInterface.update(timer);
	}
}
