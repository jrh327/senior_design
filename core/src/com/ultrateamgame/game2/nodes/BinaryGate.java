package com.ultrateamgame.game2.nodes;

public abstract class BinaryGate extends Gate {
	protected Node input1;
	protected Node input2;
	
	public void setInput1(Node n) {
		if (input1 != null) {
			input1.deleteOutputNode(this);
		}
		
		input1 = n;
		resetOutput();
		n.setOutputNode(this);
	}
	
	public void setInput2(Node n) {
		if (input2 != null) {
			input2.deleteOutputNode(this);
		}
		
		input2 = n;
		resetOutput();
		n.setOutputNode(this);
	}
	
	@Override
	public void disconnect() {
		if (input1 != null) {
			input1.deleteOutputNode(this);
		}
		if (input2 != null) {
			input2.deleteOutputNode(this);
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
		if (n == input1) {
			input1 = null;
			resetOutput();
		} else if (n == input2) {
			input2 = null;
			resetOutput();
		}
	}
}
