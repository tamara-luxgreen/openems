package io.openems.edge.ess.generic.common.offgrid.statemachine;

import java.time.Instant;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class GridSwitchHandler extends StateHandler<OffGridState, OffGridContext> {

	private Instant lastAttempt = Instant.MIN;
//	private final Logger log = LoggerFactory.getLogger(GridSwitchHandler.class);

	@Override
	protected void onEntry(OffGridContext context) throws OpenemsNamedException {

		this.lastAttempt = Instant.now();
		System.out.println(this.lastAttempt);
	}

	@Override
	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {

		if (context.batteryInverter.isStarted()) {
			// TODO should we check here the other parameters defined in Battery Nature.
			return OffGridState.STOP_BATTERY_INVERTER_BEFORE_SWITCH;
		}

		// isOngrid ?
		if (!context.offGridSwitch.getGridStatus()) {
			context.offGridSwitch.handleWritingDigitalOutputForMainContactor(false);
			context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(false);
			if (!context.offGridSwitch.getMainContactor() && !context.offGridSwitch.getGroundingContactor()) {
				return OffGridState.START_BATTERY_IN_ON_GRID;
			}
			return OffGridState.GRID_SWITCH;
		}

		// isOffgrid ?
		if (context.offGridSwitch.getGridStatus()) {
			if (context.battery.getSoc().get() < context.allowedMinSocInOffGrid
					|| context.battery.getMinCellVoltage().get() < context.allowedMinCellVoltageInOffGrid) {
				context.offGridSwitch.handleWritingDigitalOutputForMainContactor(true);
				context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(false);
				return OffGridState.STOP_BATTERY_INVERTER;
			} else {
				context.offGridSwitch.handleWritingDigitalOutputForMainContactor(false);
				context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(true);
				if (context.offGridSwitch.getMainContactor() && context.offGridSwitch.getGroundingContactor()) {
					return OffGridState.START_BATTERY_IN_OFF_GRID;
				}
				return OffGridState.GRID_SWITCH;
			}
		}
		return OffGridState.GRID_SWITCH;
	}
}
