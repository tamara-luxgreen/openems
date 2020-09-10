package io.openems.edge.ess.mr.gridcon.onoffgrid;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import io.openems.edge.ess.mr.gridcon.onoffgrid.helper.DummyDecisionTableCondition;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.MeterCommunicationFailed;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.NaProtection1On;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.NaProtection2On;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.SyncBridgeOn;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.VoltageInRange;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableHelper;

public class TestDecisionTableHelper {
	
	@Test
	public final void testIsStart() {
		for (DecisionTableCondition dct: getAllConditions()) {
			if (startConditions.contains(dct)) {
				assertTrue(DecisionTableHelper.isStart(dct));		
			} else {
				assertFalse(DecisionTableHelper.isStart(dct));
			}
		}
	}

	@Test
	public final void testIsOnGrid() {					
		for (DecisionTableCondition dct: getAllConditions()) {
			if (onGridConditions.contains(dct)) {
				assertTrue(DecisionTableHelper.isOnGrid(dct));		
			} else {
				assertFalse(DecisionTableHelper.isOnGrid(dct));
			}
		}
	}
	
	@Test
	public final void testIsOffGridStart() {					
		for (DecisionTableCondition dct: getAllConditions()) {
			if (offGridStartConditions.contains(dct)) {
				assertTrue(DecisionTableHelper.isOffGridStart(dct));		
			} else {
				assertFalse(DecisionTableHelper.isOffGridStart(dct));
			}
		}
	}
	
	@Test
	public final void testIsOffGrid() {					
		for (DecisionTableCondition dct: getAllConditions()) {
			if (offGridConditions.contains(dct)) {
				assertTrue(DecisionTableHelper.isOffGrid(dct));		
			} else {
				assertFalse(DecisionTableHelper.isOffGrid(dct));
			}
		}
	}
	
	@Test
	public final void testIsOffGridGridBack() {					
		for (DecisionTableCondition dct: getAllConditions()) {
			if (offGridGridBackConditions.contains(dct)) {
				assertTrue(DecisionTableHelper.isOffGridGridBack(dct));		
			} else {
				assertFalse(DecisionTableHelper.isOffGridGridBack(dct));
			}
		}
	}
	
	@Test
	public final void testIsOffGridGridBackRelaisDefect() {					
		for (DecisionTableCondition dct: getAllConditions()) {
			if (offGridGridBackRelaisDefectConditions.contains(dct)) {
				assertTrue(DecisionTableHelper.isOffGridGridBackRelaisDefect(dct));		
			} else {
				assertFalse(DecisionTableHelper.isOffGridGridBackRelaisDefect(dct));
			}
		}
	}
	
	@Test
	public final void testIsOffGridAdjustParameter() {					
		for (DecisionTableCondition dct: getAllConditions()) {
			if (offGridAdjustParameterConditions.contains(dct)) {
				assertTrue(DecisionTableHelper.isOffGridAdjustParameter(dct));		
			} else {
				assertFalse(DecisionTableHelper.isOffGridAdjustParameter(dct));
			}
		}
	}

	@Test
	public final void testIsOffGridGridBackInverterOff() {					
		for (DecisionTableCondition dct: getAllConditions()) {
			if (offGridGridBackInverterOffConditions.contains(dct)) {
				assertTrue(DecisionTableHelper.isOffGridGridBackInverterOff(dct));		
			} else {
				assertFalse(DecisionTableHelper.isOffGridGridBackInverterOff(dct));
			}
		}
	}
	
