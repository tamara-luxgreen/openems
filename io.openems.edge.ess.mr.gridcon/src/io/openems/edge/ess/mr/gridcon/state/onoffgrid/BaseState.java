package io.openems.edge.ess.mr.gridcon.state.onoffgrid;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.types.ChannelAddress;
import io.openems.edge.common.channel.BooleanWriteChannel;
import io.openems.edge.common.channel.Channel;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.ess.mr.gridcon.StateObject;
import io.openems.edge.meter.api.SymmetricMeter;

public abstract class BaseState implements StateObject {

//	private static final int VOLTAGE_GRID_MILLIVOLT = 200_000;
	public static final float ONOFF_GRID_VOLTAGE_FACTOR = 1;
	public static final float ONOFF_GRID_FREQUENCY_FACTOR_ONLY_ONGRID = 1.054f;

	private ComponentManager manager;
	private String outputSyncBridge;
	private String meterId;
	protected DecisionTableCondition condition;

	public BaseState(ComponentManager manager, DecisionTableCondition condition, String outputSyncBridge,
			String meterId) {
		this.manager = manager;
		this.outputSyncBridge = outputSyncBridge;
		this.meterId = meterId;
		this.condition = condition;
	}
	
//	@Override
//	public IState getNextState() {
//			if (DecisionTableHelper.isUndefined(condition)) {
//				return OnOffGridState.UNDEFINED;
//			}
//
//			if (DecisionTableHelper.isOnGridStart(condition)) {
//				return OnOffGridState.ON_GRID_START;
//			}
//
//			if (DecisionTableHelper.isOnGrid(condition)) {
//				return OnOffGridState.ON_GRID;
//			}
//			
//			if (DecisionTableHelper.isOffGridStart(condition)) {
//				return OnOffGridState.OFF_GRID_START;
//			}
//
//			if (DecisionTableHelper.isOffGrid(condition)) {
//				return OnOffGridState.OFF_GRID;
//			}
//			
//			if (DecisionTableHelper.isOffGridGridBack(condition)) {
//				return OnOffGridState.OFF_GRID_GRID_BACK;
//			}
//			
//			if (DecisionTableHelper.isOffGridAdjustParameter(condition)) {
//				return OnOffGridState.OFF_GRID_ADJUST_PARMETER;
//			}
//			
//			if (DecisionTableHelper.isOffGridGridBackRelaisDefect(condition)) {
//				return OnOffGridState.OFF_GRID_GRID_BACK_RELAIS_DEFECT;
//			}
//			
//			return OnOffGridState.UNDEFINED;
//	}



//	protected boolean isNextStateUndefined() { //TODO wozu?! eigtl müsste ich prüfen sind alle geräte da und sind die channel gesetzt...
//		return !isDigitalInputsDefined() || !isJanitzaCommunicationWorking();
//	}
//
//	private boolean isJanitzaCommunicationWorking() {
//		String modbusId = this.meterModbusBridgeId;
//		ComponentManager manager = this.manager;
//		AbstractModbusBridge modbusBridge = null;
//		try {
//			modbusBridge = manager.getComponent(modbusId);
//		} catch (OpenemsNamedException e) {
//			System.out.println("Cannot get modbus component");
//		}
//		if (modbusBridge == null) {
//			return false;
//		}
//
//		Channel<Boolean> slaveCommunicationFailedChannel = modbusBridge.getSlaveCommunicationFailedChannel();
//		Optional<Boolean> communicationFailedOpt = slaveCommunicationFailedChannel.value().asOptional();
//
//		if (!communicationFailedOpt.isPresent()) {
//			return false;
//		}
//		
//		
//		return !communicationFailedOpt.get();
//	}
//	
//	private boolean isDigitalInputsDefined() {
//		boolean defined = true;
//
//		defined = defined && isDigitalInputDefined(inputNA1);
//		defined = defined && isDigitalInputDefined(inputNA2);
//		defined = defined && isDigitalInputDefined(inputSyncBridge);
//
//		return defined;
//	}
//
//	private boolean isDigitalInputDefined(String inputAddress) {
//		boolean defined = false;
//		try {
//			BooleanReadChannel inputChannel = getBooleanReadChannel(inputAddress);
//			defined = isValueDefined(inputChannel.value());
//		} catch (Exception e) {
//			defined = false;
//		}
//		return defined;
//	}
//
//	private boolean isValueDefined(Value<Boolean> value) {
//		if (value != null) {
//			return value.isDefined();
//		}
//		return false;
//	}
//
//	private BooleanReadChannel getBooleanReadChannel(String channelAddress)
//			throws IllegalArgumentException, OpenemsNamedException {
//		return this.manager.getChannel(ChannelAddress.fromString(channelAddress));
//	}
//
//
//
//	protected boolean isNextStateOffGrid() {
//		if (isSystemOffgrid() && !isVoltageOnMeter()) {
//			return true;
//		}
//		return false;
//	}
//
//	protected boolean isNextStateGoingOnGrid() {
//		if (isSystemOffgrid() && isVoltageOnMeter()) {
//			return true;
//		}
//		return false;
//	}
//
//	private boolean isVoltageOnMeter() {
//		boolean ret = false;
//		try {
//			SymmetricMeter meter = manager.getComponent(meterId);
//			Channel<Integer> voltageChannel = meter.getVoltage();
//			int voltage = voltageChannel.value().orElse(0); // voltage is in mV
//			ret = voltage > BaseState.VOLTAGE_GRID_MILLIVOLT;
//		} catch (OpenemsNamedException e) {
//			ret = false;
//		}
//		return ret;
//	}

