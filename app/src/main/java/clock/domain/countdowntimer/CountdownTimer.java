package clock.domain.countdowntimer;

public interface CountdownTimer{
  void initialize(Integer initialValue);
  void start();
  void pause();
  void resume();
  void stop();
  void restart();
  public void addCountdownTimerStateChangeListener(CountdownTimerStateChangeListener listener);
}
