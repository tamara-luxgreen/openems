package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.ess.mr.gridcon.GridconPcs;
import io.openems.edge.ess.mr.gridcon.GridconSettings;
import io.openems.edge.ess.mr.gridcon.IState;
import io.openems.edge.ess.mr.gridcon.enums.Mode;

public class OnGrid extends BaseState {

	private float targetFrequencyOnGrid;

	public OnGrid(ComponentManager manager, DecisionTableCondition condition, String outputSyncBridge,
			String meterId, float targetFrequencyOnGrid) {
		super(manager, condition, outputSyncBridge, meterId);
		this.targetFrequencyOnGrid = targetFrequencyOnGrid;
	}

	@Override
	public IState getState() {
		return OnOffGridState.ON_GRID;
	}

	@Override
	public IState getNextState() {

		if (DecisionTableHelper.isUndefined(condition)) {
			return OnOffGridState.UNDEFINED;
		}

		if (DecisionTableHelper.isOnGrid(condition)) {
			return OnOffGridState.ON_GRID;
		}

		if (DecisionTableHelper.isOffGrid(condition)) {
			return OnOffGridState.OFF_GRID;
		}
		
		if (DecisionTableHelper.isOffGridStart(condition)) {
			return OnOffGridState.OFF_GRID_START;
		}

		return OnOffGridState.UNDEFINED;
	}

	@Override
	public void act() throws OpenemsNamedException {
		setSyncBridge(true);
	}

	@Override
	public GridconSettings getGridconSettings() {
		float frequencyFactor = targetFrequencyOnGrid / GridconPcs.DEFAULT_GRID_FREQUENCY;
		return GridconSettings.createRunningSettings(1, frequencyFactor, Mode.CURRENT_CONTROL);
	}

}
