package io.openems.edge.ess.mr.gridcon;

import java.util.HashMap;
import java.util.Map;

import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.ess.mr.gridcon.enums.ParameterSet;
import io.openems.edge.ess.mr.gridcon.state.gridconstate.GridconStateObject;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition;

public class StateController {

	private static Map<IState, StateObject> generalStateObjects;
	private static Map<IState, GridconStateObject> gridconStateObjects;

	private static DecisionTableCondition condition;

	public static void initOnGrid(//
			ComponentManager manager, //
			String gridconPcs, //
			String b1, //
			String b2, //
			String b3, //
			boolean enableI1, //
			boolean enableI2, //
			boolean enableI3, //
			ParameterSet parameterSet, //
			String hardRestartRelayAdress, //
			float offsetCurrent) {

		generalStateObjects = new HashMap<IState, StateObject>();
		gridconStateObjects = new HashMap<IState, GridconStateObject>();


		gridconStateObjects.put(io.openems.edge.ess.mr.gridcon.state.gridconstate.GridconState.STOPPED,
				new io.openems.edge.ess.mr.gridcon.state.gridconstate.Stopped(manager, gridconPcs, b1, b2, b3,
						enableI1, enableI2, enableI3, parameterSet, hardRestartRelayAdress));
		gridconStateObjects.put(io.openems.edge.ess.mr.gridcon.state.gridconstate.GridconState.RUN,
				new io.openems.edge.ess.mr.gridcon.state.gridconstate.Run(manager, gridconPcs, b1, b2, b3, enableI1,
						enableI2, enableI3, parameterSet, hardRestartRelayAdress, offsetCurrent));
		gridconStateObjects.put(io.openems.edge.ess.mr.gridcon.state.gridconstate.GridconState.UNDEFINED, 
				new io.openems.edge.ess.mr.gridcon.state.gridconstate.Undefined(manager, gridconPcs, b1, b2, b3, hardRestartRelayAdress));
		gridconStateObjects.put(io.openems.edge.ess.mr.gridcon.state.gridconstate.GridconState.ERROR,
				new io.openems.edge.ess.mr.gridcon.state.gridconstate.Error(manager, gridconPcs, b1, b2, b3, enableI1,
						enableI2, enableI3, parameterSet, hardRestartRelayAdress));

		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.ongrid.OnGridState.UNDEFINED,
				new io.openems.edge.ess.mr.gridcon.state.ongrid.Undefined());
		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.ongrid.OnGridState.ERROR,
				new io.openems.edge.ess.mr.gridcon.state.ongrid.Error());
		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.ongrid.OnGridState.ONGRID, 
				new io.openems.edge.ess.mr.gridcon.state.ongrid.OnGrid());
	}

	public static StateObject getGeneralStateObject(IState state) {
		return generalStateObjects.get(state);
	}
	
	public static GridconStateObject getGridconStateObject(IState state) {
		return gridconStateObjects.get(state);
	}

	public static void initDecisionTableCondition(DecisionTableCondition tableCondition) {
		condition = tableCondition;
	}

	public static void initOnOffGrid(ComponentManager manager, String gridconPcs, String b1, String b2, String b3,
			boolean enableIpu1, boolean enableIpu2, boolean enableIpu3, ParameterSet parameterSet,
			String inputNaProtection1, boolean na1Inverted, String inputNaProtection2, boolean na2Inverted,
			String inputSyncDeviceBridge, boolean inputSyncDeviceBridgeInverted, String outputSyncDeviceBridge,
			boolean outputSyncDeviceBridgeInverted, String outputHardReset, boolean outputHardResetInverted,
			float targetFrequencyOnGrid, float targetFrequencyOffGrid, String meterId, float deltaFrequency,
			float deltaVoltage, //
			float offsetCurrent) {

		generalStateObjects = new HashMap<IState, StateObject>();
		gridconStateObjects = new HashMap<IState, GridconStateObject>();


		int time_to_wait = 123;
		int timeToWaitForSyncBridge = 10;
		
		// State objects for gridcon in ongrid mode
		gridconStateObjects.put(io.openems.edge.ess.mr.gridcon.state.gridconstate.GridconState.STOPPED,
				new io.openems.edge.ess.mr.gridcon.state.gridconstate.Stopped(manager, gridconPcs, b1, b2, b3,
						enableIpu1, enableIpu2, enableIpu3, parameterSet, outputHardReset));
		gridconStateObjects.put(io.openems.edge.ess.mr.gridcon.state.gridconstate.GridconState.RUN,
				new io.openems.edge.ess.mr.gridcon.state.gridconstate.Run(manager, gridconPcs, b1, b2, b3, enableIpu1,
						enableIpu2, enableIpu3, parameterSet, outputHardReset, offsetCurrent));
		gridconStateObjects.put(io.openems.edge.ess.mr.gridcon.state.gridconstate.GridconState.UNDEFINED, 
				new io.openems.edge.ess.mr.gridcon.state.gridconstate.Undefined(manager,
				gridconPcs, b1, b2, b3, outputHardReset));
		gridconStateObjects.put(io.openems.edge.ess.mr.gridcon.state.gridconstate.GridconState.ERROR,
				new io.openems.edge.ess.mr.gridcon.state.gridconstate.Error(manager, gridconPcs, b1, b2, b3, enableIpu1,
						enableIpu2, enableIpu3, parameterSet, outputHardReset));

		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.UNDEFINED,
				new io.openems.edge.ess.mr.gridcon.state.onoffgrid.Undefined(manager, condition, outputSyncDeviceBridge, meterId));
		
		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.START,
				new io.openems.edge.ess.mr.gridcon.state.onoffgrid.Start(manager, condition, outputSyncDeviceBridge,
						meterId, time_to_wait));
		
		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.ON_GRID, 
				new io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnGrid(manager, condition, outputSyncDeviceBridge, meterId, targetFrequencyOnGrid));
		
		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.OFF_GRID,
				new io.openems.edge.ess.mr.gridcon.state.onoffgrid.OffGrid(manager, condition, outputSyncDeviceBridge, meterId,
						targetFrequencyOffGrid));
		
		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.OFF_GRID_START,
				new io.openems.edge.ess.mr.gridcon.state.onoffgrid.OffGridStart(manager, condition, outputSyncDeviceBridge,
						meterId, targetFrequencyOffGrid));
				
		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.OFF_GRID_GRID_BACK,
				new io.openems.edge.ess.mr.gridcon.state.onoffgrid.OffGridGridBack(manager, condition, outputSyncDeviceBridge,
						meterId, targetFrequencyOffGrid));
		
		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.OFF_GRID_GRID_BACK_RELAIS_DEFECT,
				new io.openems.edge.ess.mr.gridcon.state.onoffgrid.OffGridGridBackRelaisDefect(manager, condition, outputSyncDeviceBridge, meterId));

		generalStateObjects.put(io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.OFF_GRID_ADJUST_PARMETER,
				new io.openems.edge.ess.mr.gridcon.state.onoffgrid.OffGridAdjustParameter(manager, condition, outputSyncDeviceBridge,
						meterId, deltaFrequency, deltaVoltage));
		
		generalStateObjects.put(
				io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState.OFF_GRID_GRID_BACK_INVERTER_OFF,
				new io.openems.edge.ess.mr.gridcon.state.onoffgrid.OffGridGridBackInverterOff(manager, condition, outputSyncDeviceBridge, meterId, timeToWaitForSyncBridge ));
	}

	public static void printCondition() {
		System.out.println("condition: \n" + condition);
	}

	
}
