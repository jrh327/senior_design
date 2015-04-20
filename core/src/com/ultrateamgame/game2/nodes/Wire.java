package com.ultrateamgame.game2.nodes;

import com.ultrateamgame.game2.Constants;
import com.ultrateamgame.game2.Signal;

public class Wire extends Node {
	private Node input;
	private Node output;
	
	public Wire(Node input, Node output) {
		this.input = input;
		this.output = output;
	}
	
	public void setInput(Node input) {
		this.input = input;
	}
	
	public void setOutput(Node output) {
		this.output = output;
	}
	
	public Node getInput() {
		return input;
	}
	
	public Node getOutput() {
		return output;
	}
	
	@Override
	public Signal output() {
		return input.output();
	}
	
	@Override
	public String toString() {
		Signal o = input.output();
		if (o == Signal.NONE) {
			return Constants.WIRE_NONE;
		} else if (o == Signal.FALSE) {
			return Constants.WIRE_FALSE;
		} else if (o == Signal.TRUE) {
			return Constants.WIRE_TRUE;
		} else {
			return Constants.WIRE_NONE;
		}
	}
}
