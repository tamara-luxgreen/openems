package io.openems.edge.ess.generic.common.offgrid.statemachine;

import java.time.Duration;
import java.time.Instant;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class StartBatteryInOnGridHandler extends StateHandler<OffGridState, OffGridContext> {

	private Instant lastAttempt = Instant.MIN;
	private int attemptCounter = 0;

	@Override
	protected void onEntry(OffGridContext context) throws OpenemsNamedException {
		this.lastAttempt = Instant.MIN;
		this.attemptCounter = 0;
		GenericManagedEss ess = context.getParent();
		ess._setMaxBatteryStartAttemptsFault(false);
	}

	@Override
	public OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
		GenericManagedEss ess = context.getParent();

		if (context.battery.isStarted()) {
			return OffGridState.START_BATTERY_INVERTER_IN_ON_GRID;
		}

		boolean isMaxStartTimePassed = Duration.between(this.lastAttempt, Instant.now())
				.getSeconds() > GenericManagedEss.RETRY_COMMAND_SECONDS;
		if (isMaxStartTimePassed) {
			// First try - or waited long enough for next try

			if (this.attemptCounter > GenericManagedEss.RETRY_COMMAND_MAX_ATTEMPTS) {
				// Too many tries
				ess._setMaxBatteryStartAttemptsFault(true);
				return OffGridState.UNDEFINED;

			} else {
				// Trying to start Battery
				context.battery.start();

				this.lastAttempt = Instant.now();
				this.attemptCounter++;
				return OffGridState.START_BATTERY_IN_ON_GRID;

			}

		} else {
			// Still waiting...
			return OffGridState.START_BATTERY_IN_ON_GRID;
		}
	}

}
