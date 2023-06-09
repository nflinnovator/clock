package clock.unit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.lib.concurrent.DeterministicExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import clock.domain.CountdownTimer;
import clock.domain.CountdownTimerListener;

@DisplayName("Countdown Timer Unit Test Case")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CountdownTimerTest {
	
	private final Mockery context = new Mockery();
	private final Integer duration = 1;
    private final DeterministicExecutor executor = new DeterministicExecutor();
	private final CountdownTimerListener listener = context.mock(CountdownTimerListener.class);
	private final CountdownTimer timer = new CountdownTimer(duration,executor,listener);
	private final States state = context.states("state").startsAs("Off");
	
	@Test
	@Order(1)
	void timerStartsInTheRightState() {
		assertThat(Boolean.FALSE, equalTo(status()));
		assertThat(duration, equalTo(currentValue()));
	}
	
	@Test
	@Order(2)
	void reportsStartedWhenReceivesTimerStartedEvent() {
		
		context.checking(new Expectations() {{
			oneOf(listener).startCountdownTimer();then(state.is("On"));
			ignoring(listener).updateCountdownTimer();
			ignoring(listener).timeoutCountdownTimer();
		}});
		
		timer.onStart();
		
		context.assertIsSatisfied();
		
		assertThat(Boolean.TRUE, equalTo(status()));
		assertThat(duration, equalTo(currentValue()));
	}
	
	@Test
	@Order(3)
	void sendsUpdatesEverySecondOnceStarted() {
		context.checking(new Expectations() {{
			allowing(listener).startCountdownTimer();then(state.is("On"));
			exactly(duration).of(listener).updateCountdownTimer();then(state.is("Off"));
			ignoring(listener).timeoutCountdownTimer();
		}});
		
		timer.onStart();
		executor.runUntilIdle();
		
		context.assertIsSatisfied();
		
		assertThat(Boolean.FALSE, equalTo(status()));
		assertThat(currentValue(),equalTo(0));
	}
	
	@Test
	@Order(4)
	void reportsTimeoutWhenCountdownGetsToZero() {
		context.checking(new Expectations() {{
			allowing(listener).startCountdownTimer();then(state.is("On"));
			allowing(listener).updateCountdownTimer();then(state.is("Off"));
			oneOf(listener).timeoutCountdownTimer();when(state.is("Off"));
		}});
		
		timer.onStart();
		executor.runUntilIdle();
		
		context.assertIsSatisfied();
		
	}
	
	private Boolean status() {
		return timer.getCurrentState().status();
	}
	
	private Integer currentValue() {
		return timer.getCurrentState().currentValue();
	}


}
