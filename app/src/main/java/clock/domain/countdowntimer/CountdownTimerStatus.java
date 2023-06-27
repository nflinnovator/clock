package clock.domain.countdowntimer;

public enum CountdownTimerStatus {
	INITIALIZED {
		@Override
		String status() {
			return "OFF";
		}

		@Override
		String controlButtonText() {
			return "Start";
		}

		@Override
		boolean isStopButtonClickable() {
			return false;
		}

		@Override
		public void onControlButtonClick(CountdownTimerEventSender eventNotifier) {
			eventNotifier.onStart();
		}

		@Override
		public void onStopButtonClick(CountdownTimerEventSender eventNotifier) {
			throw new IllegalArgumentException(ON_STOP_BUTTON_CLICK_ERROR_MESSAGE);
		}
	},
	STARTED {
		@Override
		boolean canRun() {
			return true;
		}
	},
	RUNNING {
		@Override
		boolean canRun() {
			return true;
		}
	},
	PAUSED {
		@Override
		String status() {
			return "PAUSED";
		}

		@Override
		String controlButtonText() {
			return "Resume";
		}

		@Override
		public void onControlButtonClick(CountdownTimerEventSender eventNotifier) {
			eventNotifier.onStart();
		}

		@Override
		boolean isPaused() {
			return true;
		}
	},
	STOPPED {
		@Override
		String status() {
			return "OFF";
		}

		@Override
		String controlButtonText() {
			return "Restart";
		}

		@Override
		boolean isStopButtonClickable() {
			return false;
		}

		@Override
		public void onControlButtonClick(CountdownTimerEventSender eventNotifier) {
			eventNotifier.onStart();
		}

		@Override
		public void onStopButtonClick(CountdownTimerEventSender eventNotifier) {
			throw new IllegalArgumentException(ON_STOP_BUTTON_CLICK_ERROR_MESSAGE);
		}
	};

	String status() {
		return "ON";
	}

	String controlButtonText() {
		return "Pause";
	}
	
	String stopButtonText() {
		return "Stop";
	}

	boolean isStopButtonClickable() {
		return true;
	}

	public void onControlButtonClick(CountdownTimerEventSender eventNotifier) {
		eventNotifier.onPause();
	}

	private static final String ON_STOP_BUTTON_CLICK_ERROR_MESSAGE = "STOP BUTTON CANNOT BE CLICKED";

	public void onStopButtonClick(CountdownTimerEventSender eventNotifier) {
		eventNotifier.onStop();
	}

	boolean canRun() {
		return false;
	}

	boolean isPaused() {
		return false;
	}
}
