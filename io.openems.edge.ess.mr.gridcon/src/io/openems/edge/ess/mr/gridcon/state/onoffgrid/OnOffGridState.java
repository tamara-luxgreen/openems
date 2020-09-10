package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

import io.openems.common.types.OptionsEnum;
import io.openems.edge.ess.mr.gridcon.IState;

public enum OnOffGridState implements IState {
	UNDEFINED(-1, "Undefined"), //

	START(100, "Start"), //
	
	ON_GRID(201, "On Grid"), //
	
	OFF_GRID_START(300, "Off Grid Start"), //
	OFF_GRID(301, "Off Grid"), //
	OFF_GRID_GRID_BACK(302, "Off Grid Grid back"), //
	OFF_GRID_ADJUST_PARMETER(303, "Off Grid Adjust Parameter"), //
	OFF_GRID_GRID_BACK_RELAIS_DEFECT(304, "Off Grid Grid Back Relais Defect"), //
	OFF_GRID_GRID_BACK_INVERTER_OFF(305, "Off Grid Grid Back Inverter Off"), //
	; //

	private final int value;
	private final String name;

	private OnOffGridState(int value, String name) {
		this.value = value;
		this.name = name;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public OptionsEnum getUndefined() {
		return UNDEFINED;
	}
}