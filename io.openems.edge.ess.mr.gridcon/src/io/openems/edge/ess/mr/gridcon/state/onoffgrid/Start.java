package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

import java.time.LocalDateTime;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.ess.mr.gridcon.GridconSettings;
import io.openems.edge.ess.mr.gridcon.IState;
import io.openems.edge.ess.mr.gridcon.StateObject;
import io.openems.edge.ess.mr.gridcon.enums.Mode;


public class Start extends BaseState implements StateObject {

	private int timeSecondsToWaitForJanitza = 100;
	private LocalDateTime lastMethodCall;

	public Start(ComponentManager manager, DecisionTableCondition condition, String outputSyncBridge,
			String meterId, int timeSecondsToWaitForJanitza) {
		super(manager, condition, outputSyncBridge, meterId);
		this.timeSecondsToWaitForJanitza = timeSecondsToWaitForJanitza;
	}

	@Override
	public IState getState() {
		return OnOffGridState.START;
	}

	@Override
	public IState getNextState() {
		OnOffGridState stateToReturn = OnOffGridState.START;//UNDEF?!
		
		if (lastMethodCall == null) {
			lastMethodCall = LocalDateTime.now();
		}
		
		LocalDateTime now = LocalDateTime.now();

		if (DecisionTableHelper.isOnGrid(condition)) {
			stateToReturn = OnOffGridState.ON_GRID;
		}

		if (lastMethodCall.plusSeconds(timeSecondsToWaitForJanitza).isBefore(now))
			if (DecisionTableHelper.isOffGridStart(condition)) {
				stateToReturn = OnOffGridState.OFF_GRID_START;
		}

		if (DecisionTableHelper.isUndefined(condition)) {
			stateToReturn = OnOffGridState.UNDEFINED;
		}
		
		if (stateToReturn != OnOffGridState.START) {
			lastMethodCall = null;
		}
		
		return stateToReturn;
	}

	@Override
	public void act() throws OpenemsNamedException {
		setSyncBridge(true);
	}

	@Override
	public GridconSettings getGridconSettings() {
		return GridconSettings.createStopSettings(Mode.VOLTAGE_CONTROL);
	}

}
