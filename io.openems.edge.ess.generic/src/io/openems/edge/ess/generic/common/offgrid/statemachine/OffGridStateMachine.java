package io.openems.edge.ess.generic.common.offgrid.statemachine;

import com.google.common.base.CaseFormat;

import io.openems.common.types.OptionsEnum;
import io.openems.edge.common.statemachine.AbstractStateMachine;
import io.openems.edge.common.statemachine.StateHandler;

public class OffGridStateMachine extends AbstractStateMachine<OffGridStateMachine.OffGridState, OffGridContext> {

	public enum OffGridState implements io.openems.edge.common.statemachine.State<OffGridState>, OptionsEnum {

		UNDEFINED(-1), //

		TOTAL_ONGRID(0), //
		
		ERROR(1), //
		GROUNDSET(2), //		

		TOTAL_OFFGRID(3) //
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

		case TOTAL_ONGRID:
			return new TotalOnGridHandler();
			
		case TOTAL_OFFGRID:
			return new TotalOffGridHandler();
			
		case UNDEFINED:
			return new UndefinedHandler();
		case ERROR:
			return new ErrorHandler();
		case GROUNDSET:
			return new GroundSetHandler();
		}

		throw new IllegalArgumentException("Unknown State [" + state + "]");
	}

}
