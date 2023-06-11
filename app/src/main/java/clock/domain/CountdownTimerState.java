package clock.domain;

import clock.domain.CountdownTimer.CountdownTimerStatus;

public record CountdownTimerState(Integer currentValue,Integer runCount,CountdownTimerStatus status) {}
