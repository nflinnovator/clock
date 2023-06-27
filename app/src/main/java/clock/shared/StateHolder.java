package clock.shared;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

public class StateHolder<E> implements Observer<E>{

	protected ObjectProperty<E> state = new SimpleObjectProperty<>();
	
	public E getState() {
		return state.getValue();
	}

	public void addStateChangeListener(ChangeListener<E> stateChangeListener) {
		state.addListener(stateChangeListener);
	}

	@Override
	public void onStateChange(E newState) {
		state.set(newState);
	}

}
