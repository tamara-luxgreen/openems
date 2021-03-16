package io.openems.edge.iooffgridswitch;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.metatype.annotations.Designate;

import io.openems.edge.common.component.AbstractOpenemsComponent;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.event.EdgeEventConstants;
import io.openems.edge.io.offgridswitch.api.OffGridSwitch;

@Designate(ocd = Config.class, factory = true)
@Component(//
		name = "io.openems.edge.iooffgridswitch", //
		immediate = true, //
		configurationPolicy = ConfigurationPolicy.REQUIRE, //
		property = { //
				EventConstants.EVENT_TOPIC + "=" + EdgeEventConstants.TOPIC_CYCLE_BEFORE_PROCESS_IMAGE //
		} //
)
public class IoOffGridSwitch extends AbstractOpenemsComponent implements OffGridSwitch, OpenemsComponent, EventHandler {

//	private Config config = null;

	public IoOffGridSwitch() {
		super(//
				OpenemsComponent.ChannelId.values(), //
				OffGridSwitch.ChannelId.values()//
		);
	}

	@Activate
	void activate(ComponentContext context, Config config) {
		super.activate(context, config.id(), config.alias(), config.enabled());
//		this.config = config;
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
			// TODO: fill channels
			break;
		}
	}

	@Override
	public String debugLog() {
		return "Hello Bitch";
	}
}
