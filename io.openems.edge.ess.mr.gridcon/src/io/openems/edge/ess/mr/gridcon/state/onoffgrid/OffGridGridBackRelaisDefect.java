package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.ess.mr.gridcon.GridconSettings;
import io.openems.edge.ess.mr.gridcon.IState;
import io.openems.edge.ess.mr.gridcon.enums.Mode;

public class OffGridGridBackRelaisDefect extends BaseState {

	public OffGridGridBackRelaisDefect(ComponentManager manager, DecisionTableCondition condition, String outputSyncBridge, String meterId) {
		super(manager, condition, outputSyncBridge,	meterId);
	}

	@Override
	public IState getState() {
		return OnOffGridState.OFF_GRID_GRID_BACK_RELAIS_DEFECT;
	}

	@Override
	public IState getNextState() {

		if (DecisionTableHelper.isUndefined(condition)) {
			return OnOffGridState.UNDEFINED;
		}

		if (DecisionTableHelper.isOnGrid(condition)) {
			return OnOffGridState.ON_GRID;
		}

		if (DecisionTableHelper.isStart(condition)) {
			return OnOffGridState.START;
		}

		if (DecisionTableHelper.isOffGridGridBackRelaisDefect(condition)) {
			return OnOffGridState.OFF_GRID_GRID_BACK_RELAIS_DEFECT;
		}

		return OnOffGridState.UNDEFINED;
	}

	@Override
	public void act() throws OpenemsNamedException {
		setSyncBridge(false);
	}

	@Override
	public GridconSettings getGridconSettings() {
		return GridconSettings.createStopSettings(Mode.VOLTAGE_CONTROL);
	}

}
