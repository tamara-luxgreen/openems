package io.openems.edge.batteryinverter.api;

import org.osgi.annotation.versioning.ProviderType;

import io.openems.common.channel.AccessMode;
import io.openems.common.channel.Unit;
import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.types.OpenemsType;
import io.openems.edge.common.channel.BooleanReadChannel;
import io.openems.edge.common.channel.BooleanWriteChannel;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.channel.IntegerReadChannel;
import io.openems.edge.common.channel.IntegerWriteChannel;
import io.openems.edge.common.channel.StateChannel;
import io.openems.edge.common.channel.value.Value;
import io.openems.edge.common.startstop.StartStoppable;

@ProviderType
public interface OffGridBatteryInverter
		extends ManagedSymmetricBatteryInverter, SymmetricBatteryInverter, StartStoppable {

	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {
		/**
		 * Off-Grid-Frequency.
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridBatteryInverter}
		 * <li>Type: Integer
		 * <li>Range: 40-60
		 * </ul>
		 */
		OFF_GRID_FREQUENCY(Doc.of(OpenemsType.INTEGER) //
				.accessMode(AccessMode.READ_WRITE) //
				.unit(Unit.HERTZ)),

		/**
		 * On-Grid-Command
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridBatteryInverter}
		 * <li>Type: Boolean
		 * </ul>
		 */
		ON_GRID_CMD(Doc.of(OpenemsType.BOOLEAN) //
				.accessMode(AccessMode.READ_WRITE)), //

		/**
		 * Off-Grid-Command
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridBatteryInverter}
		 * <li>Type: Boolean
		 * </ul>
		 */
		OFF_GRID_CMD(Doc.of(OpenemsType.BOOLEAN) //
				.accessMode(AccessMode.READ_WRITE)), //

		/**
		 * Mod-On-Command
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridBatteryInverter}
		 * <li>Type: Boolean
		 * </ul>
		 */
		MOD_ON_CMD(Doc.of(OpenemsType.BOOLEAN) //
				.accessMode(AccessMode.READ_WRITE)), //

		/**
		 * Mod-Off-Command
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridBatteryInverter}
		 * <li>Type: Boolean
		 * </ul>
		 */
		MOD_OFF_CMD(Doc.of(OpenemsType.BOOLEAN) //
				.accessMode(AccessMode.READ_WRITE)), //

		/**
		 * Inverter-State
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridBatteryInverter}
		 * <li>Type: Boolean
		 * </ul>
		 */
		INVERTER_STATE(Doc.of(OpenemsType.BOOLEAN) //
				.text("System is ON ?")), //
		;

		private final Doc doc;

		private ChannelId(Doc doc) {
			this.doc = doc;
		}

		@Override
		public Doc doc() {
			return this.doc;
		}

	}

	/**
	 * Gets the Channel for {@link ChannelId#FREQUENCY}.
	 *
	 * @return the Channel
	 */
	public default IntegerReadChannel getOffGridFrequencyChannel() {
		return this.channel(ChannelId.OFF_GRID_FREQUENCY);
	}

	/**
	 * Gets the * {@link ChannelId#OFF_GRID_FREQUENCY}.
	 *
	 * @return the Channel {@link Value}
	 */
	public default Value<Integer> getOffGridFrequency() {
		return this.getOffGridFrequencyChannel().value();
	}

	/**
	 * Internal method to set the 'nextValue' on
	 * {@link ChannelId#OFF_GRID_FREQUENCY} Channel.
	 *
	 * @param value the next value
	 */
	public default void _setOffGridFrequency(Integer value) {
		this.getOffGridFrequencyChannel().setNextValue(value);
	}

	/**
	 * Internal method to set the 'nextValue' on
	 * {@link ChannelId#OFF_GRID_FREQUENCY} Channel.
	 *
	 * @param value the next value
	 */
	public default void _setOffGridFrequency(int value) {
		this.getOffGridFrequencyChannel().setNextValue(value);
	}

	/**
	 * Gets the Channel for {@link ChannelId#OFF_GRID_FREQUENCY}.
	 *
	 * @return the Channel
	 */
	public default IntegerWriteChannel getSetOffGridFrequencyChannel() {
		return this.channel(ChannelId.OFF_GRID_FREQUENCY);
	}

	/**
	 * Sets an Off Grid Frequency set point in [Hz]. See
	 * {@link ChannelId#SET_ACTIVE_POWER_EQUALS}.
	 * 
	 * @param value the next write value
	 * @throws OpenemsNamedException on error
	 */
	public default void setOffGridFrequency(Integer value) throws OpenemsNamedException {
		this.getSetOffGridFrequencyChannel().setNextWriteValue(value);
	}

	/**
	 * Starts the inverter.
	 * 
	 * @throws OpenemsNamedException on error
	 */
	public default void setInverterOn() throws OpenemsNamedException {
		BooleanWriteChannel setdataModOnCmd = this.channel(ChannelId.MOD_ON_CMD);
		setdataModOnCmd.setNextWriteValue(true); // true = START
	}

	/**
	 * Stops the inverter.
	 * 
	 * @throws OpenemsNamedException on error
	 */
	public default void setInverterOff() throws OpenemsNamedException {
		BooleanWriteChannel setdataModOffCmd = this.channel(ChannelId.MOD_OFF_CMD);
		setdataModOffCmd.setNextWriteValue(true); // true = STOP
	}

	/**
	 * Gets the Channel for {@link ChannelId#STATE_ON}.
	 * 
	 * @return the Channel
	 */
	public default BooleanReadChannel getInverterStateChannel() {
		return this.channel(ChannelId.INVERTER_STATE);
	}

	/**
	 * Gets the {@link StateChannel} for {@link ChannelId#STATE_ON}.
	 * 
	 * @return the Channel {@link Value}
	 */
	public default Value<Boolean> getInverterState() {
		return this.getInverterStateChannel().value();
	}

	/**
	 * set the module to on grid mode. Reading back value makes no sense
	 * 
	 * @throws OpenemsNamedException
	 */
	public default void setOngridCommand() throws OpenemsNamedException {
		BooleanWriteChannel setdataGridOnCmd = this.channel(ChannelId.ON_GRID_CMD);
		setdataGridOnCmd.setNextWriteValue(true); // 1: true, other: illegal
	}

	/**
	 * set the module to off grid mode. Reading back value makes no sense
	 * 
	 * @throws OpenemsNamedException
	 */
	public default void setOffgridCommand() throws OpenemsNamedException {
		BooleanWriteChannel setdataGridOffCmd = this.channel(ChannelId.OFF_GRID_CMD);
		setdataGridOffCmd.setNextWriteValue(true); // 1: true, other: illegal
	}
}
