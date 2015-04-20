package com.ultrateamgame.game2.nodes;

import com.ultrateamgame.game2.Constants;
import com.ultrateamgame.game2.Signal;

public class NotGate extends UnaryGate {
	
	@Override
	protected void resetOutput() {
		if (input != null) {
			if (input.output() == Signal.FALSE) {
				value = Signal.TRUE;
			} else if (input.output() == Signal.TRUE) {
				value = Signal.FALSE;
			} else {
				value = Signal.NONE;
			}
		} else {
			value = Signal.NONE;
		}
	}
	
	@Override
	public String toString() {
		if (fixed) {
			return Constants.NOT_GATE_FIXED;
		} else {
			return Constants.NOT_GATE;
		}
	}
	
	public Gate newGate() {
		return new NotGate();
	}
}
