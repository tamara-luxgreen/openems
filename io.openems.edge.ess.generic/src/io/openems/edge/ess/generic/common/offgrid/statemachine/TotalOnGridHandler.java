package io.openems.edge.ess.generic.common.offgrid.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.common.sum.GridMode;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class TotalOnGridHandler extends StateHandler<OffGridState, OffGridContext> {

	private final Logger log = LoggerFactory.getLogger(TotalOnGridHandler.class);

	protected boolean StartOnce = false;

	@Override
	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
		log.info("inside total ongrid state");
		if (context.gridDetector) {
			return OffGridState.STOP;
		}

		context.batteryInverter.setInverterOn();
		// context.softStart(true);
		boolean isInvOn = context.batteryInverter.stateOnOff();
		log.info("Is inverter on ? :" + isInvOn);

		if (isInvOn) {
			// Inverter is on

			// 1. Give command to make it on-grid
			context.batteryInverter.setOngridCommand();

			// do this before , make it undefined

			// 2. Set the grid mode to Ongrid
			context.getParent()._setGridMode(GridMode.ON_GRID);

		} else {
			log.info("Inverter is not on , going back to swithc on inverter");
			return OffGridState.TOTAL_ONGRID;
		}

		// Run in the ongrid state

		// 1. Give command to make it on-grid
		context.batteryInverter.setOngridCommand();

		// 2. Set the grid mode to Ongrid
		context.getParent()._setGridMode(GridMode.ON_GRID);

		// TYPO we need this later

//		// 3. Do the softstart of the sinexcel
//		CurrentState currentState = context.component.getSinexcelState();
//
//		switch (currentState) {
//		case UNDEFINED:
//		case SLEEPING:
//		case MPPT:
//		case THROTTLED:
//		case STARTED:
//		case STANDBY:
//			context.component.softStart(true);
//			break;
//		case SHUTTINGDOWN:
//		case FAULT:
//		case OFF:
//		default:
//			context.component.softStart(false);
//		}
		return OffGridState.TOTAL_ONGRID;
	}
}
