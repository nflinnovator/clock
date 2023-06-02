package clock;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationAdapter;
import org.testfx.framework.junit5.ApplicationFixture;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

class ApplicationDriver extends FxRobot implements ApplicationFixture {

	public void launch(Class<? extends Application> appClass, String... appArgs) throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(appClass, appArgs);
	}

	void setup() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(() -> new ApplicationAdapter(this));
	}

	void showsCountdownTimerWithDefaultValue() {
		sleep(1000);
		 verifyThat("#label", hasText("59"));
	}

	void dispose() throws Exception {
		// release all keys
		release(new KeyCode[0]);
		// release all mouse buttons
		release(new MouseButton[0]);
		FxToolkit.cleanupStages();
		FxToolkit.cleanupApplication(new ApplicationAdapter(this));
	}

	@Override
	public void init() throws Exception {}

	@Override
	public void start(Stage stage) throws Exception {}

	@Override
	public void stop() throws Exception {}
}
