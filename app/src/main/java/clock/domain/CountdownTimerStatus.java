package clock.domain;

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
	},
	RESTARTED;

	public String status() {
		return "ON";
	}

	public String controlButtonText() {
		return "Pause";
	}

	public Boolean isStopButtonClickable() {
		return Boolean.TRUE;
	}
}
