package clock.ui.controllers;

import static clock.adapters.TimerEvent.PAUSE;
import static clock.adapters.TimerEvent.RESUME;
import static clock.adapters.TimerEvent.START;
import static clock.adapters.TimerEvent.STOP;

import clock.adapters.TimerEventAnnouncer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CountdownTimerController implements EventHandler<ActionEvent> {

	private final TimerEventAnnouncer announcer;
	private Integer currentValue;
	
	public CountdownTimerController(TimerEventAnnouncer announcer) {
		this.announcer = announcer;
	}

	public CountdownTimerController(Integer currentValue, TimerEventAnnouncer announcer) {
		this.currentValue = currentValue;
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
			announcer.announce(PAUSE, currentValue);
			break;
		}
		case "Resume": {
			announcer.announce(RESUME, currentValue);
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
