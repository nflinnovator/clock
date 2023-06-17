package clock.domain;

public interface CountdownTimer{
  void initialize(Integer initialValue);
  void start();
  void run();
  void pause();
  void resume();
  void stop();
}
