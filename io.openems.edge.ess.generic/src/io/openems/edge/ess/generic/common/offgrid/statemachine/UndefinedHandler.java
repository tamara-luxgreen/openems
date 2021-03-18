package io.openems.edge.ess.generic.common.offgrid.statemachine;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class UndefinedHandler extends StateHandler<OffGridState, OffGridContext> {

	@Override
	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {

		OffGridState decisionVariable = context.getStateFromInputs();
		switch (decisionVariable) {
		case ERROR:
			return OffGridState.ERROR;

		case GROUNDSET:
			return OffGridState.GROUNDSET;

		case TOTAL_OFFGRID:
			return OffGridState.TOTAL_OFFGRID;

		case TOTAL_ONGRID:
			return OffGridState.TOTAL_ONGRID;

		case UNDEFINED:
			return OffGridState.UNDEFINED;

		}
		// This should never happen too
		return OffGridState.UNDEFINED;
	}
}
