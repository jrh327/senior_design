package com.ultrateamgame.game2;

public enum Signal {
	FALSE(1), TRUE(2), NONE(3);
	private int value;
	
	private Signal(int value) {
		this.value = value;
	}
}
