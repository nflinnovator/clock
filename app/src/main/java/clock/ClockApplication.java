package clock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import clock.domain.CountdownTimer;
import clock.domain.CountdownTimerListener;
import clock.ui.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ClockApplication extends Application {

	public static final Integer TIMER_INITIAL_VALUE = 5;
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private final CountdownTimer timer = new CountdownTimer(TIMER_INITIAL_VALUE, executor,
			new CountdownTimerStateNotifier());
	private UserInterface userInterface;

	public static void main(String... args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		userInterface = new UserInterface(primaryStage, timer);
	}

	private class CountdownTimerStateNotifier implements CountdownTimerListener {
		@Override
		public void startCountdownTimer() {
			updateUserInterface();
		}

		@Override
		public void updateCountdownTimer() {
			updateUserInterface();
		}

		@Override
		public void timeoutCountdownTimer() {
			updateUserInterface();
		}

		@Override
		public void pauseCountdownTimer() {
			updateUserInterface();
		}

		@Override
		public void resumeCountdownTimer() {
			updateUserInterface();
		}
		
		@Override
		public void stopCountdownTimer() {
			updateUserInterface();
		}

		private void updateUserInterface() {
			Platform.runLater(() -> userInterface.update(timer));
		}
	}

}
