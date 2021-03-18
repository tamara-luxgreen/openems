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

		this.log.info("We are in total ongrid state");
		
		if (context.offGridSwitch.getGridStatus()) {
			return OffGridState.GROUNDSET;
		}
		
		context.batteryInverter.setOngridCommand();
		context.getParent()._setGridMode(GridMode.ON_GRID);
		return OffGridState.TOTAL_ONGRID;
	}
}
