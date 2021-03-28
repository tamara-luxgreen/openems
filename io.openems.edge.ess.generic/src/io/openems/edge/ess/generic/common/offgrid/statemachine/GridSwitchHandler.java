package io.openems.edge.ess.generic.common.offgrid.statemachine;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.statemachine.StateHandler;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;

public class GridSwitchHandler extends StateHandler<OffGridState, OffGridContext> {

	private Instant lastAttempt = Instant.MIN;
	private final Logger log = LoggerFactory.getLogger(GridSwitchHandler.class);

	@Override
	protected void onEntry(OffGridContext context) throws OpenemsNamedException {

		this.lastAttempt = Instant.now();
		System.out.println(this.lastAttempt);
	}

	@Override
	protected OffGridState runAndGetNextState(OffGridContext context) throws OpenemsNamedException {
		Instant now = Instant.now();

		log.info("last attempt - now is : " + Duration.between(this.lastAttempt, now).getSeconds());
		long waitingSeconds = 5;

		// isOngrid ?
		if (!context.offGridSwitch.getGridStatus()) {
//			log.info("Going from off-grid to on-grid");
			waitingSeconds = 65;
		}

		// isOffgrid ?
		if (context.offGridSwitch.getGridStatus()) {

//			log.info("Going from on-grid to off-grid");
			waitingSeconds = 3;
		}

		boolean isWaitingTimePassed = Duration.between(this.lastAttempt, now).getSeconds() > waitingSeconds;

		if (isWaitingTimePassed) {
			// stop any matter
			context.batteryInverter.setInverterOff();
			context.batteryInverter.setInverterOff();
			log.info("Is inverter on ? :" + context.batteryInverter.getInverterState().getOrError());

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
				context.offGridSwitch.handleWritingDigitalOutputForGroundingContactor(true);
				return OffGridState.START_BATTERY_IN_OFF_GRID;
			}
		}else {
			log.info("Waiting seconds " + Duration.between(this.lastAttempt, now).getSeconds() + " seconds");
		}
		return OffGridState.GRID_SWITCH;
	}
}
