package io.openems.edge.ess.generic.common.offgrid.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.channel.value.Value;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.common.sum.GridMode;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class TotalOffGridHandler extends StateHandler<OffGridState, OffGridContext> {

	private final Logger log = LoggerFactory.getLogger(TotalOffGridHandler.class);

	@Override
	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {

		log.info("Inside total offgrid handler");
		if (!context.offGridSwitch.getGridStatus()) {
			return OffGridState.GROUNDSET;
		}

		context.batteryInverter.setclearFailureCommand();	
		
		context.batteryInverter._setOffGridFrequency(52);
		Value<Integer> freq = context.batteryInverter.getOffGridFrequency();
		
		log.info("frequency is : " + freq.get() + " hz");

		context.batteryInverter.setOffgridCommand();
		// 3. Set the grid mode to Offgrid , // do this before , make it undefined
		context.getParent()._setGridMode(GridMode.OFF_GRID);		

		return OffGridState.TOTAL_OFFGRID;

	}
}
