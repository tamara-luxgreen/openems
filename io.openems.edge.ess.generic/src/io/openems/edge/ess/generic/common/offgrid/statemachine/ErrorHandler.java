package io.openems.edge.ess.generic.common.offgrid.statemachine;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class ErrorHandler extends StateHandler<OffGridState, OffGridContext> {

	private int attemptCounter = 0;

	@Override
	protected void onEntry(OffGridContext context) throws OpenemsNamedException {
		this.attemptCounter = 0;
	}

	@Override
	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
		context.getParent();
		if (this.attemptCounter > 5) {
			// switch off
			context.batteryInverter.setInverterOff();
			return OffGridState.ERROR;
		} else {

			OffGridState decisionVariable = context.stateTransitionHelper();
			switch (decisionVariable) {

			case GROUNDSET:
				return OffGridState.GROUNDSET;

			case TOTAL_OFFGRID:
				return OffGridState.TOTAL_OFFGRID;

			case TOTAL_ONGRID:
				return OffGridState.TOTAL_ONGRID;

			case UNDEFINED:
				return OffGridState.UNDEFINED;

			case ERROR:
			case START:
			case STOP:
				return OffGridState.ERROR;

			}
			// This should never happen too
			this.attemptCounter++;
			return OffGridState.UNDEFINED;
		}
	}
}
