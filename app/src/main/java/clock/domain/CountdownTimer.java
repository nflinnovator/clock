package clock.domain;

public interface CountdownTimer{
  void initialize(Integer initialValue);
  void start();
  void pause();
  void resume();
  void stop();
}
