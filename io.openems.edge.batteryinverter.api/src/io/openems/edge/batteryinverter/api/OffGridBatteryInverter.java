package io.openems.edge.batteryinverter.api;

import org.osgi.annotation.versioning.ProviderType;

import io.openems.common.channel.AccessMode;
import io.openems.common.channel.Unit;
import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.types.OpenemsType;
import io.openems.edge.common.channel.Channel;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.channel.IntegerReadChannel;
import io.openems.edge.common.channel.IntegerWriteChannel;
import io.openems.edge.common.channel.value.Value;
import io.openems.edge.common.offgrid.GridType;
import io.openems.edge.common.startstop.StartStoppable;

@ProviderType
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

		ON_GRID_CMD(Doc.of(OpenemsType.BOOLEAN) //
				.accessMode(AccessMode.READ_WRITE)), //

		OFF_GRID_CMD(Doc.of(OpenemsType.BOOLEAN) //
				.accessMode(AccessMode.READ_WRITE)), //
		
		MOD_ON_CMD(Doc.of(FalseTrue.values()) //
				.accessMode(AccessMode.READ_WRITE)), //

		MOD_OFF_CMD(Doc.of(FalseTrue.values()) //
				.accessMode(AccessMode.READ_WRITE)), //

		SET_INTERN_DC_RELAY(Doc.of(OpenemsType.INTEGER) //
				.accessMode(AccessMode.READ_WRITE) //
				.unit(Unit.NONE)),
		CLEAR_FAILURE_CMD(Doc.of(OpenemsType.BOOLEAN) //
				.accessMode(AccessMode.READ_WRITE)),
		
		INVERTER_STATE(Doc.of(OpenemsType.BOOLEAN) //
				.accessMode(AccessMode.READ_WRITE)),
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
}
