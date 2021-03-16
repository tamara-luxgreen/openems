//package io.openems.edge.ess.generic.common.offgrid.statemachine;
//
//import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
//import io.openems.edge.common.statemachine.StateHandler;
//import io.openems.edge.ess.generic.common.offgrid.statemachine.StateMachine.State;
//public class GroundSetHandler extends StateHandler<State, OffGridContext> {
//
//	@Override
//	protected State runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
//
//		// isOngrid ?
//		if (!context.getGridDetector().get()) {
//			// grounding set to goto ongrid
//			context.component.handleWritingDigitalOutputForGrounding(false);
//			context.component.handleWritingDigitalOutputForMain(false);
//			return State.TOTAL_ONGRID;
//		}
//
//		// isOffgrid ?
//		if (context.getGridDetector().get()) {
//			// grounding set to goto ongrid
//			context.component.handleWritingDigitalOutputForGrounding(true);
//			return State.TOTAL_OFFGRID;
//		}
//		return State.GROUNDSET;
//	}
//}
