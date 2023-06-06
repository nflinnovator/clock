package clock.unit;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.Test;

import clock.domain.CountdownTimer;
import clock.domain.CountdownTimerListener;

class CountdownTimerTest {
	
	private final Mockery context = new Mockery();
	private final CountdownTimerListener listener = context.mock(CountdownTimerListener.class);
	private final CountdownTimer timer = new CountdownTimer(listener);

	@Test
	void reportsStartedWhenReceivesTimerStartedEvent() {
		
		context.checking(new Expectations() {{
			oneOf(listener).countdownTimerStarted();
		}});
		
		timer.onTimerStartedEvent();
		
		context.assertIsSatisfied();
	}

}
