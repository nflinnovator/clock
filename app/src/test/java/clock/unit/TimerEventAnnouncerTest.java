package clock.unit;

import static clock.adapters.CountdownTimerEvent.INITIALIZE;
import static clock.adapters.CountdownTimerEvent.PAUSE;
import static clock.adapters.CountdownTimerEvent.RESUME;
import static clock.adapters.CountdownTimerEvent.START;
import static clock.adapters.CountdownTimerEvent.STOP;
import static clock.adapters.CountdownTimerEvent.RUN;
import static clock.adapters.CountdownTimerEvent.RESTART;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.adapters.CountdownTimerEvent;
import clock.adapters.CountdownTimerEventAnnouncer;
import clock.domain.CountdownTimer;

@DisplayName("UserEventHandler Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TimerEventAnnouncerTest {
	private final Mockery context = new Mockery();
	private final CountdownTimer listener = context.mock(CountdownTimer.class);
	private final CountdownTimerEventAnnouncer announcer = new CountdownTimerEventAnnouncer(listener);
	private final static Integer CURRENT_COUNTDOWN_TIMER_VALUE = 1; 
	
	@Test
	@Order(1)
	void notifiesCountdownTimerInitializeWhenInitializeTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).initialize(CURRENT_COUNTDOWN_TIMER_VALUE);
			}
		});

		final CountdownTimerEvent event = INITIALIZE; 
		announcer.announce(event,CURRENT_COUNTDOWN_TIMER_VALUE);

		context.assertIsSatisfied();
	}

	@Test
	@Order(2)
	void notifiesCountdownTimerStartWhenStartCountdownTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).start();
			}
		});

		final CountdownTimerEvent event = START; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}
	
	@Test 
	@Order(3)
	void notifiesCountdownTimerRunWhenRunCountdownTimerEventReceived() { 

		context.checking(new Expectations() {
			{
				oneOf(listener).run();
			}
		});

		final CountdownTimerEvent event = RUN; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}
	
	@Test 
	@Order(4)
	void notifiesCountdownTimerPauseWhenPauseCountdownTimerEventReceived() { 

		context.checking(new Expectations() {
			{
				oneOf(listener).pause();
			}
		});

		final CountdownTimerEvent event = PAUSE; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}
	
	@Test
	@Order(5)
	void notifiesCountdownTimerResumeWhenResumeCountdownTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).resume();
			}
		});

		final CountdownTimerEvent event = RESUME; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}
	
	@Test
	@Order(6)
	void notifiesCountdownTimerStopWhenStopCountdownTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).stop();
			}
		});

		final CountdownTimerEvent event = STOP; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}
	
	@Test
	@Order(7)
	void notifiesCountdownTimerRestartWhenRestartCountdownTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).restart();
			}
		});

		final CountdownTimerEvent event = RESTART; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}

}
