package io.openems.edge.ess.generic.common.offgrid.statemachine;

import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class UndefinedHandler extends StateHandler<OffGridState, OffGridContext> {

	@Override
	public OffGridState runAndGetNextState(OffGridContext context) {
		GenericManagedEss ess = context.getParent();
		switch (ess.getStartStopTarget()) {
		case UNDEFINED:
			// Stuck in UNDEFINED State
			return OffGridState.UNDEFINED;

		case START:
			// force START
			if (ess.hasFaults()) {
				// TODO should we consider also Battery-Inverter and Battery Faults?
				// TODO should the Modbus-Device also be on error, when then Modbus-Bridge is on
				// error?

				// Has Faults -> error handling
				return OffGridState.ERROR;
			} else {
				// No Faults -> Check the Relay States
				return OffGridState.GRID_SWITCH;
			}

		case STOP:
			// force STOP
			return OffGridState.STOP_BATTERY_INVERTER;
		}

		assert false;
		return OffGridState.UNDEFINED; // can never happen
	}

}
