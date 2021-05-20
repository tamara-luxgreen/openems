package io.openems.edge.ess.generic.common.offgrid.statemachine;

import java.time.Duration;
import java.time.Instant;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class StopBatteryHandler extends StateHandler<OffGridState, OffGridContext> {

	private Instant lastAttempt = Instant.MIN;
	private int attemptCounter = 0;

	@Override
	protected void onEntry(OffGridContext context) throws OpenemsNamedException {
		this.lastAttempt = Instant.MIN;
		this.attemptCounter = 0;
		GenericManagedEss ess = context.getParent();
		ess._setMaxBatteryStopAttemptsFault(false);
	}

	@Override
	public OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
		GenericManagedEss ess = context.getParent();

		if (context.battery.isStopped()) {
			return OffGridState.STOPPED;
		}

		boolean isMaxStartTimePassed = Duration.between(this.lastAttempt, Instant.now())
				.getSeconds() > GenericManagedEss.RETRY_COMMAND_SECONDS;
		if (isMaxStartTimePassed) {
			// First try - or waited long enough for next try

			if (this.attemptCounter > GenericManagedEss.RETRY_COMMAND_MAX_ATTEMPTS) {
				// Too many tries
				ess._setMaxBatteryStopAttemptsFault(true);
				return OffGridState.UNDEFINED;

			} else {
				// Trying to stop Battery
				context.battery.stop();

				this.lastAttempt = Instant.now();
				this.attemptCounter++;
				return OffGridState.STOP_BATTERY;
			}
		} else {
			// Still waiting...
			return OffGridState.STOP_BATTERY;
		}
	}
}
