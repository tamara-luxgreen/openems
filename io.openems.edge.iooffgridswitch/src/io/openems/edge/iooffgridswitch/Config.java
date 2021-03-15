package io.openems.edge.iooffgridswitch;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(//
		name = "io.openems.edge.iooffgridswitch", //
		description = "")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "io.openems.edge.iooffgridswitch0";

	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;

	// input channel ------------------------
	@AttributeDefinition(name = "Main contactor", description = "Input channle to control Main Contactor")
	String digitalInput1() default "io0/DigitalInputM1C1";

	@AttributeDefinition(name = "Grid detector", description = "Input channel to detect grid")
	String digitalInput2() default "io0/DigitalInputM1C2";

	@AttributeDefinition(name = "Grounding", description = "Input channel to control grounding")
	String digitalInput3() default "io0/DigitalInputM2C1";

	// output channel ------------------------
	@AttributeDefinition(name = "DigitalOutput", description = "Output channel 1")
	String digitalOutput1() default "io0/DigitalInputM3C1";

	@AttributeDefinition(name = "DigitalOutput", description = "Output channel 2")
	String digitalOutput2() default "io0/DigitalInputM3C2";

	@AttributeDefinition(name = "DigitalOutput", description = "Output channel to control grounding")
	String digitalOutput3() default "io0/DigitalInputM4C1";

	String webconsole_configurationFactory_nameHint() default "io.openems.edge.iooffgridswitch [{id}]";

}