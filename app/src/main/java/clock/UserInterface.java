package clock;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserInterface extends Stage{
	
	public UserInterface() {
		super();
		Parent sceneRoot = new Label("My Countdown Timer App");
        Scene scene = new Scene(sceneRoot, 400, 400);
        setScene(scene);
        setTitle("Clock Application");
        show();
	}

}
