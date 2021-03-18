package io.openems.edge.io.offgridswitch.api;

import io.openems.common.channel.AccessMode;
import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.types.OpenemsType;
import io.openems.edge.common.channel.BooleanWriteChannel;
import io.openems.edge.common.channel.Channel;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.channel.value.Value;
import io.openems.edge.common.component.OpenemsComponent;

public interface OffGridSwitch extends OpenemsComponent {
	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {
		/**
		 * Main-Contactor.
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridSwitch}
		 * <li>Type: Boolean
		 * <li>Range: 0=CONTACTOR TURNED ON, 1=CONTACTOR TURNED OFF
		 * </ul>
		 */
		MAIN_CONTACTOR(Doc.of(OpenemsType.BOOLEAN)//
				.accessMode(AccessMode.READ_ONLY)), //

		/**
		 * Set-Main-Contactor.
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridSwitch}
		 * <li>Type: Boolean
		 * <li>Range: 0=CONTACTOR TURNED ON, 1=CONTACTOR TURNED OFF
		 * </ul>
		 */
		SET_MAIN_CONTACTOR(Doc.of(OpenemsType.BOOLEAN)//
				.accessMode(AccessMode.WRITE_ONLY)), //

		/**
		 * Grounding-Contactor.
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridSwitch}
		 * <li>Type: Boolean
		 * <li>Range: 0=CONTACTOR TURNED ON, 1=CONTACTOR TURNED OFF
		 * </ul>
		 */
		GROUNDING_CONTACTOR(Doc.of(OpenemsType.BOOLEAN)//
				.accessMode(AccessMode.READ_ONLY)), //
		/**
		 * Set-Grounding-Contactor.
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridSwitch}
		 * <li>Type: Boolean
		 * <li>Range: 0=CONTACTOR TURNED ON, 1=CONTACTOR TURNED OFF
		 * </ul>
		 */
		SET_GROUNDING_CONTACTOR(Doc.of(OpenemsType.BOOLEAN)//
				.accessMode(AccessMode.WRITE_ONLY)), //

		/**
		 * Grid Status.
		 * 
		 * <ul>
		 * <li>Interface: {@link OffGridSwitch}
		 * <li>Type: Boolean
		 * <li>Range: 0=ON-GRID, 1=OFF-GRID
		 * </ul>
		 */
		GRID_STATUS(Doc.of(OpenemsType.BOOLEAN)//
				.accessMode(AccessMode.READ_ONLY)),//
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
	 * Gets the Channel for {@link ChannelId#MAIN_CONTACTOR}.
	 * 
	 * @return the Channel
	 */
	public default Channel<Boolean> getMainContactorChannel() {
		return this.channel(ChannelId.MAIN_CONTACTOR);
	}

	/**
	 * {@link ChannelId#MAIN_CONTACTOR}.
	 * 
	 * @return the Channel {@link Value}
	 */
	public default Boolean getMainContactor() {
		return this.getMainContactorChannel().value().get();
	}

	/**
	 * Internal method to set the 'nextValue' on {@link ChannelId#MAIN_CONTACTOR}
	 * Channel.
	 * 
	 * @param value the next value
	 */
	public default void _setMainContactor(Boolean value) {
		this.getMainContactorChannel().setNextValue(value);
	}

//	/**
//	 * Gets the Channel for {@link ChannelId#SET_MAIN_CONTACTOR}.
//	 *
//	 * @return the Channel
//	 */
//	public default BooleanWriteChannel getSetMainContactorChannel() {
//		return this.channel(ChannelId.SET_MAIN_CONTACTOR);
//	}
//
//	/**
//	 * See {@link ChannelId#SET_MAIN_CONTACTOR}.
//	 * 
//	 * @param optional the next write value
//	 * @throws OpenemsNamedException on error
//	 */
//	public default void setMainContactor(Boolean value) throws OpenemsNamedException {
//		this.getSetMainContactorChannel().setNextWriteValue(value);
//	}

	/**
	 * Gets the Channel for {@link ChannelId#GROUNDING_CONTACTOR}.
	 * 
	 * @return the Channel
	 */
	public default Channel<Boolean> getGroundingContactorChannel() {
		return this.channel(ChannelId.GROUNDING_CONTACTOR);
	}

	/**
	 * {@link ChannelId#GROUNDING_CONTACTOR}.
	 * 
	 * @return the Channel {@link Value}
	 */
	public default Boolean getGroundingContactor() {
		return this.getGroundingContactorChannel().value().get();
	}

	/**
	 * Internal method to set the 'nextValue' on
	 * {@link ChannelId#GROUNDING_CONTACTOR} Channel.
	 * 
	 * @param value the next value
	 */
	public default void _setGroundingContactor(Boolean value) {
		this.getGroundingContactorChannel().setNextValue(value);
	}

//	/**
//	 * Gets the Channel for {@link ChannelId#SET_GROUNDING_CONTACTOR}.
//	 *
//	 * @return the Channel
//	 */
//	public default BooleanWriteChannel getSetGroundingContactorChannel() {
//		return this.channel(ChannelId.SET_GROUNDING_CONTACTOR);
//	}
//
//	/**
//	 * See {@link ChannelId#SET_GROUNDING_CONTACTOR}.
//	 * 
//	 * @param value the next write value
//	 * @throws OpenemsNamedException on error
//	 */
//	public default void setGroundingContactor(Boolean value) throws OpenemsNamedException {
//		this.getSetGroundingContactorChannel().setNextWriteValue(value);
//	}

	/**
	 * Gets the Channel for {@link ChannelId#GRID_STATUS}.
	 * 
	 * @return the Channel
	 */
	public default Channel<Boolean> getGridStatusChannel() {
		return this.channel(ChannelId.GRID_STATUS);
	}

	/**
	 * {@link ChannelId#GRID_STATUS}.
	 * 
	 * @return the Channel {@link Value}
	 */
	public default Boolean getGridStatus() {
		return this.getGridStatusChannel().value().get();
	}

	/**
	 * Internal method to set the 'nextValue' on {@link ChannelId#GRID_STATUS}
	 * Channel.
	 * 
	 * @param value the next value
	 */
	public default void _setGridStatus(Boolean value) {
		this.getGridStatusChannel().setNextValue(value);
	}
	
	
	public void handleWritingDigitalOutput(BooleanWriteChannel channel, boolean value) ;
	
	
}
