package clock.unit;

import static clock.adapters.TimerEvent.PAUSE;
import static clock.adapters.TimerEvent.RESUME;
import static clock.adapters.TimerEvent.START;
import static clock.adapters.TimerEvent.STOP;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.adapters.TimerEvent;
import clock.adapters.TimerEventAnnouncer;
import clock.domain.CountdownTimer;

@DisplayName("UserEventHandler Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TimerEventAnnouncerTest {
	private final Mockery context = new Mockery();
	private final CountdownTimer listener = context.mock(CountdownTimer.class);
	private final TimerEventAnnouncer announcer = new TimerEventAnnouncer(listener);
	private final static Integer CURRENT_COUNTDOWN_TIMER_VALUE = 1; 

	@Test
	@Order(1)
	void notifiesTimerStartedWhenStartTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).onStart();
			}
		});

		final TimerEvent event = START; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}
	
	@Test 
	@Order(2)
	void notifiesTimerPausedWhenPauseTimerEventReceived() { 

		context.checking(new Expectations() {
			{
				oneOf(listener).onPause(with(any(Integer.class)));
			}
		});

		final TimerEvent event = PAUSE; 
		announcer.announce(event,CURRENT_COUNTDOWN_TIMER_VALUE);

		context.assertIsSatisfied();
	}
	
	@Test
	@Order(3)
	void notifiesTimerResumedWhenResumeTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).onResume(with(any(Integer.class)));
			}
		});

		final TimerEvent event = RESUME; 
		announcer.announce(event,CURRENT_COUNTDOWN_TIMER_VALUE);

		context.assertIsSatisfied();
	}
	
	@Test
	@Order(4)
	void notifiesTimerStoppedWhenStopTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).onStop();
			}
		});

		final TimerEvent event = STOP; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}

}
