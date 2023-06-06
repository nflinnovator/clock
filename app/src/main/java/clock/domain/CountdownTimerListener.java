package clock.domain;

import java.util.EventListener;

public interface CountdownTimerListener extends EventListener{
       void countdownTimerStarted();
}
