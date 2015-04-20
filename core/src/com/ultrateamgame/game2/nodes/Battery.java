package com.ultrateamgame.game2.nodes;

import com.ultrateamgame.game2.Constants;
import com.ultrateamgame.game2.Signal;

public class Battery extends Node {
	
	public Battery() {
		fixed = true;
	}
	
	@Override
	public Signal output() {
		return Signal.TRUE;
	}
	
	@Override
	public String toString() {
		return Constants.BATTERY;
	}
}
