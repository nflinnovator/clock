package clock;

import java.util.concurrent.Executors;

import clock.adapters.CountdownTimerStateChangeNotifier;
import clock.domain.CountdownTimer;
import clock.domain.SimpleCountdownTimer;
import clock.ui.viewmodels.CountdownTimerViewModel;
import clock.ui.views.CountdownTimerView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private static final String APPLICATION_NAME = "Clock Application";

	private static Integer initialValue;

	private final CountdownTimerViewModel countdownTimerViewModel = new CountdownTimerViewModel();
	private final CountdownTimerView countdownTimerView = new CountdownTimerView(countdownTimerViewModel);
	private final CountdownTimer countdownTimer = new SimpleCountdownTimer(Executors.newSingleThreadExecutor(),
			new CountdownTimerStateChangeNotifier(countdownTimerViewModel));

	public static void main(String... args) {
		initialValue = Integer.parseInt(args[0]);
		launch(args);
	}

	@Override
	public void init() throws Exception {
		countdownTimer.onInit(initialValue);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(APPLICATION_NAME);
		primaryStage.setScene(new Scene(countdownTimerView, 300, 400));
		primaryStage.setOnShowing(new ClockUserInterfaceEventManager());
		primaryStage.show();
	}

	private class ClockUserInterfaceEventManager implements EventHandler<WindowEvent> {

		@Override
		public void handle(WindowEvent event) {
			countdownTimerView.addCountdownTimerEventListener(countdownTimer);
			countdownTimerView.buildView();
		}
	}

}
