package io.openems.edge.ess.generic.common.offgrid.statemachine;

import java.time.Duration;
import java.time.Instant;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.startstop.StartStop;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.common.sum.GridMode;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class StartedInOffGridHandler extends StateHandler<OffGridState, OffGridContext> {

	private Instant lastAttempt = Instant.MIN;

	@Override
	protected void onEntry(OffGridContext context) throws OpenemsNamedException {

		this.lastAttempt = Instant.now();
		System.out.println(this.lastAttempt);
	}

	@Override
	public OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
		GenericManagedEss ess = context.getParent();

		if (ess.hasFaults()) {
			return OffGridState.UNDEFINED;
		}

		if (!context.battery.isStarted()) {
			return OffGridState.UNDEFINED;
		}

		if (!context.batteryInverter.isStarted()) {
			return OffGridState.UNDEFINED;
		}

		// Grid is On?
		if (!context.offGridSwitch.getGridStatus()) {
			Instant now = Instant.now();
			// Just hard coded 65 sec waiting
			long waitingSeconds = 65;
			boolean isWaitingTimePassed = Duration.between(this.lastAttempt, now).getSeconds() > waitingSeconds;

			if (isWaitingTimePassed) {
				context.batteryInverter.setOffgridCommand(false);
				return OffGridState.STOP_BATTERY_INVERTER_BEFORE_SWITCH;
			} else {
				context.getParent()._setGridMode(GridMode.UNDEFINED);
				return OffGridState.STARTED_IN_OFF_GRID;
			}
		}
		if (context.getParent().getAllowedDischargePower().orElse(0) <= 0) {
			context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(false);
			context.offGridSwitch.handleWritingDigitalOutputForMainContactor(true);
			return OffGridState.STOP_BATTERY_INVERTER;
		}
		ess._setStartStop(StartStop.START);
		context.batteryInverter.setOffgridCommand(true);
		context.batteryInverter.setOffGridFrequency(52);
		context.getParent()._setGridMode(GridMode.OFF_GRID);
		return OffGridState.STARTED_IN_OFF_GRID;
	}
}
