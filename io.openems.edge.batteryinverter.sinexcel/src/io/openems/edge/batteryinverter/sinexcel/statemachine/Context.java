package io.openems.edge.batteryinverter.sinexcel.statemachine;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.batteryinverter.api.OffGridBatteryInverter;
import io.openems.edge.batteryinverter.sinexcel.Config;
import io.openems.edge.batteryinverter.sinexcel.Sinexcel;
import io.openems.edge.common.channel.BooleanWriteChannel;
import io.openems.edge.common.channel.IntegerWriteChannel;
import io.openems.edge.common.statemachine.AbstractContext;

public class Context extends AbstractContext<Sinexcel> {

	protected final Config config;
	protected final int setActivePower;
	protected final int setReactivePower;

	public Context(Sinexcel parent, Config config, int setActivePower, int setReactivePower) {
		super(parent);
		this.config = config;
		this.setActivePower = setActivePower;
		this.setReactivePower = setReactivePower;
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
		IntegerWriteChannel setDcRelay = this.getParent().channel(Sinexcel.ChannelId.SET_INTERN_DC_RELAY);
		setDcRelay.setNextWriteValue(switchOn ? 1 : 0);
	}

	protected void setclearFailureCommand() throws OpenemsNamedException {
		BooleanWriteChannel setClearFailureCmd = this.getParent().channel(Sinexcel.ChannelId.CLEAR_FAILURE_CMD);
		setClearFailureCmd.setNextWriteValue(true); // 1: true, other: illegal
	}

	/**
	 * At first the PCS needs a stop command, then is required to remove the AC
	 * connection, after that the Grid OFF command.
	 * 
	 * @throws OpenemsNamedException on error
	 */
	public void islandOn() throws OpenemsNamedException {
		IntegerWriteChannel setAntiIslanding = this.getParent().channel(Sinexcel.ChannelId.SET_ANTI_ISLANDING);
		setAntiIslanding.setNextWriteValue(0); // Disabled
		IntegerWriteChannel setDataGridOffCmd = this.getParent().channel(OffGridBatteryInverter.ChannelId.OFF_GRID_CMD);
		setDataGridOffCmd.setNextWriteValue(1); // Stop
	}

}