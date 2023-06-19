package clock;

import java.util.concurrent.Executors;

import clock.adapters.input.CountdownTimerEventSender;
import clock.adapters.input.CountdownTimerEventNotifier;
import clock.adapters.output.CountdownTimerStateChangeNotifier;
import clock.adapters.output.JavaFXThreadForCountdownTimerListener;
import clock.domain.CountdownTimer;
import clock.domain.CountdownTimerStateChangeListener;
import clock.domain.SimpleCountdownTimer;
import clock.ui.viewmodels.CountdownTimerViewModel;
import clock.ui.views.CountdownTimerView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static final String APPLICATION_NAME = "Clock Application";

	private static Integer initialValue;

	private final CountdownTimerViewModel viewModel = new CountdownTimerViewModel();
	private final CountdownTimerStateChangeListener stateChangeListener = new CountdownTimerStateChangeNotifier(
			viewModel);
	private final CountdownTimer countdownTimer = new SimpleCountdownTimer(Executors.newSingleThreadExecutor(),
			new JavaFXThreadForCountdownTimerListener(stateChangeListener));
	private final CountdownTimerEventSender eventSender = new CountdownTimerEventNotifier(countdownTimer);

	public static void main(String... args) {
		initialValue = Integer.parseInt(args[0]);
		launch(args);
	}

	@Override
	public void init() throws Exception {
		eventSender.onInitialize(initialValue);
		viewModel.setCountdownTimerEventSender(eventSender);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(APPLICATION_NAME);
		primaryStage.setScene(new Scene(new CountdownTimerView(viewModel), 300, 400));
		primaryStage.show();
	}

}
