package clock.unit;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.Test;

import clock.adapters.UserEventHandler;
import clock.domain.UserEventListener;
import javafx.event.ActionEvent;

class UserEventHandlerTest {
	private final Mockery context = new Mockery();
	private final UserEventListener listener = context.mock(UserEventListener.class);
	private final UserEventHandler handler = new UserEventHandler(listener);

	@Test
	void notifiesTimerStartedWhenStartTimerEventReceived() {

		context.checking(new Expectations() {
			{
				oneOf(listener).onTimerStartedEvent();
			}
		});

		final var event = new ActionEvent();
		handler.handle(event);

		context.assertIsSatisfied();
	}

}
