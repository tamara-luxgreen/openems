package io.openems.edge.ess.generic.common.offgrid.statemachine;

import io.openems.edge.common.startstop.StartStop;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.common.sum.GridMode;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class StoppedHandler extends StateHandler<OffGridState, OffGridContext> {

	@Override
	public OffGridState runAndGetNextState(OffGridContext context) {
		GenericManagedEss ess = context.getParent();

		if (ess.hasFaults()) {
			return OffGridState.UNDEFINED;
		}

		if (!context.battery.isStopped()) {
			return OffGridState.UNDEFINED;
		}

		if (!context.batteryInverter.isStopped()) {
			return OffGridState.UNDEFINED;
		}

		// Grid is On?
		if (!context.offGridSwitch.getGridStatus()) {
			context.getParent()._setGridMode(GridMode.UNDEFINED);
			ess._setStartStop(StartStop.START);
			return OffGridState.UNDEFINED;
		}
		// Mark as stopped
		ess._setStartStop(StartStop.STOP);

		return OffGridState.STOPPED;
	}

}