	@Test
	public final void testIsUndefined() {					
		for (DecisionTableCondition dct: getAllConditions()) {
			if (getDefinedConditions().contains(dct)) {
				assertFalse(DecisionTableHelper.isUndefined(dct));
			} else {
				assertTrue(DecisionTableHelper.isUndefined(dct));		
			}
		}
	}
	
	
	//Start:  Condition 0 0 1 - 1
	Collection<DecisionTableCondition> startConditions = Arrays.asList(new DecisionTableCondition[] {
			new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)	
	});

	//On Grid: Condition 1 1 - - -
	Collection<DecisionTableCondition> onGridConditions = Arrays.asList(new DecisionTableCondition[] {
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET),
			
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET),
			
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET),
			
			
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET),
			
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET),
			
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET),
			
			
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET),
			
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET),
			
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)
	});
		
	//Off Grid Start:  Condition 0 0 1 - 1
	Collection<DecisionTableCondition> offGridStartConditions = Arrays.asList(new DecisionTableCondition[] {
			new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)	
	});	
		
	//Off Grid:  Condition 0 0 0 0 -
		Collection<DecisionTableCondition> offGridConditions = Arrays.asList(new DecisionTableCondition[] {
				new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE),
				new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE),
				new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)
	});	
		
	//Off Grid Grid Back:  Condition 0 0 0 1 1
	Collection<DecisionTableCondition> offGridGridBackConditions = Arrays.asList(new DecisionTableCondition[] {
		new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)
	});
	
	//Off Grid Grid Back Relais Defect:  Condition 0 0 0 1 0
	Collection<DecisionTableCondition> offGridGridBackRelaisDefectConditions = Arrays.asList(new DecisionTableCondition[] {
			new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)
	});

	//Off Grid Adjust Parameter:  Condition 1 0 0 1 1
		Collection<DecisionTableCondition> offGridAdjustParameterConditions = Arrays.asList(new DecisionTableCondition[] {
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)
	});
		
	//Off Grid Grid Back Inverter Off:  Condition 1 0 1 - 1
	Collection<DecisionTableCondition> offGridGridBackInverterOffConditions = Arrays.asList(new DecisionTableCondition[] {
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE),
			new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)
	});
	
	
	Collection<DecisionTableCondition> getDefinedConditions() {
		Collection<DecisionTableCondition> definedConditions = new ArrayList<>();
		
		definedConditions.addAll(startConditions);
		definedConditions.addAll(onGridConditions);
		definedConditions.addAll(offGridStartConditions);
		definedConditions.addAll(offGridConditions);
		definedConditions.addAll(offGridGridBackConditions);
		definedConditions.addAll(offGridAdjustParameterConditions);
		definedConditions.addAll(offGridGridBackRelaisDefectConditions);
		definedConditions.addAll(offGridGridBackInverterOffConditions);
		
		return definedConditions;
	}
		
	// Returns all possibilities of conditions
	private Collection<DecisionTableCondition> getAllConditions() {
		Collection<DecisionTableCondition> all = new ArrayList<>();
		
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //1
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //2
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //3
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //4
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //5
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //6
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //7
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //8
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //9
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //10
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //11
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //12
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //13
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //14
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //15
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //16
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //17
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //18
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //19
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //20
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //21
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //22
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //23
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //24
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //25
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //26
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //27
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //28
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //29
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //30
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //31
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //32
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //33
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //34
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //35
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //36
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //37
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //38
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //39
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //40
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //41
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //42
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //43
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //44
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //45
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //46
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //47
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //48
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //49
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //50
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //51
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //52
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //53
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //54
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //55
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //56
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //57
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //58
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //59
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //60
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //61
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //62
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //63
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //64
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //65
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //66
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //67
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //68
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //69
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //70
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //71
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //72
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //73
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //74
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //75
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //76
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //77
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //78
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //79
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //80
		all.add(new DummyDecisionTableCondition(NaProtection1On.UNSET, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //81
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //82
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //83
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //84
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //85
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //86
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //87
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //88
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //89
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //90
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //91
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //92
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //93
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //94
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //95
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //96
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //97
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //98
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //99
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //100
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //101
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //102
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //103
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //104
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //105
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //106
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //107
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //108
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //109
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //110
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //111
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //112
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //113
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //114
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //115
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //116
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //117
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //118
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //119
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //120
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //121
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //122
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //123
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //124
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //125
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //126
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //127
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //128
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //129
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //130
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //131
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //132
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //133
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //134
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //135
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //136
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //137
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //138
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //139
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //140
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //141
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //142
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //143
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //144
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //145
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //146
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //147
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //148
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //149
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //150
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //151
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //152
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //153
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //154
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //155
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //156
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //157
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //158
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //159
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //160
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //161
		all.add(new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //162
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //163
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //164
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //165
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //166
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //167
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //168
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //169
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //170
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //171
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //172
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //173
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //174
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //175
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //176
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //177
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //178
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //179
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //180
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //181
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //182
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //183
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //184
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //185
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //186
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //187
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //188
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.UNSET, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //189
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //190
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //191
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //192
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //193
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //194
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //195
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //196
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //197
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //198
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //199
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //200
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //201
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //202
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //203
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //204
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //205
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //206
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //207
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //208
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //209
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //210
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //211
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //212
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //213
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //214
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //215
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //216
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //217
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //218
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //219
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //220
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //221
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //222
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //223
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //224
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //225
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //226
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //227
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //228
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //229
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //230
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //231
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //232
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //233
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //234
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET)); //235
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE)); //236
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE)); //237
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET)); //238
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE)); //239
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE)); //240
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET)); //241
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE)); //242
		all.add(new DummyDecisionTableCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE)); //243	
		
		return all;
	}

}
