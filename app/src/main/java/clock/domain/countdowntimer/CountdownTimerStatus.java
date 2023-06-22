package clock.domain.countdowntimer;

public enum CountdownTimerStatus {
	INITIALIZED {
		@Override
		public String controlButtonText() {
			return "Start";
		}

		@Override
		public String status() {
			return "OFF";
		}

		@Override
		public Boolean isStopButtonClickable() {
			return Boolean.FALSE;
		}

		@Override
		public void onControlButtonClick(CountdownTimerEventSender event) {
			event.onStart();
		}

		@Override
		public void onStopButtonClick(CountdownTimerEventSender countdownTimer) {
			throw new IllegalArgumentException(ON_STOP_BUTTON_CLICK_ERROR_MESSAGE);
		}
		
		@Override
		Boolean isRunnable() {
			return Boolean.FALSE;
		}
	},
	STARTED, RUNNING, PAUSED {
		@Override
		public String controlButtonText() {
			return "Resume";
		}

		@Override
		public String status() {
			return "PAUSED";
		}

		@Override
		public void onControlButtonClick(CountdownTimerEventSender event) {
			event.onResume();
		}
		
		@Override
		Boolean isRunnable() {
			return Boolean.FALSE;
		}
	},
	RESUMED, STOPPED {
		@Override
		public String controlButtonText() {
			return "Restart";
		}

		@Override
		public String status() {
			return "OFF";
		}

		@Override
		public Boolean isStopButtonClickable() {
			return Boolean.FALSE;
		}

		@Override
		public void onControlButtonClick(CountdownTimerEventSender event) {
			event.onRestart();
		}

		@Override
		public void onStopButtonClick(CountdownTimerEventSender countdownTimer) {
			throw new IllegalArgumentException(ON_STOP_BUTTON_CLICK_ERROR_MESSAGE);
		}
		
		@Override
		Boolean isRunnable() {
			return Boolean.FALSE;
		}
	},
	RESTARTED;

	private static final String ON_STOP_BUTTON_CLICK_ERROR_MESSAGE = "STOP BUTTON CANNOT BE CLICKED";

	public String status() {
		return "ON";
	}

	public String controlButtonText() {
		return "Pause";
	}

	public Boolean isStopButtonClickable() {
		return Boolean.TRUE;
	}
	
	Boolean isRunnable() {
		return Boolean.TRUE;
	}

	public void onControlButtonClick(CountdownTimerEventSender eventNotifier) {
		eventNotifier.onPause();
	}

	public void onStopButtonClick(CountdownTimerEventSender eventNotifier) {
		eventNotifier.onStop();
	}
}
