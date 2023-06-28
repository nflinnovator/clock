package clock.domain;

public interface Observer<E> {
	
	abstract void onStateChange(E newState);

}
