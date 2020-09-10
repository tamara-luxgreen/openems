package io.openems.edge.ess.mr.gridcon.onoffgrid.helper;

import io.openems.edge.ess.mr.gridcon.state.onoffgrid.DecisionTableCondition;

public class DummyDecisionTableCondition implements DecisionTableCondition {

	NaProtection1On isNaProtection1On;
	NaProtection2On isNaProtection2On;
	MeterCommunicationFailed isMeterCommunicationFailed;
	VoltageInRange isVoltageInRange;
	SyncBridgeOn isSyncBridgeOn;

	public DummyDecisionTableCondition(NaProtection1On isNaProtection1On, NaProtection2On isNaProtection2On,
			MeterCommunicationFailed isMeterCommunicationFailed, VoltageInRange isVoltageInRange,
			SyncBridgeOn isSyncBridgeOn) {
		this.isNaProtection1On = isNaProtection1On;
		this.isNaProtection2On = isNaProtection2On;
		this.isMeterCommunicationFailed = isMeterCommunicationFailed;
		this.isVoltageInRange = isVoltageInRange;
		this.isSyncBridgeOn = isSyncBridgeOn;
	}

	@Override
	public NaProtection1On isNaProtection1On() {
		return isNaProtection1On;
	}

	@Override
	public NaProtection2On isNaProtection2On() {
		return isNaProtection2On;
	}

	@Override
	public MeterCommunicationFailed isMeterCommunicationFailed() {
		return isMeterCommunicationFailed;
	}

	@Override
	public VoltageInRange isVoltageInRange() {
		return isVoltageInRange;
	}

	@Override
	public SyncBridgeOn isSyncBridgeOn() {
		return isSyncBridgeOn;
	}

	public void setNaProtection1On(NaProtection1On isNaProtection1On) {
		this.isNaProtection1On = isNaProtection1On;
	}

	public void setNaProtection2On(NaProtection2On isNaProtection2On) {
		this.isNaProtection2On = isNaProtection2On;
	}

	public void setMeterCommunicationFailed(MeterCommunicationFailed isMeterCommunicationFailed) {
		this.isMeterCommunicationFailed = isMeterCommunicationFailed;
	}

	public void setVoltageInRange(VoltageInRange isVoltageInRange) {
		this.isVoltageInRange = isVoltageInRange;
	}

	public void setSyncBridgeOn(SyncBridgeOn isSyncBridgeOn) {
		this.isSyncBridgeOn = isSyncBridgeOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isMeterCommunicationFailed == null) ? 0 : isMeterCommunicationFailed.hashCode());
		result = prime * result + ((isNaProtection1On == null) ? 0 : isNaProtection1On.hashCode());
		result = prime * result + ((isNaProtection2On == null) ? 0 : isNaProtection2On.hashCode());
		result = prime * result + ((isSyncBridgeOn == null) ? 0 : isSyncBridgeOn.hashCode());
		result = prime * result + ((isVoltageInRange == null) ? 0 : isVoltageInRange.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DummyDecisionTableCondition other = (DummyDecisionTableCondition) obj;
		if (isMeterCommunicationFailed != other.isMeterCommunicationFailed)
			return false;
		if (isNaProtection1On != other.isNaProtection1On)
			return false;
		if (isNaProtection2On != other.isNaProtection2On)
			return false;
		if (isSyncBridgeOn != other.isSyncBridgeOn)
			return false;
		if (isVoltageInRange != other.isVoltageInRange)
			return false;
		return true;
	}

	
	
}
