package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.ess.mr.gridcon.GridconPcs;
import io.openems.edge.ess.mr.gridcon.GridconSettings;
import io.openems.edge.ess.mr.gridcon.IState;
import io.openems.edge.ess.mr.gridcon.enums.Mode;

public class OffGridAdjustParameter extends BaseState {

	private float deltaVoltage;
	private float deltaFrequency;
	
	GridconSettings gridconSettings = null;
	
	public OffGridAdjustParameter(ComponentManager manager, DecisionTableCondition condition, String outputSyncBridge,
			String meterId, float deltaFrequency, float deltaVoltage) {
		super(manager, condition, outputSyncBridge, meterId);

		this.deltaFrequency = deltaFrequency;
		this.deltaVoltage = deltaVoltage;
	}
	

	@Override
	public void act() throws OpenemsNamedException {
		setSyncBridge(true);
	}

	@Override
	public GridconSettings getGridconSettings() {
		float targetFrequency = getFrequencyOnMeter() + deltaFrequency;
		float frequencyFactor = targetFrequency / GridconPcs.DEFAULT_GRID_FREQUENCY;

		float targetVoltage = getVoltageOnMeter() + deltaVoltage;
		float voltageFactor = targetVoltage / GridconPcs.DEFAULT_GRID_VOLTAGE;
		
		
		Mode mode = Mode.VOLTAGE_CONTROL;
		gridconSettings = GridconSettings.createRunningSettings(voltageFactor, frequencyFactor, mode);
		
		return gridconSettings;
	}

	@Override
	public IState getState() {
		return OnOffGridState.OFF_GRID_ADJUST_PARMETER;
	}


	@Override
	public IState getNextState() {
		
		if (DecisionTableHelper.isOffGrid(condition)) {
			return OnOffGridState.OFF_GRID;
		}
		
		if (DecisionTableHelper.isOnGrid(condition)) {
			return OnOffGridState.ON_GRID;
		}

		if (DecisionTableHelper.isOffGridAdjustParameter(condition)) {
			return OnOffGridState.OFF_GRID_ADJUST_PARMETER;
		}
		
		if (DecisionTableHelper.isOffGridGridBackInverterOff(condition)) {
			return OnOffGridState.OFF_GRID_GRID_BACK_INVERTER_OFF;
		}
		
		return OnOffGridState.UNDEFINED;
	}
}
