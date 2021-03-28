package io.openems.edge.ess.generic.common.offgrid.statemachine;

import io.openems.edge.battery.api.Battery;
import io.openems.edge.batteryinverter.api.OffGridBatteryInverter;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.OffGridState;
import io.openems.edge.ess.generic.common.statemachine.Context;
import io.openems.edge.io.offgridswitch.api.OffGridSwitch;

public class OffGridContext extends Context {
	protected final Battery battery;
	protected final OffGridBatteryInverter batteryInverter;
	protected final OffGridSwitch offGridSwitch;

	public OffGridContext(GenericManagedEss parent, Battery battery, OffGridBatteryInverter batteryInverter,
			OffGridSwitch offGridSwitch) {
		super(parent, battery, batteryInverter);
		this.batteryInverter = batteryInverter;
		this.offGridSwitch = offGridSwitch;
		this.battery = battery;
	}

	protected OffGridState getStateFromInputs() {

		if (offGridSwitch.getGridStatus()) {
			// off grid
			if (offGridSwitch.getMainContactor()) {
				// main is true, maincontactor is open
				if (offGridSwitch.getGroundingContactor()) {
					// ground is true , grounding is closed
					// 1 1 1
					// error
					// System.out.println(" one " + State.ERROR);
					return OffGridState.ERROR;
				} else {
					// 1 1 0
					// total offgrid
					// System.out.println(" one " + State.TOTAL_OFFGRID);
					return OffGridState.START_BATTERY_IN_OFF_GRID;
				}
			} else {
				// main is false, main contactor is closed
				if (offGridSwitch.getGroundingContactor()) {
					// 0 1 1
					// We are going to off grid
					// System.out.println(" one " + State.GROUNDSET);
					return OffGridState.UNDEFINED;
				} else {
					// 0 1 0
					// System.out.println(" one " + State.ERROR);
					return OffGridState.UNDEFINED;
				}
			}
		} else {
			// on grid
			if (offGridSwitch.getMainContactor()) {
				if (offGridSwitch.getGroundingContactor()) {
					// 1 0 1
					// System.out.println(" one " + State.GROUNDSET);
					return OffGridState.UNDEFINED;
				} else {
					// 1 0 0
					// System.out.println(" one " + State.ERROR);
					return OffGridState.UNDEFINED;
				}

			} else {
				if (offGridSwitch.getGroundingContactor()) {
					// 0 0 1
					// System.out.println(" one " + State.TOTAL_ONGRID);
					return OffGridState.START_BATTERY_IN_ON_GRID;
				} else {
					// 0 0 0
					// System.out.println(" one " + State.GROUNDSET);
					return OffGridState.UNDEFINED;

				}
			}
		}
	}
}
