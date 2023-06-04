package clock;

import java.util.EventListener;

public interface UserEventListener extends EventListener{
  void countdownStarted();
}
