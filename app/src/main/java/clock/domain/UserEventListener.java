package clock.domain;

import java.util.EventListener;

public interface UserEventListener extends EventListener{
  void onStart();
  void onPause();
  void onResume();
  void onStop();
}
