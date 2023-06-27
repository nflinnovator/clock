package clock.domain.countdowntimer;

public interface CountdownTimerEventSender {
	void onInitialize(Integer initialValue); 
	void onStart(); 
	void onPause();
	void onResume();
	void onStop();
}
