package clock.ui;

import clock.ui.views.CountdownTimerView;
import clock.ui.views.WatchView;
import javafx.scene.layout.BorderPane;

public class LayoutManager extends BorderPane{
	
	private final CountdownTimerView countdownTimerView;
	private final WatchView watchView;
	
	public LayoutManager(CountdownTimerView countdownTimerView,WatchView watchView) {
		this.countdownTimerView = countdownTimerView;
		this.watchView = watchView;
		build();
	}
	
	public void build() {
		setTop(countdownTimerView.getContainer());
		setCenter(watchView.getContainer());
	}

}
