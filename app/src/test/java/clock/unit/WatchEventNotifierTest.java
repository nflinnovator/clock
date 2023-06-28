package clock.unit;

import java.time.LocalTime;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.adapters.input.WatchEventNotifier;
import clock.domain.watch.Watch;
import clock.domain.watch.WatchEventSender;

@DisplayName("WatchEventNotifier Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WatchEventNotifierTest {
	private final Mockery context = new Mockery();
	private final Watch watch = context.mock(Watch.class);
	private final WatchEventSender eventSender = new WatchEventNotifier(watch);
	private final static LocalTime CURRENT_TIME = LocalTime.of(10, 30, 20);
	

	@Test
	@Order(1)
	void notifiesStartWhenItReceivesOnStartMessage() {

		context.checking(new Expectations() {
			{
				oneOf(watch).start(CURRENT_TIME);
			}
		});

         eventSender.onStart(CURRENT_TIME);

		context.assertIsSatisfied();
	}
	
	@Test
	@Order(2)
	void notifiesStopWhenItReceivesOnStopMessage() {

		context.checking(new Expectations() {
			{
				oneOf(watch).stop();
			}
		});

         eventSender.onStop();

		context.assertIsSatisfied();
	}

}
