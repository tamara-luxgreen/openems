//package io.openems.edge.ess.generic.common.offgrid.statemachine;
//
//import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
//import io.openems.edge.common.statemachine.StateHandler;
//import io.openems.edge.ess.generic.common.offgrid.statemachine.StateMachine.State;
//
//public class ErrorHandler extends StateHandler<State, OffGridContext> {
//
//	private int attemptCounter = 0;
//
//	@Override
//	protected void onEntry(OffGridContext context) throws OpenemsNamedException {
//		this.attemptCounter = 0;
//	}
//
//	@Override
//	protected State runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
//		context.getParent();
//		if (this.attemptCounter > 5) {
//			// switch off
//			context.component.inverterOff();
//			return State.ERROR;
//		} else {
//
//			State decisionVariable = context.stateTransitionHelper();
//			switch (decisionVariable) {
//
//			case GROUNDSET:
//				return State.GROUNDSET;
//
//			case TOTAL_OFFGRID:
//				return State.TOTAL_OFFGRID;
//
//			case TOTAL_ONGRID:
//				return State.TOTAL_ONGRID;
//
//			case UNDEFINED:
//				return State.UNDEFINED;
//
//			case ERROR:
//			case START:
//			case STOP:
//				return State.ERROR;
//
//			}
//			// This should never happen too
//			this.attemptCounter++;
//			return State.UNDEFINED;
//		}
//	}
//}
