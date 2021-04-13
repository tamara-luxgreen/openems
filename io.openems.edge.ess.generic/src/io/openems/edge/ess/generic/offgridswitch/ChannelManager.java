package io.openems.edge.ess.generic.offgridswitch;

import io.openems.edge.battery.api.Battery;
import io.openems.edge.batteryinverter.api.OffGridBatteryInverter;
import io.openems.edge.ess.generic.common.AbstractGenericEssChannelManager;
import io.openems.edge.ess.generic.common.GenericManagedEss;

public class ChannelManager extends AbstractGenericEssChannelManager<Battery, OffGridBatteryInverter> {

	public ChannelManager(GenericManagedEss parent) {
		super(parent);
	}
}
