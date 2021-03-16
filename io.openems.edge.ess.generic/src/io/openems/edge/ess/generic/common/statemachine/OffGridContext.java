package io.openems.edge.ess.generic.common.statemachine;

import io.openems.edge.battery.api.Battery;
import io.openems.edge.batteryinverter.api.OffGridBatteryInverter;
import io.openems.edge.ess.generic.common.GenericManagedEss;
import io.openems.edge.io.offgridswitch.api.OffGridSwitch;

public class OffGridContext extends Context {

	protected final Battery battery;
	protected final OffGridBatteryInverter batteryInverter;
	protected final OffGridSwitch offGridSwitch;

	public OffGridContext(GenericManagedEss parent, Battery battery, OffGridBatteryInverter batteryInverter,
			OffGridSwitch offGridSwitch) {
		super(parent, battery, batteryInverter);
		this.battery = battery;
		this.batteryInverter = batteryInverter;
		this.offGridSwitch = offGridSwitch;
	}
}