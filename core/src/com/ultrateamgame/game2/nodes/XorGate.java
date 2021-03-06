package com.ultrateamgame.game2.nodes;

import com.ultrateamgame.game2.Constants;
import com.ultrateamgame.game2.Signal;

public class XorGate extends BinaryGate {
	
	@Override
	protected void resetOutput() {
		if (input1 != null && input2 != null) {
			if (input1.output() == Signal.TRUE && input2.output() == Signal.FALSE) {
				value = Signal.TRUE;
			} else if (input1.output() == Signal.FALSE && input2.output() == Signal.TRUE) {
				value = Signal.TRUE;
			} else if (input1.output() != Signal.NONE && input2.output() != Signal.NONE) {
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
			return Constants.XOR_GATE_FIXED;
		} else {
			return Constants.XOR_GATE;
		}
	}
	
	public Gate newGate() {
		return new XorGate();
	}
}
