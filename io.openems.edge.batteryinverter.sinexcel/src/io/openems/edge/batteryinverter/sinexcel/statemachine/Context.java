package io.openems.edge.batteryinverter.sinexcel.statemachine;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.batteryinverter.api.OffGridBatteryInverter;
import io.openems.edge.batteryinverter.sinexcel.Config;
import io.openems.edge.batteryinverter.sinexcel.Sinexcel;
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