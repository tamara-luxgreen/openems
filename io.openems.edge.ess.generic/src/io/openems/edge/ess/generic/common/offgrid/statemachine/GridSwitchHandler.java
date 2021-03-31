package io.openems.edge.ess.generic.common.offgrid.statemachine;

import java.time.Instant;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class GridSwitchHandler extends StateHandler<OffGridState, OffGridContext> {

	private Instant lastAttempt = Instant.MIN;
//	private final Logger log = LoggerFactory.getLogger(GridSwitchHandler.class);

	@Override
	protected void onEntry(OffGridContext context) throws OpenemsNamedException {

		this.lastAttempt = Instant.now();
		System.out.println(this.lastAttempt);
	}

	@Override
	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
//		Instant now = Instant.now();

		if (context.batteryInverter.isStarted()) {
			// TODO should we check here the other parameters defined in Battery Nature.
			return OffGridState.STOP_BATTERY_INVERTER_BEFORE_SWITCH;
		}

		// isOngrid ?
		if (!context.offGridSwitch.getGridStatus()) {
			// grounding set to goto on-grid
			context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(false);
			context.offGridSwitch.handleWritingDigitalOutputForMainContactor(false);
			return OffGridState.START_BATTERY_IN_ON_GRID;
		}

		// isOffgrid ?
		if (context.offGridSwitch.getGridStatus()) {
			// grounding set to goto on-grid
			if (context.battery.getSoc().getOrError() > 5 || context.battery.getMinCellVoltage().getOrError() > 3) {
				context.offGridSwitch.handleWritingDigitalOutputForMainContactor(true);
				context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(true);
				return OffGridState.START_BATTERY_IN_OFF_GRID;
			} else {
				context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(false);
				context.offGridSwitch.handleWritingDigitalOutputForMainContactor(true);
				return OffGridState.STOP_BATTERY_INVERTER;

			}
		}

//		log.info("last attempt - now is : " + Duration.between(this.lastAttempt, now).getSeconds());
//		long waitingSeconds = 5;

//		// isOngrid ?
//		if (!context.offGridSwitch.getGridStatus()) {
////			log.info("Going from off-grid to on-grid");
//			waitingSeconds = 65;
//		}
//
//		// isOffgrid ?
//		if (context.offGridSwitch.getGridStatus()) {
////			log.info("Going from on-grid to off-grid");
//			waitingSeconds = 3;
//		}

//		boolean isWaitingTimePassed = Duration.between(this.lastAttempt, now).getSeconds() > waitingSeconds;

//		if (isWaitingTimePassed) {
//			log.info("Is inverter on ? :" + context.batteryInverter.getInverterState().getOrError());
//
//			// Grid : true = Off-Grid, false = On-Grid
//			if (context.offGridSwitch.getGridStatus()) {
//				// Battery Protetion in Off-Grid, Soc should not be less than configured Soc, or
//				// Cell Min Volt
//				if (context.battery.getSoc().getOrError() > 5 || context.battery.getMinCellVoltage().getOrError() > 3) {
//					// Main Relay : true = Relay Opened, false = Relay Closed, !!! Set false on
//					// Main, will close the relay !!!
//					if (context.offGridSwitch.getMainContactor()) {
//						// Ground Relay : true = Relay Opened, false = Relay Closed, !!! Set false on
//						// Ground Relay, will open the relay !!!
//						if (context.offGridSwitch.getGroundingContactor()) {
//							context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(true);
//							return OffGridState.START_BATTERY_IN_OFF_GRID;
//						} else {
//							return OffGridState.START_BATTERY_IN_OFF_GRID;
//						}
//						// Still In Off-Grid, But Main is Close
//					} else {
//						// If Grounding is close
//						if (context.offGridSwitch.getGroundingContactor()) {
//							context.offGridSwitch.handleWritingDigitalOutputForMainContactor(true);
//							context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(true);
//							return OffGridState.START_BATTERY_IN_OFF_GRID;
//						} else {
//							// If Grounding is open
//							context.offGridSwitch.handleWritingDigitalOutputForMainContactor(true);
//							context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(true);
//							return OffGridState.START_BATTERY_IN_OFF_GRID;
//						}
//					}
//				} else {
//					// Protect the battery
//					context.offGridSwitch.handleWritingDigitalOutputForMainContactor(true);
//					context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(true);
//					return OffGridState.STOP_BATTERY_INVERTER;
//				}
//				// In On-Grid
//			} else {
//				// Main Relay Open !
//				if (context.offGridSwitch.getMainContactor()) {
//					// Ground Relay Open !
//					if (context.offGridSwitch.getGroundingContactor()) {
//						context.offGridSwitch.handleWritingDigitalOutputForMainContactor(false);
//						return OffGridState.START_BATTERY_IN_ON_GRID;
//					} else {
//						// Ground Relay Close
//						context.offGridSwitch.handleWritingDigitalOutputForMainContactor(false);
//						context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(false);
//						return OffGridState.START_BATTERY_IN_ON_GRID;
//					}
//					// Still In On-Grid, But Main is Close !
//				} else {
//					// If Grounding is close
//					if (context.offGridSwitch.getGroundingContactor()) {
//						context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(false);
//						return OffGridState.START_BATTERY_IN_ON_GRID;
//					} else {
//						// If Grounding is open
//						context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(false);
//						context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(false);
//						return OffGridState.START_BATTERY_IN_ON_GRID;
//					}
//				}
//			}
//		} else {
//			log.info("Waiting seconds " + Duration.between(this.lastAttempt, now).getSeconds() + " seconds");
//		}
		return OffGridState.GRID_SWITCH;
	}
}
