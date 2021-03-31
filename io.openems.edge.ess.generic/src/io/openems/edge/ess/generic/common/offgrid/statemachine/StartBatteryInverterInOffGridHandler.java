package io.openems.edge.ess.generic.common.offgrid.statemachine;

import java.time.Duration;
import java.time.Instant;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.common.sum.GridMode;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class StartBatteryInverterInOffGridHandler extends StateHandler<OffGridState, OffGridContext> {

	private Instant lastAttempt = Instant.MIN;
	private int attemptCounter = 0;

	@Override
	protected void onEntry(OffGridContext context) throws OpenemsNamedException {
		this.lastAttempt = Instant.MIN;
		this.attemptCounter = 0;
		GenericManagedEss ess = context.getParent();
		ess._setMaxBatteryInverterStartAttemptsFault(false);
	}

	@Override
	public OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
		GenericManagedEss ess = context.getParent();

		// Inverter is on
		if (context.batteryInverter.isStarted()) {
			return OffGridState.STARTED_IN_OFF_GRID;
		}

		boolean isMaxStartTimePassed = Duration.between(this.lastAttempt, Instant.now())
				.getSeconds() > GenericManagedEss.RETRY_COMMAND_SECONDS;
		if (isMaxStartTimePassed) {
			// First try - or waited long enough for next try

			if (this.attemptCounter > GenericManagedEss.RETRY_COMMAND_MAX_ATTEMPTS) {
				// Too many tries
				ess._setMaxBatteryInverterStartAttemptsFault(true);
				return OffGridState.UNDEFINED;

			} else {
				// Trying to start Battery
				context.batteryInverter.start();
				context.batteryInverter.setOffgridCommand(true);
				context.batteryInverter.setOffGridFrequency(52);
				context.getParent()._setGridMode(GridMode.OFF_GRID);
				this.lastAttempt = Instant.now();
				this.attemptCounter++;
				return OffGridState.START_BATTERY_INVERTER_IN_OFF_GRID;
			}
		} else {
			// Still waiting...
			return OffGridState.START_BATTERY_INVERTER_IN_OFF_GRID;
		}
	}
}
