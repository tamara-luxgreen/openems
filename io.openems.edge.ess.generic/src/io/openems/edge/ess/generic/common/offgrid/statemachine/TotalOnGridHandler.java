//package io.openems.edge.ess.generic.common.offgrid.statemachine;
//
//import java.time.Duration;
//import java.time.Instant;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
//import io.openems.edge.common.statemachine.StateHandler;
//import io.openems.edge.common.sum.GridMode;
//import io.openems.edge.ess.generic.common.GenericManagedEss;
//import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;
//import io.openems.edge.ess.generic.common.statemachine.Context;
//import io.openems.edge.ess.generic.common.statemachine.StateMachine.State;
//
//public class TotalOnGridHandler extends StateHandler<OffGridState, OffGridContext> {
//
//	private final Logger log = LoggerFactory.getLogger(TotalOnGridHandler.class);
//
//	protected boolean StartOnce = false;
//
//	
//	private Instant lastAttempt = Instant.MIN;
//	private int attemptCounter = 0;
//
//	@Override
//	protected void onEntry(OffGridContext context) throws OpenemsNamedException {
//		this.lastAttempt = Instant.MIN;
//		this.attemptCounter = 0;
//		GenericManagedEss ess = context.getParent();
//		ess._setMaxBatteryInverterStartAttemptsFault(false);
//	}
//
//	@Override
//	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
//
//		this.log.info("Starts the battery inverter in On Grid Mode");
//		
//		GenericManagedEss ess = context.getParent();
//
//		if (context.batteryInverter.isStarted()) {
//			return OffGridState.START_BATTERY_IN_ON_GRID;
//		}
//
//		boolean isMaxStartTimePassed = Duration.between(this.lastAttempt, Instant.now())
//				.getSeconds() > GenericManagedEss.RETRY_COMMAND_SECONDS;
//		if (isMaxStartTimePassed) {
//			// First try - or waited long enough for next try
//
//			if (this.attemptCounter > GenericManagedEss.RETRY_COMMAND_MAX_ATTEMPTS) {
//				// Too many tries
//				ess._setMaxBatteryInverterStartAttemptsFault(true);
//				return State.UNDEFINED;
//
//			} else {
//				// Trying to start Battery
//				context.batteryInverter.start();
//
//				this.lastAttempt = Instant.now();
//				this.attemptCounter++;
//				return State.START_BATTERY_INVERTER;
//
//			}
//
//		} else {
//			// Still waiting...
//			return State.START_BATTERY_INVERTER;
//		}
//		context.batteryInverter.setOngridCommand();
//		context.getParent()._setGridMode(GridMode.ON_GRID);
//		return OffGridState.TOTAL_;
//	}
//}
