package io.openems.edge.batteryinverter.api;

import io.openems.common.channel.Unit;
import io.openems.common.types.OpenemsType;
import io.openems.edge.common.channel.Channel;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.channel.IntegerReadChannel;
import io.openems.edge.common.channel.value.Value;
import io.openems.edge.common.offgrid.GridType;
import io.openems.edge.common.offgrid.OperationMode;
import io.openems.edge.common.startstop.StartStoppable;

public interface OffGridBatteryInverter
		extends ManagedSymmetricBatteryInverter, SymmetricBatteryInverter, StartStoppable {

	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {
		/**
		 * Grid-Type.
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridBatteryInverter}
		 * <li>Type: Enum
		 * <li>Range: 0=3P4W, 1=3P3W
		 * </ul>
		 */
		GRID_TYPE(Doc.of(GridType.values())),

		/**
		 * OffGrid-Frequency.
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridBatteryInverter}
		 * <li>Type: Integer
		 * <li>Range: 40-60
		 * </ul>
		 */
		OFFGRID_FREQUENCY(Doc.of(OpenemsType.INTEGER)//
				.unit(Unit.HERTZ)),

		/**
		 * OffGrid-Frequency.
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridBatteryInverter}
		 * <li>Type: Integer
		 * <li>Range: 45-55
		 * </ul>
		 */
		OPERATION_MODE(Doc.of(OperationMode.values()))

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
	 * Gets the Channel for {@link ChannelId#GRID_TYPE}.
	 * 
	 * @return the Channel
	 */
	public default Channel<GridType> getGridTypeChannel() {
		return this.channel(ChannelId.GRID_TYPE);
	}

	/**
	 * Does the battery has 3 phase-4 wire or 3 phase-3 wire grid type ? See
	 * {@link ChannelId#GRID_TYPE}.
	 * 
	 * @return the Channel {@link Value}
	 */
	public default GridType getGridType() {
		return this.getGridTypeChannel().value().asEnum();
	}

	/**
	 * Internal method to set the 'nextValue' on {@link ChannelId#GRID_TYPE}
	 * Channel.
	 * 
	 * @param value the next value
	 */
	public default void _setGridType(GridType value) {
		this.getGridTypeChannel().setNextValue(value);
	}

	/**
	 * Gets the Channel for {@link ChannelId#FREQUENCY}.
	 *
	 * @return the Channel
	 */
	public default IntegerReadChannel getOffGridFrequencyChannel() {
		return this.channel(ChannelId.OFFGRID_FREQUENCY);
	}

	/**
	 * Gets the * {@link ChannelId#OFFGRID_FREQUENCY}.
	 *
	 * @return the Channel {@link Value}
	 */
	public default Value<Integer> getOffGridFrequency() {
		return this.getOffGridFrequencyChannel().value();
	}

	/**
	 * Internal method to set the 'nextValue' on {@link ChannelId#OFFGRID_FREQUENCY}
	 * Channel.
	 *
	 * @param value the next value
	 */
	public default void _setOffGridFrequency(Integer value) {
		this.getOffGridFrequencyChannel().setNextValue(value);
	}

	/**
	 * Internal method to set the 'nextValue' on {@link ChannelId#OFFGRID_FREQUENCY}
	 * Channel.
	 *
	 * @param value the next value
	 */
	public default void _setOffGridFrequency(int value) {
		this.getOffGridFrequencyChannel().setNextValue(value);
	}

	/**
	 * Gets the Channel for {@link ChannelId#OPERATION_MODE}.
	 * 
	 * @return the Channel
	 */
	public default Channel<OperationMode> getOperationModeChannel() {
		return this.channel(ChannelId.OPERATION_MODE);
	}

	/**
	 * Will inverter operate only in On grid or On/Off grid mode.
	 * {@link ChannelId#OPERATION_MODE}.
	 * 
	 * @return the Channel {@link Value}
	 */
	public default OperationMode getOperationMode() {
		return this.getOperationModeChannel().value().asEnum();
	}

	/**
	 * Internal method to set the 'nextValue' on {@link ChannelId#OPERATION_MODE}
	 * Channel.
	 * 
	 * @param value the next value
	 */
	public default void _setOperaitonMode(OperationMode value) {
		this.getOperationModeChannel().setNextValue(value);
	}

}
