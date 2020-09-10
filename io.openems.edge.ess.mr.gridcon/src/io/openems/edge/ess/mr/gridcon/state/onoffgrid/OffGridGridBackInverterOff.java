package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

import java.time.LocalDateTime;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.ess.mr.gridcon.GridconSettings;
import io.openems.edge.ess.mr.gridcon.IState;
import io.openems.edge.ess.mr.gridcon.enums.Mode;

public class OffGridGridBackInverterOff extends BaseState {

	private LocalDateTime lastMethodCall;
	private int timeSecondsToWaitForSyncBridge = 0;
	
	public OffGridGridBackInverterOff(ComponentManager manager, DecisionTableCondition condition, String outputSyncBridge, String meterId, int timeSecondsToWaitForSyncBridge) {
		super(manager, condition, outputSyncBridge,	meterId);
		this.timeSecondsToWaitForSyncBridge = timeSecondsToWaitForSyncBridge;
	}

	@Override
	public IState getState() {
		return OnOffGridState.OFF_GRID_GRID_BACK_INVERTER_OFF;
	}

	@Override
	public IState getNextState() {
		OnOffGridState stateToReturn = OnOffGridState.OFF_GRID_GRID_BACK_INVERTER_OFF;
				
		if (lastMethodCall == null) {
			lastMethodCall = LocalDateTime.now();
		}
	
		if (DecisionTableHelper.isUndefined(condition)) {
			stateToReturn = OnOffGridState.UNDEFINED;
		}

		if (DecisionTableHelper.isOnGrid(condition)) {
			stateToReturn = OnOffGridState.ON_GRID;
		}

		if (DecisionTableHelper.isOffGridGridBackInverterOff(condition)) {
			stateToReturn = OnOffGridState.OFF_GRID_GRID_BACK_INVERTER_OFF;
		}
		
		if (stateToReturn != OnOffGridState.START) {
			lastMethodCall = null;
		}
		
		return stateToReturn;
	}

	@Override
	public void act() throws OpenemsNamedException {
		if (lastMethodCall == null) {
			lastMethodCall = LocalDateTime.now();
		}
		
		LocalDateTime now = LocalDateTime.now();
		
		if (lastMethodCall.plusSeconds(timeSecondsToWaitForSyncBridge).isBefore(now)) {
			setSyncBridge(false);
		} else {
			setSyncBridge(true);
		}
	}

	@Override
	public GridconSettings getGridconSettings() {
		return GridconSettings.createStopSettings(Mode.VOLTAGE_CONTROL);
	}

}
