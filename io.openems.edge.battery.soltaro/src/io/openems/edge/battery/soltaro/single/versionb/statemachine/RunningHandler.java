package io.openems.edge.battery.soltaro.single.versionb.statemachine;

import io.openems.edge.battery.soltaro.single.versionb.statemachine.StateMachine.State;
import io.openems.edge.common.channel.value.Value;
import io.openems.edge.common.startstop.StartStop;
import io.openems.edge.common.statemachine.StateHandler;

public class RunningHandler extends StateHandler<State, Context> {

	@Override
	public State runAndGetNextState(Context context) {
		if (ControlAndLogic.hasError(context.component, context.config.numberOfSlaves())) {
			return State.UNDEFINED;
		}

		if (!ControlAndLogic.isSystemRunning(context.component)) {
			return State.UNDEFINED;
		}

		// Mark as started
		context.component._setStartStop(StartStop.START);
		
		setCurrentLimitations(context);
				

		return State.RUNNING;
	}

	private void setCurrentLimitations(Context context) {
		int maxAllowedChargeCurrent = context.config.maxAllowedChargeCurrent();
		int maxAllowedDischargeCurrent = context.config.maxAllowedDischargeCurrent();
		
		if (ControlAndLogic.isAlarm1PoleTempTooHot(context.component)) {
			maxAllowedChargeCurrent = context.config.maxAllowedChargeCurrentWhenPoleTempTooHigh();
			maxAllowedDischargeCurrent = context.config.maxAllowedDischargeCurrentWhenPoleTempTooHigh();
		}
		
		Value<Integer> chargeMaxCurrentFromBattery = context.component.getChargeMaxCurrent();
		if (chargeMaxCurrentFromBattery.isDefined()) {
			maxAllowedChargeCurrent = Math.min(maxAllowedChargeCurrent, chargeMaxCurrentFromBattery.get());
		}
		
		Value<Integer> dischargeMaxCurrentFromBattery = context.component.getDischargeMaxCurrent();
		if (dischargeMaxCurrentFromBattery.isDefined()) {
			maxAllowedDischargeCurrent = Math.min(maxAllowedDischargeCurrent, dischargeMaxCurrentFromBattery.get());
		}
		
		context.component._setChargeMaxCurrent(maxAllowedChargeCurrent);
		context.component._setDischargeMaxCurrent(maxAllowedDischargeCurrent);
	}

}
