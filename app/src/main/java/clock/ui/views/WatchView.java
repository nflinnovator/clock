package clock.ui.views;

import clock.domain.StateHolder;
import clock.domain.watch.WatchState;
import javafx.scene.control.Label;

public class WatchView extends View<WatchState> {

	private Label watchLabel;

	public WatchView(StateHolder<WatchState> stateHolder) {
		super(stateHolder);
	}

	@Override
	protected void buildView() {
		watchLabel = ViewUtilities.createLabel(format(initialState()), "watchLabelId");
		ViewUtilities.addToRoot(container, watchLabel);
	}

	@Override
	protected void updateView(WatchState currentState) {
		watchLabel.setText(format(currentState));
	}

	private String format(WatchState state) {
		return format(initialState().hour()) + " : " + format(initialState().minute()) + " : "
				+ format(initialState().second());
	}

	private String format(int time) {
		return time <= 9 ? "0" + String.valueOf(time) : String.valueOf(time);
	}

}
