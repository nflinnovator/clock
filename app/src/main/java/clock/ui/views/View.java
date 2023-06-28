package clock.ui.views;

import clock.domain.StateHolder;
import javafx.scene.layout.VBox;

abstract class View<E> {

	protected final StateHolder<E> stateHolder;

	protected final VBox container = new VBox();

	View(StateHolder<E> stateHolder) {
		this.stateHolder = stateHolder;
		buildView();
		addStateChangeListener();
	}

	protected E initialState() {
		return stateHolder.getState();
	}

	protected void addStateChangeListener() {
		stateHolder.addStateChangeListener((observable, oldValue, newValue) -> {
			updateView(newValue);
		});
	}

	protected abstract void buildView();

	protected abstract void updateView(E currentState);

	public VBox getContainer() {
		return container;
	}

}
