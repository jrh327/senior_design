package com.ultrateamgame.game2.nodes;

public abstract class UnaryGate extends Gate {
	protected Node input;
	
	public void setInput(Node n) {
		if (input != null) {
			input.deleteOutputNode(this);
		}
		
		input = n;
		resetOutput();
		n.setOutputNode(this);
	}
	
	@Override
	public void disconnect() {
		if (input != null) {
			input.deleteOutputNode(this);
		}
		if (outputNode != null) {
			if (outputNode instanceof Gate) {
				((Gate)outputNode).deleteInput(this);
			} else if (outputNode instanceof Lightbulb) {
				((Lightbulb)outputNode).deleteInput(this);
			}
		}
	}
	
	@Override
	public void deleteInput(Node n) {
		if (n == input) {
			input = null;
			resetOutput();
		}
	}
}
