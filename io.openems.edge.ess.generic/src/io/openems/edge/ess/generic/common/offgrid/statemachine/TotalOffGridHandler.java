package io.openems.edge.ess.generic.common.offgrid.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.channel.value.Value;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.common.sum.GridMode;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.State;

public class TotalOffGridHandler extends StateHandler<State, OffGridContext> {

	private final Logger log = LoggerFactory.getLogger(TotalOffGridHandler.class);

	@Override
	protected State runAndGetNextState(OffGridContext context) throws OpenemsNamedException {

		log.info("Inside total offgrid handler");

		if (!context.getGridDetector()) {
			return State.STOP;
		}
		context.setclearFailureCommand();
		// Set start command to inverter
		context.setInverterOn();
		context.softStart(true);
		boolean isInvOn = context.stateOnOff();
		log.info("Is inverter on ? :" + isInvOn);

		context.batteryInverter.setOffGridFrequency(52);
		Value<Integer> freq = context.batteryInverter.getOffGridFrequency();
		log.info("frequency is : " + freq.get() + " hz");
		
		
		context.setOffgridCommand();

		// 3. Set the grid mode to Offgrid , // do this before , make it undefined
		context.getParent()._setGridMode(GridMode.OFF_GRID);
		
		if (isInvOn) {
			// Inverter is on

			// 1. Set the frequncy
			context.batteryInverter.setOffGridFrequency(52);

			if (freq.get() == 52) {

				// 2. Give command to make it off-grid
				context.setOffgridCommand();

				// 3. Set the grid mode to Offgrid , // do this before , make it undefined
				context.getParent()._setGridMode(GridMode.OFF_GRID);
			} else {
				log.info("frequency is not 52 yet, goign back again to check freq");
				return State.TOTAL_OFFGRID;
			}

		} else {
			log.info("Inverter is not on, going back to swithc on inverter");
			return State.TOTAL_OFFGRID;
		}

		// Run in the offgrid state

//		// 1. Set the frequncy
//		context.component.setFrequency();
//		
//		// 2. Give command to make it off-grid
//		context.component.setOffgridCommand();
//		
//		// 3. Set the grid mode to Offgrid , // do this before , make it undefined
//		context.component._setGridMode(GridMode.OFF_GRID);

		// 4. Do the softstart of the sinexcel
		
		log.info("parameters are set for the inverter to the softstart");
		
		
//		CurrentState currentState = context.getSinexcelState();
//		switch (currentState) {
//		case UNDEFINED:
//		case SLEEPING:
//		case MPPT:
//		case THROTTLED:
//		case STARTED:
//		case STANDBY:
//			context..softStart(true);
//			break;
//		case SHUTTINGDOWN:
//		case FAULT:
//		case OFF:
//		default:
//			context..softStart(false);
//		}

		return State.TOTAL_OFFGRID;

	}
}
