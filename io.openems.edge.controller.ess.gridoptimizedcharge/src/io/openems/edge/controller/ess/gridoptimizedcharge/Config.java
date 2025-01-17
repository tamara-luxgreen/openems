package io.openems.edge.controller.ess.gridoptimizedcharge;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(//
		name = "Controller Ess Grid Optimized Charge", //
		description = "Delays the charging of the storage system based on predicted production and consumption and charges the Ess to limit the Sell-to-Grid power")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "ctrlGridOptimizedCharge0";

	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;

	@AttributeDefinition(name = "Ess-ID", description = "ID of Ess device.")
	String ess_id() default "ess0";

	@AttributeDefinition(name = "Grid-Meter-Id", description = "ID of the Grid-Meter.")
	String meter_id() default "meter0";

	@AttributeDefinition(name = "Is Sell-To-Grid-Limit enabled?", description = "Is the sell to grid limit logic enabled?.")
	boolean sellToGridLimitEnabled() default true;

	@AttributeDefinition(name = "Maximum allowed Sell-To-Grid power", description = "The target limit for sell-to-grid power.")
	int maximumSellToGridPower() default 7000;

	@AttributeDefinition(name = "Number of buffer minutes", description = "The number of buffer minutes to make sure the battery still "
			+ "charges full, even on prediction errors.")
	int noOfBufferMinutes() default 120;

	@AttributeDefinition(name = "Mode", description = "Set the type of mode.")
	Mode mode() default Mode.AUTOMATIC;

	@AttributeDefinition(name = "Target Time", description = "Charging to 100 % SoC is delayed till this hour of the day, e.g. 17 for 5 pm. Local timezone of this device is applied.")
	String manualTargetTime() default "17:00";

	@AttributeDefinition(name = "Debug Mode", description = "Activates the debug mode (Displays the Predicted Values in the Log only once!)")
	boolean debugMode() default false;

	@AttributeDefinition(name = "Ramp percentage in the sell to grid limit logic", description = "Percentage that is applied on the sellToGridLimit power limit, if it's not a more strict value.")
	int sellToGridLimitRampPercentage() default 2;

	@AttributeDefinition(name = "Ess target filter", description = "This is auto-generated by 'Ess-ID'.")
	String ess_target() default "";

	@AttributeDefinition(name = "Meter target filter", description = "This is auto-generated by 'Grid-Meter-ID'.")
	String meter_target() default "";

	String webconsole_configurationFactory_nameHint() default "Controller Ess Grid Optimized Charge [{id}]";
}