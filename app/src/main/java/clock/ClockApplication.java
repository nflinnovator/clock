package clock;

import clock.domain.CountdownTimer;
import clock.domain.CountdownTimerListener;
import clock.ui.UserInterface;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClockApplication extends Application implements CountdownTimerListener{
	
	private final CountdownTimer timer = new CountdownTimer(this);
	private UserInterface userInterface;
	
	public static void main(String... args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		userInterface = new UserInterface(primaryStage,timer);
	}

	@Override
	public void countdownTimerStarted() {
		updateStatusLabel(timer.getCurrentState().status());
	}
	
	private void updateStatusLabel(boolean status) {
		userInterface.updateStatusLabel(status);
	}

}
