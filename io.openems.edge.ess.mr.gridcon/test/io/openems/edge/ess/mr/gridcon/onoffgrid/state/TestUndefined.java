package io.openems.edge.ess.mr.gridcon.onoffgrid.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import io.openems.edge.ess.mr.gridcon.onoffgrid.helper.Creator;
import io.openems.edge.ess.mr.gridcon.onoffgrid.helper.DummyDecisionTableCondition;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.MeterCommunicationFailed;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.NaProtection1On;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.NaProtection2On;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.SyncBridgeOn;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.VoltageInRange;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.Undefined;

public class TestUndefined {

	private Undefined sut;
	private static DummyDecisionTableCondition condition;
		
	@Before
	public void setUp() throws Exception {
		condition = new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		sut = new Undefined(//
				Creator.getDummyComponentManager()//
				, condition//
				, Creator.OUTPUT_SYNC_DEVICE_BRIDGE//
				, Creator.METER_ID//
				);
	}

	@Test
	public final void testGetState() {
		assertEquals(OnOffGridState.UNDEFINED, sut.getState());
	}

	@Test
	public void testGetNextStateStart() {
		// According to the state machine the next state is "START" if condition is 0,0,1,-,0
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.START, sut.getNextState());
		
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.START, sut.getNextState());
		
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.START, sut.getNextState());
	}
	
	@Test
	public void testGetNextStateOnGrid() {
		// According to the state machine the next state is "ON GRID" if condition is 1,1,-,-,-
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
				
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
				
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
				
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.FALSE, VoltageInRange.UNSET, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		

		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
				
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
				
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
				
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
				
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.FALSE, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
				
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.TRUE, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
				
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.UNSET, VoltageInRange.UNSET, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.ON_GRID, sut.getNextState());
	}
	
	@Test
	public void testGetNextStateOffGridGridBackRelaisDefect() {
		// According to the state machine the next state is "OFF GRID_BACK_RELAIS_DEFECT" if condition is 0,0,0,1,0
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.OFF_GRID_GRID_BACK_RELAIS_DEFECT, sut.getNextState());
	}
	
	@Test
	public void testGetNextStateOffGridStart() {
		// According to the state machine the next state is "OFF_GRID_START" if condition is 0,0,1,-,1
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID_START, sut.getNextState());

		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID_START, sut.getNextState());
		
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID_START, sut.getNextState());
	}

	@Test
	public void testGetNextStateOffGrid() {
		// According to the state machine the next state is "OFF_GRID" if condition is 0,0,0,0,-
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.FALSE);
		assertEquals(OnOffGridState.OFF_GRID, sut.getNextState());

		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID, sut.getNextState());
		
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.FALSE, SyncBridgeOn.UNSET);
		assertEquals(OnOffGridState.OFF_GRID, sut.getNextState());
	}
	
	@Test
	public void testGetNextStateOffGridGridBack() {
		// According to the state machine the next state is "OFF GRID GRID BACK" if condition is 0,0,0,1,1
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID_GRID_BACK, sut.getNextState());
		
	}
	
	@Test
	public void testGetNextStateAdjustParameter() {
		// According to the state machine the next state is "OFF GRID ADJUST PARAMETER" if condition is 1,0,0,1,1
		setCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID_ADJUST_PARMETER, sut.getNextState());
	}
	
	@Test
	public void testGetNextStateOffGridGridBackInverterOff() {
		// According to the state machine the next state is "OFF_GRID GRID BACK INVERTER OFF" if condition is 1,0,1,-,1
		setCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.FALSE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID_GRID_BACK_INVERTER_OFF, sut.getNextState());

		setCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID_GRID_BACK_INVERTER_OFF, sut.getNextState());
		
		setCondition(NaProtection1On.TRUE, NaProtection2On.FALSE, MeterCommunicationFailed.TRUE, VoltageInRange.UNSET, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID_GRID_BACK_INVERTER_OFF, sut.getNextState());
	}
	
	@Test
	public void testAct() {
		try {
			sut.act();
			
			assertNull(sut.getGridconSettings());
			
		} catch (Exception e) {
			fail("Cannot happen, Undefined.act() should do nothing!");
		}
	}
	
	private static void setCondition(NaProtection1On b, NaProtection2On c, MeterCommunicationFailed e, VoltageInRange f, SyncBridgeOn g) {
		condition.setNaProtection1On(b);
		condition.setNaProtection2On(c);
		condition.setMeterCommunicationFailed(e);
		condition.setVoltageInRange(f);
		condition.setSyncBridgeOn(g);
	}
}
