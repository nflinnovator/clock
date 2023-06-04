package clock;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class UserEventHandler implements EventHandler<ActionEvent>{
	
	private final UserEventListener listener;
	
	public UserEventHandler(UserEventListener listener) {
	  this.listener = listener;
	}

	@Override
	public void handle(ActionEvent event) {
		listener.countdownStarted();
	}

}
