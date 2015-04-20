package com.ultrateamgame.game2.nodes;

import com.ultrateamgame.game2.Signal;

public abstract class Node {
	protected boolean fixed = false;
	protected Node outputNode;
	protected int xPos;
	protected int yPos;
	
	public void setFixed() {
		fixed = true;
	}
	
	public boolean isFixed() {
		return fixed;
	}
	
	public Node getOutputNode() {
		return outputNode;
	}
	
	public void setOutputNode(Node node) {
		if (outputNode != null) {
			if (outputNode instanceof Gate) {
				((Gate)outputNode).deleteInput(this);
			} else if (outputNode instanceof Lightbulb) {
				((Lightbulb)outputNode).deleteInput(this);
			}
		}
		
		outputNode = node;
	}
	
	public void deleteOutputNode(Node node) {
		if (outputNode == node) {
			outputNode = null;
		}
	}
	
	public void setXPos(int x) {
		xPos = x;
	}
	
	public void setYPos(int y) {
		yPos = y;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public abstract Signal output();
	
	@Override
	public abstract String toString();
}
