package clock;

import static clock.ui.views.CountdownTimerView.RUN_COUNT_LABEL_ID;
import static clock.ui.views.CountdownTimerView.CONTROL_BUTTON_ID;
import static clock.ui.views.CountdownTimerView.STATUS_LABEL_ID;
import static clock.ui.views.CountdownTimerView.STOP_BUTTON_ID;
import static clock.ui.views.CountdownTimerView.TIMER_LABEL_ID;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.GeneralMatchers.typeSafeMatcher;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import org.hamcrest.Matcher;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationAdapter;
import org.testfx.framework.junit5.ApplicationFixture;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

class ApplicationDriver extends FxRobot implements ApplicationFixture {

	private static final String COUNTDOWN_TIMER_INITIAL_VALUE = "5";

	private void launch(Class<? extends Application> appClass, String... appArgs) throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(appClass, appArgs);
	}

	void setup() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(() -> new ApplicationAdapter(this));
	}

	void startApplication() throws Exception {
		// launch(Main.class, COUNTDOWN_TIMER_INITIAL_VALUE);
		Main.main(COUNTDOWN_TIMER_INITIAL_VALUE);
	}

	void showsCountdownTimerWithValues(Integer value, Integer runCount, String status) {
		sleep(500);
		verifyThat(node(TIMER_LABEL_ID), hasText(String.valueOf(value)));
		verifyThat(node(RUN_COUNT_LABEL_ID), hasText(String.valueOf(runCount)));
		verifyThat(node(STATUS_LABEL_ID), hasText(status));
	}

	void showCountdownTimerWithValue(Integer value) {
		sleep(100);
		verifyThat(node(TIMER_LABEL_ID), hasText(String.valueOf(value)));
	}

	void showCountdownTimerWithRunCount(Integer runCount) {
		sleep(100);
		verifyThat(node(RUN_COUNT_LABEL_ID), hasText(String.valueOf(runCount)));
	}

	void showCountdownTimerWithStatus(String status) {
		sleep(100);
		verifyThat(node(STATUS_LABEL_ID), hasText(status));
	}

	void startCountdownTimer() {
		startOrPauseOrResumeCountdownTimer();
	}

	void pausesCountdownTimer() {
		startOrPauseOrResumeCountdownTimer();
	}

	void resumesCountdownTimer() {
		startOrPauseOrResumeCountdownTimer();
	}

	void stopsCountdownTimer() {
		sleep(500);
		clickOn(node(STOP_BUTTON_ID));
	}

	void showsStartButtonWithText(String text) {
		sleep(100);
		verifyThat(node(CONTROL_BUTTON_ID), hasText(text));
	}

	void hasDisabledStopButton() {
		sleep(500);
		verifyThat(node(STOP_BUTTON_ID), isDisabled());
	}

	void dispose() throws Exception {
		// release all keys
		release(new KeyCode[0]);
		// release all mouse buttons
		release(new MouseButton[0]);
		FxToolkit.cleanupStages();
		FxToolkit.cleanupApplication(new ApplicationAdapter(this));
	}

	private void startOrPauseOrResumeCountdownTimer() {
		sleep(500);
		clickOn(node(CONTROL_BUTTON_ID));
	}

	private String node(String id) {
		return "#" + id;
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

	private Matcher<Button> isDisabled() {
		return typeSafeMatcher(Button.class, "Button " + "is Disabled", Button::isDisabled);
	}
}
