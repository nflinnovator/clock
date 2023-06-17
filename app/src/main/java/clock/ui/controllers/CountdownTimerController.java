package clock.ui.controllers;

import static clock.adapters.CountdownTimerEvent.PAUSE;
import static clock.adapters.CountdownTimerEvent.RESUME;
import static clock.adapters.CountdownTimerEvent.START;
import static clock.adapters.CountdownTimerEvent.STOP;

import clock.adapters.CountdownTimerEventAnnouncer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CountdownTimerController implements EventHandler<ActionEvent> {

	private final CountdownTimerEventAnnouncer announcer;
	
	public CountdownTimerController(CountdownTimerEventAnnouncer announcer) {
		this.announcer = announcer;
	}

	@Override
	public void handle(ActionEvent event) {
		final var source = ((Button) event.getSource()).getText();
		switch (source) {
		case "Start": {
			announcer.announce(START);
			break;
		}
		case "Pause": {
			announcer.announce(PAUSE);
			break;
		}
		case "Resume": {
			announcer.announce(RESUME);
			break;
		}
		case "Restart": {
			announcer.announce(START);
			break;
		}
		case "Stop": {
			announcer.announce(STOP);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + source);
		}
	}

}
