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
import clock.domain.CountdownTimer.CountdownTimerStatus;
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
		assertThat(CountdownTimerStatus.OFF, equalTo(status()));
		assertThat(duration, equalTo(currentValue()));
		assertThat(0, equalTo(runCount()));
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
		
		assertThat(CountdownTimerStatus.ON, equalTo(status()));
		assertThat(duration, equalTo(currentValue()));
		assertThat(1, equalTo(runCount()));
	}
	
	@Test
	@Order(3)
	void sendsUpdatesEverySecondOnceStarted() {
		context.checking(new Expectations() {{
			allowing(listener).startCountdownTimer();then(state.is("On"));
			exactly(duration).of(listener).updateCountdownTimer();when(state.is("On"));
			ignoring(listener).timeoutCountdownTimer();
		}});
		
		timer.onStart();
		executor.runUntilIdle();
		
		context.assertIsSatisfied();
		
		assertThat(CountdownTimerStatus.STOPPED, equalTo(status()));
		assertThat(currentValue(),equalTo(0));
		assertThat(1, equalTo(runCount()));
	}
	
	@Test
	@Order(4)
	void reportsTimeoutWhenCountdownGetsToZero() {
		context.checking(new Expectations() {{
			allowing(listener).startCountdownTimer();then(state.is("On"));
			allowing(listener).updateCountdownTimer();when(state.is("On"));
			oneOf(listener).timeoutCountdownTimer();then(state.is("Off"));
		}});
		
		timer.onStart();
		executor.runUntilIdle();
		
		context.assertIsSatisfied();
		
	}
	
	@Test
	@Order(5)
	void timerIsInTheRightStateWhenStartedSeveralTimes() {
		context.checking(new Expectations() {{
			exactly(2).of(listener).startCountdownTimer();then(state.is("On"));
			ignoring(listener).updateCountdownTimer();
			ignoring(listener).timeoutCountdownTimer();
		}});
		
		timer.onStart();
		timer.onStart();
		
		context.assertIsSatisfied();
		
		assertThat(CountdownTimerStatus.ON, equalTo(status()));
		assertThat(duration, equalTo(currentValue()));
		assertThat(2, equalTo(runCount()));
	}
	
	@Test
	@Order(6)
	void sendsNptificationWhenReceivesCountdownTimerPausedEvent() {
		
		context.checking(new Expectations() {{
			oneOf(listener).pauseCountdownTimer();then(state.is("Paused"));
		}});
		
		timer.onPause();
		
		context.assertIsSatisfied();
		
		assertThat(CountdownTimerStatus.PAUSED, equalTo(status()));
	}
	
	private CountdownTimerStatus status() {
		return timer.getCurrentState().status();
	}
	
	private Integer currentValue() {
		return timer.getCurrentState().currentValue();
	}
	
	private Integer runCount() {
		return timer.getCurrentState().runCount();
	}


}
