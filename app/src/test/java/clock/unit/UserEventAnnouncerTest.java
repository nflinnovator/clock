package clock.unit;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.adapters.UserEventAnnouncer;
import clock.adapters.UserEventAnnouncer.TimerEvent;
import clock.domain.UserEventListener;

@DisplayName("UserEventHandler Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserEventAnnouncerTest {
	private final Mockery context = new Mockery();
	private final UserEventListener listener = context.mock(UserEventListener.class);
	private final UserEventAnnouncer announcer = new UserEventAnnouncer(listener);

	@Test
	@Order(1)
	void notifiesTimerStartedWhenStartTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).onStart();
			}
		});

		final TimerEvent event = TimerEvent.START; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}
	
	@Test
	@Order(2)
	void notifiesTimerPausedWhenPauseTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).onPause();
			}
		});

		final TimerEvent event = TimerEvent.PAUSE; 
		announcer.announce(event);

		context.assertIsSatisfied();
	}

}
