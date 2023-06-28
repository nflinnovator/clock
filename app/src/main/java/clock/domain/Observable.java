package clock.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<E> {
	
	protected List<Observer<E>> observers = new ArrayList<>();
	
	protected E state;
	
	public boolean addObserver(Observer<E> observer) {
		return observers.add(observer);
	}
	
	public boolean addObservers(List<Observer<E>> observers) {
		return this.observers.addAll(observers);
	}
	
	public boolean removeObserver(Observer<E> observer) {
		return observers.remove(observer);
	}
	
	public boolean removeObservers(List<Observer<E>> observers) {
		return this.observers.removeAll(observers);
	}
	
	protected void notifyStateChange() {
		observers.stream().forEach((observer) -> observer.onStateChange(state));
	}

}
