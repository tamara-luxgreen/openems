package io.openems.edge.ess.generic.common.offgrid.statemachine;

import java.util.Optional;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.battery.api.Battery;
import io.openems.edge.batteryinverter.api.FalseTrue;
import io.openems.edge.batteryinverter.api.OffGridBatteryInverter;
import io.openems.edge.common.channel.BooleanReadChannel;
import io.openems.edge.common.channel.EnumWriteChannel;
import io.openems.edge.common.channel.IntegerWriteChannel;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.ess.generic.common.offgrid.statemachine.OffGridStateMachine.State;
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

	protected State stateTransitionHelper() throws IllegalArgumentException, OpenemsNamedException {
		mainContactor = getMainContactor();
		gridDetector = getGridDetector();
		grounding = getGrounding();

		return getStateFromInputs(mainContactor, gridDetector, grounding);

	}

	protected boolean getMainContactor() throws IllegalArgumentException, OpenemsNamedException {
		return this.offGridSwitch.getMainContactor();
	}

	protected boolean getGridDetector() throws IllegalArgumentException, OpenemsNamedException {
		return this.offGridSwitch.getGridStatus();
	}

	protected boolean getGrounding() throws IllegalArgumentException, OpenemsNamedException {
		return this.offGridSwitch.getGroundingContactor();
	}

	protected static State getStateFromInputs(boolean mainContactor, boolean gridDetector, boolean grounding) {

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
					return State.ERROR;
				} else {
					// 1 1 0
					// total offgrid
					// System.out.println(" one " + State.TOTAL_OFFGRID);
					return State.TOTAL_OFFGRID;
				}
			} else {
				// main is false, main contactor is cloased
				if (grounding) {
					// 0 1 1
					// We are going to off grid
					// System.out.println(" one " + State.GROUNDSET);
					return State.GROUNDSET;
				} else {
					// 0 1 0
					// System.out.println(" one " + State.ERROR);
					return State.GROUNDSET;
				}
			}
		} else {
			// on grid
			if (mainContactor) {
				if (grounding) {
					// 1 0 1
					// System.out.println(" one " + State.GROUNDSET);
					return State.GROUNDSET;
				} else {
					// 0 1 0
					// System.out.println(" one " + State.ERROR);
					return State.GROUNDSET;
				}

			} else {
				if (grounding) {
					// 0 0 1
					// System.out.println(" one " + State.TOTAL_ONGRID);
					return State.TOTAL_ONGRID;
				} else {
					// 0 0 0
					// System.out.println(" one " + State.GROUNDSET);
					return State.GROUNDSET;

				}

			}
		}
	}

	/**
	 * Starts the inverter.
	 * 
	 * @throws OpenemsNamedException on error
	 */
	protected void setInverterOn() throws OpenemsNamedException {
		EnumWriteChannel setdataModOnCmd = this.getParent().channel(  OffGridBatteryInverter.ChannelId.MOD_ON_CMD);
		setdataModOnCmd.setNextWriteValue(FalseTrue.TRUE); // true = START
		
		
		//TODO change the param later
		softStart(true);
		
	}

	/**
	 * Stops the inverter.
	 * 
	 * @throws OpenemsNamedException on error
	 */
	protected void setInverterOff() throws OpenemsNamedException {
		EnumWriteChannel setdataModOffCmd = this.getParent().channel(OffGridBatteryInverter.ChannelId.MOD_OFF_CMD);
		setdataModOffCmd.setNextWriteValue(FalseTrue.TRUE); // true = STOP
	}

	/**
	 * Executes a Soft-Start. Sets the internal DC relay. Once this register is set
	 * to 1, the PCS will start the soft-start procedure, otherwise, the PCS will do
	 * nothing on the DC input Every time the PCS is powered off, this register will
	 * be cleared to 0. In some particular cases, the BMS wants to re-softstart, the
	 * EMS should actively clear this register to 0, after BMS soft-started, set it
	 * to 1 again.
	 *
	 * @throws OpenemsNamedException on error
	 */
	protected void softStart(boolean switchOn) throws OpenemsNamedException {
		IntegerWriteChannel setDcRelay = this.getParent().channel(OffGridBatteryInverter.ChannelId.SET_INTERN_DC_RELAY);
		setDcRelay.setNextWriteValue(switchOn ? 1 : 0);
	}
	
	public boolean stateOnOff() {
		BooleanReadChannel v = this.getParent().channel(OffGridBatteryInverter.ChannelId.INVERTER_STATE);
		Optional<Boolean> stateOff = v.getNextValue().asOptional();
		return stateOff.isPresent() && stateOff.get();
	}
	
	
	/**
	 * set the module to on grid mode. Reading back value makes no sense
	 * 
	 * @throws OpenemsNamedException
	 */
	public void setOngridCommand() throws OpenemsNamedException {
		EnumWriteChannel setdataGridOffCmd = this.getParent().channel(OffGridBatteryInverter.ChannelId.ON_GRID_CMD);
		// IntegerWriteChannel setdataGridOffCmd =
		// this.channel(EssSinexcel.ChannelId.ON_GRID_CMD);
		setdataGridOffCmd.setNextWriteValue(FalseTrue.TRUE); // 1: true, other: illegal
	}

	/**
	 * set the module to off grid mode. Reading back value makes no sense
	 * 
	 * @throws OpenemsNamedException
	 */
	public void setOffgridCommand() throws OpenemsNamedException {
		EnumWriteChannel setdataGridOffCmd = this.getParent().channel(OffGridBatteryInverter.ChannelId.OFF_GRID_CMD);
		setdataGridOffCmd.setNextWriteValue(FalseTrue.TRUE); // 1: true, other: illegal
	}

	public void setclearFailureCommand() throws OpenemsNamedException {
		EnumWriteChannel setClearFailureCmd = this.getParent().channel(OffGridBatteryInverter.ChannelId.CLEAR_FAILURE_CMD);
		setClearFailureCmd.setNextWriteValue(FalseTrue.TRUE); // 1: true, other: illegal
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
