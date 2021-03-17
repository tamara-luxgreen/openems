package io.openems.edge.ess.generic.common.offgrid.statemachine;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class GroundSetHandler extends StateHandler<OffGridState, OffGridContext> {

	@Override
	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {

		// isOngrid ?
		if (!context.gridDetector) {
			// grounding set to goto ongrid
			context.offGridSwitch.setGroundingContactor(false);
			context.offGridSwitch.setMainContactor(false);
			return OffGridState.TOTAL_ONGRID;
		}

		// isOffgrid ?
		if (context.gridDetector) {
			// grounding set to goto ongrid
			context.offGridSwitch.setGroundingContactor(true);
			return OffGridState.TOTAL_OFFGRID;
		}
		return OffGridState.GROUNDSET;
	}
}
