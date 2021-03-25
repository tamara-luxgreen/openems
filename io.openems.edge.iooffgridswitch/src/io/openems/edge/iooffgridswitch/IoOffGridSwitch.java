package io.openems.edge.iooffgridswitch;

import java.util.Optional;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.types.ChannelAddress;
import io.openems.edge.common.channel.BooleanReadChannel;
import io.openems.edge.common.channel.BooleanWriteChannel;
import io.openems.edge.common.component.AbstractOpenemsComponent;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.event.EdgeEventConstants;
import io.openems.edge.io.offgridswitch.api.OffGridSwitch;

@Designate(ocd = Config.class, factory = true)
@Component(//
		name = "Io.Off.Grid.Switch", //
		immediate = true, //
		configurationPolicy = ConfigurationPolicy.REQUIRE, //
		property = { //
				EventConstants.EVENT_TOPIC + "=" + EdgeEventConstants.TOPIC_CYCLE_BEFORE_PROCESS_IMAGE //
		} //
)
public class IoOffGridSwitch extends AbstractOpenemsComponent implements OffGridSwitch, OpenemsComponent, EventHandler {

	private final Logger log = LoggerFactory.getLogger(IoOffGridSwitch.class);

	private ChannelAddress inputMainContactorChannelAddr;
	private ChannelAddress inputGridStatusChannelAddr;
	private ChannelAddress inputGroundingContactorChannelAddr;

	private ChannelAddress outputMainContactorChannelAddr;
	private ChannelAddress outputGroundingContactorChannelAddr;

	@Reference
	protected ComponentManager componentManager;

	private Config config = null;

	public IoOffGridSwitch() {
		super(//
				OpenemsComponent.ChannelId.values(), //
				OffGridSwitch.ChannelId.values()//
		);
	}

	@Activate
	void activate(ComponentContext context, Config config) throws OpenemsNamedException {
		super.activate(context, config.id(), config.alias(), config.enabled());
		this.config = config;
		this.inputMainContactorChannelAddr = ChannelAddress.fromString(this.config.digitalInput1());
		this.inputGridStatusChannelAddr = ChannelAddress.fromString(this.config.digitalInput2());
		this.inputGroundingContactorChannelAddr = ChannelAddress.fromString(this.config.digitalInput3());

		this.outputMainContactorChannelAddr = ChannelAddress.fromString(this.config.digitalOutput1());
		this.outputGroundingContactorChannelAddr = ChannelAddress.fromString(this.config.digitalOutput3());
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();
	}

	@Override
	public void handleEvent(Event event) {
		if (!this.isEnabled()) {
			return;
		}
		switch (event.getTopic()) {
		case EdgeEventConstants.TOPIC_CYCLE_BEFORE_PROCESS_IMAGE:
			this.handleInput();
			break;
		}
	}

	public void handleInput() {
		BooleanReadChannel inChannel1, inChannel2, inChannel3;
		try {
			inChannel1 = this.componentManager.getChannel(inputMainContactorChannelAddr);
			this._setMainContactor(inChannel1.value().getOrError());
			inChannel2 = this.componentManager.getChannel(inputGridStatusChannelAddr);
			this._setGridStatus(inChannel2.value().getOrError());
			inChannel3 = this.componentManager.getChannel(inputGroundingContactorChannelAddr);
			this._setGroundingContactor(inChannel3.value().getOrError());
		} catch (IllegalArgumentException | OpenemsNamedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleWritingDigitalOutput(BooleanWriteChannel channel, boolean value) {
		{
			try {
				Optional<Boolean> currentValueOpt = channel.value().asOptional();
				if (!currentValueOpt.isPresent() || currentValueOpt.get() != value) {
					this.log.info("Set output [" + channel.address() + "] " + (value) + ".");
					channel.setNextWriteValue(value);
				}
			} catch (OpenemsNamedException e) {
				this.log.error("Unable to set output: [" + channel.address() + "] " + e.getMessage());

			}
		}
	}

	@Override
	public void handleWritingDigitalOutputForMainContactor(boolean value) throws IllegalArgumentException, OpenemsNamedException {
		handleWritingDigitalOutput(this.componentManager.getChannel(outputMainContactorChannelAddr), value);
	}

	@Override
	public void handleWritingDigitalOutputForGroundingContactor(boolean value)
			throws IllegalArgumentException, OpenemsNamedException {
		handleWritingDigitalOutput(this.componentManager.getChannel(outputGroundingContactorChannelAddr), value);
	}
}
