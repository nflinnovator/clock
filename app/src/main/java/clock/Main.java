package clock;

import java.util.concurrent.Executors;

import clock.adapters.input.CountdownTimerEventNotifier;
import clock.adapters.output.JavaFXThreadForCountdownTimerObserver;
import clock.domain.countdowntimer.CountdownTimerEventSender;
import clock.domain.countdowntimer.CountdownTimerManager;
import clock.domain.countdowntimer.CountdownTimerState;
import clock.domain.countdowntimer.DefaultCountdownTimer;
import clock.domain.countdowntimer.DefaultCountdownTimerManager;
import clock.domain.soundplayer.ClockSoundPlayer;
import clock.domain.soundplayer.SoundPlayerState;
import clock.shared.StateHolder;
import clock.sound.JavaFxSoundPlayer;
import clock.ui.views.CountdownTimerView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static final String APPLICATION_NAME = "Clock Application";

	private static Integer initialValue;

	private final StateHolder<CountdownTimerState> countdownTimerStateHolder = new StateHolder<>();
	private final StateHolder<SoundPlayerState> soundPlayerStateHolder = new StateHolder<>();
	private final ClockSoundPlayer soundPlayer = new ClockSoundPlayer();
	private final DefaultCountdownTimer countdownTimer = new DefaultCountdownTimer(Executors.newSingleThreadExecutor(),
			soundPlayer);
	private final CountdownTimerManager countdownTimerManager = new DefaultCountdownTimerManager(countdownTimer);
	private final CountdownTimerEventSender countdownTimerEventSender = new CountdownTimerEventNotifier(
			countdownTimerManager);

	public static void main(String... args) {
		initialValue = Integer.parseInt(args[0]);
		launch(args);
	}

	@Override
	public void init() throws Exception {
		countdownTimer.addObserver(new JavaFXThreadForCountdownTimerObserver(countdownTimerStateHolder));
		soundPlayer.addObserver(soundPlayerStateHolder);
		countdownTimerEventSender.onInitialize(initialValue);
		new JavaFxSoundPlayer(soundPlayerStateHolder);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(APPLICATION_NAME);
		primaryStage.setScene(
				new Scene(new CountdownTimerView(countdownTimerStateHolder, countdownTimerEventSender), 300, 300));
		primaryStage.show();
	}

}
