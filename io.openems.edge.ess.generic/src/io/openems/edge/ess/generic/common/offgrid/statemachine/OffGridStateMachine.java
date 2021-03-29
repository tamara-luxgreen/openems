package io.openems.edge.ess.generic.common.offgrid.statemachine;

import com.google.common.base.CaseFormat;

import io.openems.common.types.OptionsEnum;
import io.openems.edge.common.statemachine.AbstractStateMachine;
import io.openems.edge.common.statemachine.StateHandler;

public class OffGridStateMachine extends AbstractStateMachine<OffGridStateMachine.OffGridState, OffGridContext> {

	public enum OffGridState implements io.openems.edge.common.statemachine.State<OffGridState>, OptionsEnum {

		UNDEFINED(-1), //

		START_BATTERY_IN_ON_GRID(10), //
		START_BATTERY_INVERTER_IN_ON_GRID(11), //
		STARTED_IN_ON_GRID(12), //

		START_BATTERY_IN_OFF_GRID(20), //
		START_BATTERY_INVERTER_IN_OFF_GRID(21), //
		STARTED_IN_OFF_GRID(22), //

		STOP_BATTERY_INVERTER(30), //
		STOP_BATTERY(31), //
		STOPPED(32), //

		STOP_BATTERY_INVERTER_BEFORE_SWITCH(40), //
		GRID_SWITCH(41), //

		ERROR(50), //
		; //

		private final int value;

		private OffGridState(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return this.value;
		}

		@Override
		public String getName() {
			return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name());
		}

		@Override
		public OptionsEnum getUndefined() {
			return UNDEFINED;
		}

		@Override
		public OffGridState[] getStates() {
			return OffGridState.values();
		}
	}

	public OffGridStateMachine(OffGridState initialState) {
		super(initialState);
	}

	@Override
	public StateHandler<OffGridState, OffGridContext> getStateHandler(OffGridState state) {
		switch (state) {
		case UNDEFINED:
			return new UndefinedHandler();
		case STARTED_IN_OFF_GRID:
			return new StartedInOffGridHandler();
		case STARTED_IN_ON_GRID:
			return new StartedInOnGridHandler();
		case START_BATTERY_INVERTER_IN_OFF_GRID:
			return new StartBatteryInverterInOffGridHandler();
		case START_BATTERY_INVERTER_IN_ON_GRID:
			return new StartBatteryInverterInOnGridHandler();
		case START_BATTERY_IN_OFF_GRID:
			return new StartBatteryInOffGridHandler();
		case START_BATTERY_IN_ON_GRID:
			return new StartBatteryInOnGridHandler();
		case STOPPED:
			return new StoppedHandler();
		case STOP_BATTERY:
			return new StopBatteryHandler();
		case STOP_BATTERY_INVERTER:
			return new StopBatteryInverterHandler();
		case STOP_BATTERY_INVERTER_BEFORE_SWITCH:
			return new StopBatteryInverterBeforeSwitchHandler();
		case ERROR:
			return new ErrorHandler();
		case GRID_SWITCH:
			return new GridSwitchHandler();
		}

		throw new IllegalArgumentException("Unknown State [" + state + "]");
	}

}
