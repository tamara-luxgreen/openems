package io.openems.edge.ess.generic.common.offgrid.statemachine;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.State;

public class GroundSetHandler extends StateHandler<State, OffGridContext> {

	@Override
	protected State runAndGetNextState(OffGridContext context) throws OpenemsNamedException {

		// isOngrid ?
		if (!context.getGridDetector()) {
			// grounding set to goto ongrid
			context.offGridSwitch.setGroundingContactor(false);
			context.offGridSwitch.setMainContactor(false);
			return State.TOTAL_ONGRID;
		}

		// isOffgrid ?
		if (context.getGridDetector()) {
			// grounding set to goto ongrid
			context.offGridSwitch.setGroundingContactor(true);
			return State.TOTAL_OFFGRID;
		}
		return State.GROUNDSET;
	}
}
