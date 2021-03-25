package io.openems.edge.iooffgridswitch;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(//
		name = "IO Off Grid Switch", //
		description = "")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "ioOffGridSwitch0";

	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;

	// input channel ------------------------
	@AttributeDefinition(name = "Digital Input Main Control Relay", description = "Input channle of Main Control Relay")
	String digitalInput1() default "io0/DigitalInputM1C1";

	@AttributeDefinition(name = "Digital Input Grid Status", description = "Input channel of grid status")
	String digitalInput2() default "io0/DigitalInputM1C2";

	@AttributeDefinition(name = "Digital Input Grounding", description = "Input channel of grounding control relay")
	String digitalInput3() default "io0/DigitalInputM1C3";
	
	// output channel ------------------------
	@AttributeDefinition(name = "Digital Output Main Control Relay", description = "Output channel of main control relay")
    String digitalOutput1() default "io0/DigitalOutputM1C1";
	
	@AttributeDefinition(name = "Digital Output Grounding Control Relay", description = "Output channel of grounding control relay")
    String digitalOutput3() default "io0/DigitalOutputM1C3";

	String webconsole_configurationFactory_nameHint() default "IO Off Grid Switch [{id}]";

}