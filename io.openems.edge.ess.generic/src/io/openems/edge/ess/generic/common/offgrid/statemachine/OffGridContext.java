package io.openems.edge.ess.generic.common.offgrid.statemachine;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
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

	protected boolean mainContactor;
	protected boolean gridDetector;
	protected boolean grounding;

	public OffGridContext(GenericManagedEss parent, Battery battery, OffGridBatteryInverter batteryInverter,
			OffGridSwitch offGridSwitch) {
		super(parent, battery, batteryInverter);
		this.batteryInverter = batteryInverter;
		this.offGridSwitch = offGridSwitch;
		this.battery = battery;
	}

	protected OffGridState stateTransitionHelper() throws IllegalArgumentException, OpenemsNamedException {
		mainContactor = this.offGridSwitch.getMainContactor();
		grounding = this.offGridSwitch.getGroundingContactor();
		gridDetector = this.offGridSwitch.getGridStatus();

		return getStateFromInputs(mainContactor, gridDetector, grounding);

	}

	protected static OffGridState getStateFromInputs(boolean mainContactor, boolean gridDetector, boolean grounding) {

//		System.out.println("grid detector : " + gridDetector);
//		System.out.println("main : " + mainContactor);
//		System.out.println("ground : " + grounding);

		if (gridDetector) {
			// off grid
			if (mainContactor) {
				// main is true, maincontactor is open
				if (grounding) {
					// ground is true , grounding is closed
					// 1 1 1
					// error
					// System.out.println(" one " + State.ERROR);
					return OffGridState.ERROR;
				} else {
					// 1 1 0
					// total offgrid
					// System.out.println(" one " + State.TOTAL_OFFGRID);
					return OffGridState.TOTAL_OFFGRID;
				}
			} else {
				// main is false, main contactor is cloased
				if (grounding) {
					// 0 1 1
					// We are going to off grid
					// System.out.println(" one " + State.GROUNDSET);
					return OffGridState.GROUNDSET;
				} else {
					// 0 1 0
					// System.out.println(" one " + State.ERROR);
					return OffGridState.GROUNDSET;
				}
			}
		} else {
			// on grid
			if (mainContactor) {
				if (grounding) {
					// 1 0 1
					// System.out.println(" one " + State.GROUNDSET);
					return OffGridState.GROUNDSET;
				} else {
					// 0 1 0
					// System.out.println(" one " + State.ERROR);
					return OffGridState.GROUNDSET;
				}

			} else {
				if (grounding) {
					// 0 0 1
					// System.out.println(" one " + State.TOTAL_ONGRID);
					return OffGridState.TOTAL_ONGRID;
				} else {
					// 0 0 0
					// System.out.println(" one " + State.GROUNDSET);
					return OffGridState.GROUNDSET;

				}

			}
		}
	}

//	/**
//	 * At first the PCS needs a stop command, then is required to remove the AC
//	 * connection, after that the Grid OFF command.
//	 * 
//	 * @throws OpenemsNamedException on error
//	 */
//	public void islandOn() throws OpenemsNamedException {
//		IntegerWriteChannel setAntiIslanding = this.getParent().channel(Sinexcel.ChannelId.SET_ANTI_ISLANDING);
//		setAntiIslanding.setNextWriteValue(0); // Disabled
//		IntegerWriteChannel setDataGridOffCmd = this.getParent().channel(OffGridBatteryInverter.ChannelId.OFF_GRID_CMD);
//		setDataGridOffCmd.setNextWriteValue(1); // Stop
//	}

}
