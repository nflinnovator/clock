package clock;

import static clock.ui.UserInterface.START_TIMER_BUTTON_ID;
import static clock.ui.UserInterface.STATUS_LABEL_ID;
import static clock.ui.UserInterface.TIMER_LABEL_ID;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.util.concurrent.TimeUnit;

import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationAdapter;
import org.testfx.framework.junit5.ApplicationFixture;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

class ApplicationDriver extends FxRobot implements ApplicationFixture {
	
	private static final String NO_PARAMETER = null;

	private void launch(Class<? extends Application> appClass, String... appArgs) throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(appClass, appArgs);
	}

	void setup() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(() -> new ApplicationAdapter(this));
	}
	
	void startApplication() throws Exception {
		launch(ClockApplication.class, NO_PARAMETER);
	}

	void showCountdownTimerWithValue(Integer value) {
		sleep(1000, TimeUnit.MILLISECONDS);
		verifyThat(formatId(TIMER_LABEL_ID), hasText(String.valueOf(value)));
	}
	
	void showCountdownTimerStatus(String status) {
		sleep(1000, TimeUnit.MILLISECONDS);
		verifyThat(formatId(STATUS_LABEL_ID), hasText(status));
	}
    
    
	void startCountdownTimer() {
		sleep(1000, TimeUnit.MILLISECONDS);
		clickOn(formatId(START_TIMER_BUTTON_ID));
	}
	/*
	void hasShownCountdownTimerDecrementingEverySecondFor(Integer value) {
		
	}*/

	void dispose() throws Exception {
		// release all keys
		release(new KeyCode[0]);
		// release all mouse buttons
		release(new MouseButton[0]);
		FxToolkit.cleanupStages();
		FxToolkit.cleanupApplication(new ApplicationAdapter(this));
	}
	
	private String formatId(String id) {
		return "#"+id;
	}

	@Override
	public void init() throws Exception {
	}

	@Override
	public void start(Stage stage) throws Exception {
	}

	@Override
	public void stop() throws Exception {
	}
}
