package clock.stateholders;

import clock.domain.soundplayer.SoundPlayerState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

public class SoundPlayerStateHolder {

	private SoundPlayerState currentState;

	private IntegerProperty tickCount = new SimpleIntegerProperty();
	private BooleanProperty hasBeeped = new SimpleBooleanProperty();

	public final Integer getTickCount() {
		return tickCount.getValue();
	}

	public final Boolean getHasBeeped() {
		return hasBeeped.getValue();
	}

	public void addTickCountChangeListener(ChangeListener<Number> tickCountChangeListener) {
		tickCount.addListener(tickCountChangeListener);
	}

	public void addHasBeepedChangeListener(ChangeListener<Boolean> hasBeepedChangeListener) {
		hasBeeped.addListener(hasBeepedChangeListener);
	}

	public void update(SoundPlayerState newState) {
		currentState = newState;
		updateTickCount();
		updateHasBeeped();
	}

	private void updateTickCount() {
		tickCount.set(currentState.tickCount());
	}

	private void updateHasBeeped() {
		hasBeeped.set(currentState.status().hasBeeped());
	}

}
