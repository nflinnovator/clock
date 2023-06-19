package clock.adapters.input;

public interface CountdownTimerEventSender {
	void onInitialize(Integer initialValue);
	void onStart();
	void onRun();
	void onPause();
	void onResume();
	void onStop();
	void onRestart();
}
