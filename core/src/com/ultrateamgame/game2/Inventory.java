package com.ultrateamgame.game2;

import com.ultrateamgame.game2.nodes.Gate;

public class Inventory {
	private int yPos;
	private int width;
	private int openWidth;
	private int gateWidth;
	private Gate[] gates;
	private boolean isOpen;
	
	private static Inventory instance = null;
	
	private Inventory() {
		this.isOpen = true;
	}
	
	public static Inventory getInstance() {
		if (instance == null) {
			instance = new Inventory();
		}
		return instance;
	}
	
	public void setParameters(int y, int width, int openWidth, int gateWidth) {
		this.yPos = y;
		this.width = width;
		this.openWidth = openWidth;
		this.gateWidth = gateWidth;
	}
	
	public void setGates(Gate[] gates) {
		this.gates = gates;
	}
	
	public final Gate[] getGates() {
		return gates;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public boolean clickedInInventory(int x, int y) {
		if (y < yPos) {
			return false;
		}
		
		if (isOpen) {
			return x <= openWidth;
		} else {
			return x <= width;
		}
	}
	
	public Gate interpretClick(int x, int y) {
		if (x <= width && y >= yPos) {
			isOpen = !isOpen;
			
			if (isOpen) {
				width = Constants.BACKPACK_OPEN_WIDTH;
			} else {
				width = Constants.BACKPACK_CLOSED_WIDTH;
			}
			
			return null;
		}
		
		if (!isOpen) {
			return null;
		}
		
		x -= (width + Constants.INVENTORY_BUFFER);
		
		int gateNum = x / (gateWidth + Constants.INVENTORY_BUFFER);
		
		if (gateNum < gates.length) {
			return gates[gateNum];
		}
		
		return null;
	}
}
