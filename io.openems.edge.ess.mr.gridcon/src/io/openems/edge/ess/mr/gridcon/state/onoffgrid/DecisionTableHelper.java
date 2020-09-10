package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

public class DecisionTableHelper {

	public static boolean isStart(DecisionTableCondition condition) {
		boolean ret = false;
		try {
			ret = condition.isNaProtection1On().getValue() == Boolean.FALSE
					&& condition.isNaProtection2On().getValue() == Boolean.FALSE
					&& condition.isMeterCommunicationFailed().getValue() == Boolean.TRUE
					&& condition.isSyncBridgeOn().getValue() == Boolean.FALSE;
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	public static boolean isOffGridStart(DecisionTableCondition condition) {
		boolean ret = false;
		try {
			ret = condition.isNaProtection1On().getValue() == Boolean.FALSE
					&& condition.isNaProtection2On().getValue() == Boolean.FALSE
					&& condition.isMeterCommunicationFailed().getValue() == Boolean.TRUE
					&& condition.isSyncBridgeOn().getValue() == Boolean.TRUE;
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	
	public static boolean isOnGrid(DecisionTableCondition condition) {
		boolean ret = false;
		try {
			ret = condition.isNaProtection1On().getValue() == Boolean.TRUE
					&& condition.isNaProtection2On().getValue() == Boolean.TRUE;
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	public static boolean isOffGrid(DecisionTableCondition condition) {
		boolean ret = false;
		try {
			ret = condition.isNaProtection1On().getValue() == Boolean.FALSE
					&& condition.isNaProtection2On().getValue() == Boolean.FALSE
					&& condition.isMeterCommunicationFailed().getValue() == Boolean.FALSE
					&& condition.isVoltageInRange().getValue() == Boolean.FALSE;
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	
	public static boolean isOffGridGridBack(DecisionTableCondition condition) {
		boolean ret = false;
		try {
			ret = condition.isNaProtection1On().getValue() == Boolean.FALSE
					&& condition.isNaProtection2On().getValue() == Boolean.FALSE
					&& condition.isMeterCommunicationFailed().getValue() == Boolean.FALSE
					&& condition.isVoltageInRange().getValue() == Boolean.TRUE
					&& condition.isSyncBridgeOn().getValue() == Boolean.TRUE;
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	
	public static boolean isOffGridGridBackRelaisDefect(DecisionTableCondition condition) {
		boolean ret = false;
		try {
			ret = condition.isNaProtection1On().getValue() == Boolean.FALSE
					&& condition.isNaProtection2On().getValue() == Boolean.FALSE
					&& condition.isMeterCommunicationFailed().getValue() == Boolean.FALSE
					&& condition.isVoltageInRange().getValue() == Boolean.TRUE
					&& condition.isSyncBridgeOn().getValue() == Boolean.FALSE;
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	
	public static boolean isOffGridAdjustParameter(DecisionTableCondition condition) {
		boolean ret = false;
		try {
			ret = condition.isNaProtection1On().getValue() == Boolean.TRUE
					&& condition.isNaProtection2On().getValue() == Boolean.FALSE
					&& condition.isMeterCommunicationFailed().getValue() == Boolean.FALSE
					&& condition.isVoltageInRange().getValue() == Boolean.TRUE
					&& condition.isSyncBridgeOn().getValue() == Boolean.TRUE;
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	public static boolean isOffGridGridBackInverterOff(DecisionTableCondition condition) {
		boolean ret = false;
		try {
			ret = condition.isNaProtection1On().getValue() == Boolean.TRUE
					&& condition.isNaProtection2On().getValue() == Boolean.FALSE
					&& condition.isMeterCommunicationFailed().getValue() == Boolean.TRUE
					&& condition.isSyncBridgeOn().getValue() == Boolean.TRUE;
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	

	public static boolean isUndefined(DecisionTableCondition condition) {
		return !isStart(condition) && // 
			   !isOnGrid(condition) && //
			   !isOffGridStart(condition) && //
			   !isOffGrid(condition) && //
			   !isOffGridGridBack(condition) && //
			   !isOffGridAdjustParameter(condition) && //
			   !isOffGridGridBackRelaisDefect(condition) && //
			   !isOffGridGridBackInverterOff(condition);
	}

}
