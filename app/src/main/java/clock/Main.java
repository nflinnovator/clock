package clock;

import java.time.LocalTime;
import java.util.concurrent.Executors;

import clock.adapters.input.CountdownTimerEventNotifier;
import clock.adapters.input.WatchEventNotifier;
import clock.adapters.output.JavaFXThreadForStateObserver;
import clock.domain.StateHolder;
import clock.domain.countdowntimer.CountdownTimerEventSender;
import clock.domain.countdowntimer.CountdownTimerManager;
import clock.domain.countdowntimer.CountdownTimerState;
import clock.domain.countdowntimer.DefaultCountdownTimer;
import clock.domain.countdowntimer.DefaultCountdownTimerManager;
import clock.domain.soundplayer.ClockSoundPlayer;
import clock.domain.soundplayer.SoundPlayerState;
import clock.domain.watch.DefaultWatch;
import clock.domain.watch.WatchEventSender;
import clock.domain.watch.WatchState;
import clock.sound.JavaFxSoundPlayer;
import clock.ui.LayoutManager;
import clock.ui.views.CountdownTimerView;
import clock.ui.views.WatchView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static final String APPLICATION_NAME = "Clock Application";

	private static Integer initialValue;

	private final StateHolder<CountdownTimerState> countdownTimerStateHolder = new StateHolder<>();
	private final StateHolder<WatchState> watchStateHolder = new StateHolder<>();
	private final StateHolder<SoundPlayerState> countdownTimerSoundPlayerStateHolder = new StateHolder<>();
	private final StateHolder<SoundPlayerState> watchSoundPlayerStateHolder = new StateHolder<>();
	
	private final ClockSoundPlayer countdownTimerSoundPlayer = new ClockSoundPlayer(),
			watchSoundPlayer = new ClockSoundPlayer();
	
	private final DefaultCountdownTimer countdownTimer = new DefaultCountdownTimer(Executors.newSingleThreadExecutor(),
			countdownTimerSoundPlayer);
	private final DefaultWatch watch = new DefaultWatch(Executors.newSingleThreadExecutor(), watchSoundPlayer);
	
	private final CountdownTimerManager countdownTimerManager = new DefaultCountdownTimerManager(countdownTimer);
	
	private final CountdownTimerEventSender countdownTimerEventSender = new CountdownTimerEventNotifier(
			countdownTimerManager);
	private final WatchEventSender watchEventSender = new WatchEventNotifier(watch);

	public static void main(String... args) {
		initialValue = Integer.parseInt(args[0]);
		launch(args);
	}

	@Override
	public void init() throws Exception {
		countdownTimer.addObserver(new JavaFXThreadForStateObserver<CountdownTimerState>(countdownTimerStateHolder));
		watch.addObserver(new JavaFXThreadForStateObserver<WatchState>(watchStateHolder));
		countdownTimerSoundPlayer.addObserver(countdownTimerSoundPlayerStateHolder);
		watchSoundPlayer.addObserver(watchSoundPlayerStateHolder);
		countdownTimerEventSender.onInitialize(initialValue);
		watchEventSender.onStart(LocalTime.now());
		new JavaFxSoundPlayer(countdownTimerSoundPlayerStateHolder);
		new JavaFxSoundPlayer(watchSoundPlayerStateHolder);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(APPLICATION_NAME);
		primaryStage.setScene(new Scene(
				new LayoutManager(new CountdownTimerView(countdownTimerStateHolder, countdownTimerEventSender),
						new WatchView(watchStateHolder)),
				300, 300));
		primaryStage.show();
	}

}
