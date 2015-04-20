package com.ultrateamgame.game2.nodes;

import com.ultrateamgame.game2.Constants;
import com.ultrateamgame.game2.Signal;

public class Lightbulb extends Node {
	private Node input;
	
	public Lightbulb() {
		fixed = true;
	}
	
	public void setInput(Node n) {
		if (input != null) {
			input.deleteOutputNode(this);
		}
		
		input = n;
		n.setOutputNode(this);
	}
	
	public void deleteInput(Node n) {
		if (n == input) {
			input = null;
		}
	}
	
	@Override
	public Signal output() {
		if (input == null) {
			return Signal.NONE;
		}
		return input.output();
	}
	
	@Override
	public String toString() {
		if (input != null && input.output() == Signal.TRUE) {
			return Constants.LIGHTBULB_ON;
		} else {
			return Constants.LIGHTBULB_OFF;
		}
	}
}
