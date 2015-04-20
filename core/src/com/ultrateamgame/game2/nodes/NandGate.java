package com.ultrateamgame.game2.nodes;

import com.ultrateamgame.game2.Constants;
import com.ultrateamgame.game2.Signal;

public class NandGate extends BinaryGate {
	
	@Override
	protected void resetOutput() {
		if (input1 != null && input2 != null) {
			if (input1.output() == Signal.TRUE && input2.output() == Signal.TRUE) {
				value = Signal.FALSE;
			} else if (input1.output() != Signal.NONE && input2.output() != Signal.NONE) {
				value = Signal.TRUE;
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
			return Constants.NAND_GATE_FIXED;
		} else {
			return Constants.NAND_GATE;
		}
	}
	
	public Gate newGate() {
		return new NandGate();
	}
}
