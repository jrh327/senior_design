package com.ultrateamgame.game2.nodes;

import com.ultrateamgame.game2.Signal;

public abstract class Gate extends Node {
	protected Signal value;
	
	@Override
	public Signal output() {
		return value;
	}
	
	public abstract void disconnect();
	public abstract void deleteInput(Node n);
	protected abstract void resetOutput();
	public abstract Gate newGate();
}
