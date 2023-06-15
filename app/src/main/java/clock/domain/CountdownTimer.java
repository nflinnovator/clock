package clock.domain;

public interface CountdownTimer{
  void onInit(Integer initialValue);
  void onStart();
  void onPause(Integer pauseValue);
  void onResume(Integer resumeValue);
  void onStop();
}
