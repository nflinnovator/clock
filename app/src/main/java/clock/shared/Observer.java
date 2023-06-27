package clock.shared;

public interface Observer<E> {
	
	abstract void onStateChange(E newState);

}
