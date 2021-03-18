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
	@AttributeDefinition(name = "Main contactor", description = "Input channle to control Main Contactor")
	String digitalInput1() default "io0/DigitalOutputM1C1";

	@AttributeDefinition(name = "Grid detector", description = "Input channel to detect grid")
	String digitalInput2() default "io0/DigitalInputM1C2";

	@AttributeDefinition(name = "Grounding", description = "Input channel to control grounding")
	String digitalInput3() default "io0/DigitalOutputM1C3";
	
	// output channel ------------------------
	@AttributeDefinition(name = "DigitalOutput", description = "Output channel 1")
    String digitalOutput1() default "io0/DigitalOutputM1C1";
	
	@AttributeDefinition(name = "DigitalOutput", description = "Output channel 2")
    String digitalOutput2() default "io0/DigitalOutputM1C2";
	
	@AttributeDefinition(name = "DigitalOutput", description = "Output channel 2")
    String digitalOutput3() default "io0/DigitalOutputM1C3";
	
	

	String webconsole_configurationFactory_nameHint() default "IO Off Grid Switch [{id}]";

}