	protected float getVoltageOnMeter() {
		float ret = Float.MIN_VALUE;
		try {
			SymmetricMeter meter = manager.getComponent(meterId);
			Channel<Integer> voltageChannel = meter.getVoltage();
			int voltage = voltageChannel.value().orElse(0); // voltage is in mV
			ret = voltage / 1000.0f;
		} catch (OpenemsNamedException e) {
			System.out.println(e);
		}
		return ret;
	}

	protected float getFrequencyOnMeter() {
		float ret = Float.MIN_VALUE;
		try {
			SymmetricMeter meter = manager.getComponent(meterId);
			Channel<Integer> frequencyChannel = meter.getFrequency();
			int frequency = frequencyChannel.value().orElse(0); // voltage is in mV
			ret = frequency / 1000.0f;
		} catch (OpenemsNamedException e) {
			System.out.println(e);
		}
		return ret;
	}

	protected void setSyncBridge(boolean b) {
		System.out.println("setSyncBridge : parameter --> " + b);
		try {
			System.out.println("Try to write " + b + " to the sync bridge channel");
			System.out.println("output sync bridge address: " + outputSyncBridge);
			BooleanWriteChannel outputSyncDeviceBridgeChannel = this.manager
					.getChannel(ChannelAddress.fromString(outputSyncBridge));
			outputSyncDeviceBridgeChannel.setNextWriteValue(b);
		} catch (IllegalArgumentException | OpenemsNamedException e) {
			System.out.println("Error writing channel");
			System.out.println(e.getMessage());
		}
	}

//	protected boolean isSystemOngrid() {
//		if (isDigitalInputDefined(inputNA1) && isDigitalInputDefined(inputNA2)) {
//			try {
//				boolean na1Value = getBooleanReadChannel(inputNA1).value().get();
//				boolean na2Value = getBooleanReadChannel(inputNA2).value().get();
//
//				if (na1Inverted) {
//					na1Value = !na1Value;
//				}
//
//				if (na2Inverted) {
//					na2Value = !na2Value;
//				}
//
//				return na1Value && na2Value;
//			} catch (IllegalArgumentException | OpenemsNamedException e) {
//				return false;
//			}
//		}
//		return false;
//	}
//
//	protected boolean isSystemOffgrid() {
//		if (isDigitalInputDefined(inputNA1) && isDigitalInputDefined(inputNA2)) {
//			try {
//				boolean na1Value = getBooleanReadChannel(inputNA1).value().get();
//				boolean na2Value = getBooleanReadChannel(inputNA2).value().get();
//
//				if (na1Inverted) {
//					na1Value = !na1Value;
//				}
//
//				if (na2Inverted) {
//					na2Value = !na2Value;
//				}
//
//				return !na1Value || !na2Value;
//			} catch (IllegalArgumentException | OpenemsNamedException e) {
//				return false;
//			}
//		}
//		return false;
//	}

}
