package io.openems.edge.ess.generic.common.offgrid.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class StartInverterHandler extends StateHandler<OffGridState, OffGridContext> {

	private final Logger log = LoggerFactory.getLogger(StartInverterHandler.class);

	@Override
	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {

		log.info("Inside  StartInverter handler");

		// Start the inverter
		context.batteryInverter.setInverterOn();

		boolean isInvOn = context.batteryInverter.stateOnOff();
		log.info("Is inverter on ? :" + isInvOn);

		if (!isInvOn) {
			return OffGridState.START;
		}

		// Started the inverter, Check the grid detector
		// If Grid is off
		if (context.gridDetector) {
			return OffGridState.TOTAL_OFFGRID;
		} else {
			return OffGridState.TOTAL_ONGRID;
		}
	}
}
