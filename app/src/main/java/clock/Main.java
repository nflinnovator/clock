package clock;

import java.util.concurrent.Executors;

import clock.adapters.input.CountdownTimerEventNotifier;
import clock.adapters.output.CountdownTimerStateChangeNotifier;
import clock.adapters.output.JavaFXThreadForCountdownTimerListener;
import clock.adapters.output.SoundPlayerStateChangeNotifier;
import clock.domain.countdowntimer.CountdownTimer;
import clock.domain.countdowntimer.CountdownTimerEventSender;
import clock.domain.countdowntimer.DefaultCountdownTimer;
import clock.domain.soundplayer.ClockSoundPlayer;
import clock.domain.soundplayer.SoundPlayer;
import clock.sound.JavaFxSoundPlayer;
import clock.stateholders.CountdownTimerStateHolder;
import clock.stateholders.SoundPlayerStateHolder;
import clock.ui.views.CountdownTimerView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static final String APPLICATION_NAME = "Clock Application";

	private static Integer initialValue;

	private final CountdownTimerStateHolder countdownTimerStateHolder = new CountdownTimerStateHolder();
	private final SoundPlayerStateHolder soundPlayerStateHolder = new SoundPlayerStateHolder();
	private final SoundPlayer soundPlayer = new ClockSoundPlayer();
	private final CountdownTimer countdownTimer = new DefaultCountdownTimer(Executors.newSingleThreadExecutor(),
			soundPlayer);
	private final CountdownTimerEventSender eventSender = new CountdownTimerEventNotifier(countdownTimer);

	public static void main(String... args) {
		initialValue = Integer.parseInt(args[0]);
		launch(args);
	}

	@Override
	public void init() throws Exception {
		countdownTimer.addCountdownTimerStateChangeListener(new JavaFXThreadForCountdownTimerListener(
				new CountdownTimerStateChangeNotifier(countdownTimerStateHolder)));
		soundPlayer.addSoundPlayerStateChangeListener(new SoundPlayerStateChangeNotifier(soundPlayerStateHolder));
		eventSender.onInitialize(initialValue);
		countdownTimerStateHolder.setCountdownTimerEventSender(eventSender);
		new JavaFxSoundPlayer(soundPlayerStateHolder);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(APPLICATION_NAME);
		primaryStage.setScene(new Scene(new CountdownTimerView(countdownTimerStateHolder), 300, 300));
		primaryStage.show();
	}

}
