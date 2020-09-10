package io.openems.edge.ess.mr.gridcon.onoffgrid.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.types.ChannelAddress;
import io.openems.edge.common.channel.BooleanWriteChannel;
import io.openems.edge.ess.mr.gridcon.GridconPcs;
import io.openems.edge.ess.mr.gridcon.GridconSettings;
import io.openems.edge.ess.mr.gridcon.enums.Mode;
import io.openems.edge.ess.mr.gridcon.onoffgrid.helper.Creator;
import io.openems.edge.ess.mr.gridcon.onoffgrid.helper.DummyComponentManager;
import io.openems.edge.ess.mr.gridcon.onoffgrid.helper.DummyDecisionTableCondition;
import io.openems.edge.ess.mr.gridcon.onoffgrid.helper.DummyIo;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.MeterCommunicationFailed;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.NaProtection1On;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.NaProtection2On;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.SyncBridgeOn;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition.VoltageInRange;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.OffGrid;
import io.openems.edge.ess.mr.gridcon.state.onoffgrid.OnOffGridState;

public class TestOffGrid {

	private OffGrid sut;
	private DummyComponentManager manager = Creator.getDummyComponentManager();
	private static DummyDecisionTableCondition condition;
		
	@Before
	public void setUp() throws Exception {
		condition = new DummyDecisionTableCondition(NaProtection1On.TRUE, NaProtection2On.TRUE, MeterCommunicationFailed.TRUE, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		sut = new OffGrid(//
				manager  
				, condition//
				, Creator.OUTPUT_SYNC_DEVICE_BRIDGE//
				, Creator.METER_ID//
				, Creator.TARGET_FREQUENCY_OFFGRID//
				);
	}

	@Test
	public final void testGetState() {
		assertEquals(OnOffGridState.OFF_GRID, sut.getState());
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
		// According to the state machine the next state is "OFF GRID_GRID_BACK" if condition is 0,0,0,1,1
		setCondition(NaProtection1On.FALSE, NaProtection2On.FALSE, MeterCommunicationFailed.FALSE, VoltageInRange.TRUE, SyncBridgeOn.TRUE);
		assertEquals(OnOffGridState.OFF_GRID_GRID_BACK, sut.getNextState());
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
	public void testAct() {
		// frequency preset for gridcon 50,6 Hz, Voltage control mode; sync bridge true
			
		try {
			sut.act();
			
			String channelName = DummyIo.adaptChannelAdress(Creator.OUTPUT_SYNC_DEVICE_BRIDGE);
			ChannelAddress adress = ChannelAddress.fromString(channelName);
			BooleanWriteChannel outputSyncDeviceBridgeChannel = this.manager.getChannel(adress);
			boolean expectedSyncBridge = true;
			boolean actualSyncBridge = outputSyncDeviceBridgeChannel.getNextWriteValue().get();
			
			assertEquals(expectedSyncBridge, actualSyncBridge);
			
			float f = Creator.TARGET_FREQUENCY_OFFGRID / GridconPcs.DEFAULT_GRID_FREQUENCY;
			GridconSettings expectedSettings = GridconSettings.createRunningSettings(1, f, Mode.VOLTAGE_CONTROL);
			
			GridconSettings actualSettings = sut.getGridconSettings();
			
			assertEquals(expectedSettings, actualSettings);
			
		} catch (OpenemsNamedException e) {
			fail(e.getMessage());
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
