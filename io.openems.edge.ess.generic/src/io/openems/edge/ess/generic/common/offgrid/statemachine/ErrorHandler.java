package io.openems.edge.ess.generic.common.offgrid.statemachine;

import java.time.Duration;
import java.time.Instant;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.startstop.StartStop;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class ErrorHandler extends StateHandler<OffGridState, OffGridContext> {

	private Instant entryAt = Instant.MIN;

	@Override
	protected void onEntry(OffGridContext context) throws OpenemsNamedException {
		this.entryAt = Instant.now();

		// Try to stop systems
		context.battery.setStartStop(StartStop.STOP);
		context.batteryInverter.setStartStop(StartStop.STOP);
	}

	@Override
	protected void onExit(OffGridContext context) throws OpenemsNamedException {
		GenericManagedEss ess = context.getParent();

		ess._setMaxBatteryStartAttemptsFault(false);
		ess._setMaxBatteryStopAttemptsFault(false);
		ess._setMaxBatteryInverterStartAttemptsFault(false);
		ess._setMaxBatteryInverterStopAttemptsFault(false);
	}

	@Override
	public OffGridState runAndGetNextState(OffGridContext context) {
		if (Duration.between(this.entryAt, Instant.now()).getSeconds() > 120) {
			// Try again
			return OffGridState.UNDEFINED;
		}

		return OffGridState.ERROR;
	}

}
