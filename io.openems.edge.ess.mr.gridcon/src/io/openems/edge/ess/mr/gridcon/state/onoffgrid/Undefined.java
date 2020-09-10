package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.ess.mr.gridcon.GridconSettings;
import io.openems.edge.ess.mr.gridcon.IState;
import io.openems.edge.ess.mr.gridcon.StateObject;

public class Undefined extends BaseState implements StateObject {

	private final Logger log = LoggerFactory.getLogger(Undefined.class);

	public Undefined(ComponentManager manager, DecisionTableCondition condition, String outputSyncBridge,
			String meterId) {
		super(manager, condition, outputSyncBridge, meterId);
	}

	@Override
	public IState getState() {
		return io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.UNDEFINED;
	}

	@Override
	public IState getNextState() {
		if (DecisionTableHelper.isUndefined(condition)) {
			return OnOffGridState.UNDEFINED;
		}

		if (DecisionTableHelper.isStart(condition)) {
			return OnOffGridState.START;
		}
		
		if (DecisionTableHelper.isOnGrid(condition)) {
			return OnOffGridState.ON_GRID;
		}
		
		if (DecisionTableHelper.isOffGridGridBackRelaisDefect(condition)) {
			return OnOffGridState.OFF_GRID_GRID_BACK_RELAIS_DEFECT;
		}

		if (DecisionTableHelper.isOffGrid(condition)) {
			return OnOffGridState.OFF_GRID;
		}
		
		if (DecisionTableHelper.isOffGridStart(condition)) {
			return OnOffGridState.OFF_GRID_START;
		}
		
		if (DecisionTableHelper.isOffGridGridBack(condition)) {
			return OnOffGridState.OFF_GRID_GRID_BACK;
		}
		
		if (DecisionTableHelper.isOffGridAdjustParameter(condition)) {
			return OnOffGridState.OFF_GRID_ADJUST_PARMETER;
		}
		
		if (DecisionTableHelper.isOffGridGridBackInverterOff(condition)) {
			return OnOffGridState.OFF_GRID_GRID_BACK_INVERTER_OFF;
		}

		return OnOffGridState.UNDEFINED;
	}

	@Override
	public void act() {
		log.info("Nothing to do!");
	}

	@Override
	public GridconSettings getGridconSettings() {
		return null;
	}
}
