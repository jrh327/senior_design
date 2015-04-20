package com.ultrateamgame.game2;

import java.util.ArrayList;

import com.ultrateamgame.game2.nodes.Battery;
import com.ultrateamgame.game2.nodes.BinaryGate;
import com.ultrateamgame.game2.nodes.Gate;
import com.ultrateamgame.game2.nodes.Lightbulb;
import com.ultrateamgame.game2.nodes.Node;
import com.ultrateamgame.game2.nodes.UnaryGate;

public class Board {

	private Node[][] board;
	public Lightbulb[] lightbulbs;
	public Battery[] batteries;
	public Gate[] fixedGates;
	public ArrayList<Gate> userGates;
	
	private int selectedX, selectedY;
	private Node selectedNode;
	private Node selectedOutput;
	private Node selectedInput1;
	private Node selectedInput2;
	
	private Gate selectedGate;
	
	private static Board instance = null;
	
	private Board() {
		userGates = new ArrayList<Gate>(10);
	}
	
	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
	public void setBoard(Node[][] board) {
		this.board = board;
	}
	
	public final Node[][] getBoard() {
		return board;
	}
	
	public void setBatteries(Battery[] batteries) {
		this.batteries = batteries;
	}
	
	public void setLightbulbs(Lightbulb[] lightbulbs) {
		this.lightbulbs = lightbulbs;
	}
	
	public void setFixedGates(Gate[] fixedGates) {
		this.fixedGates = fixedGates;
	}
	
	public Node getSelectedNode() {
		return selectedNode;
	}
	
	public void setSelectedGate(Gate gate) {
		this.selectedGate = gate;
	}
	
	public void interpretClick(final int x, final int y) {
		int boardElementX = x / Constants.ENTITY_WIDTH;
		int boardElementY = y / Constants.ENTITY_HEIGHT;
		
		Node node = board[boardElementY][boardElementX];
		
		if (placeGate(x, y)) {
			return;
		}
		
		selectedNode = node;
		selectedX = boardElementX;
		selectedY = boardElementY;
		
		if (node == selectedOutput) {
			selectedOutput = null;
			selectedX = -1;
			selectedY = -1;
			return;
		}
		
		if (node == selectedInput1) {
			selectedInput1 = null;
			return;
		}
		
		if (node == selectedInput2) {
			selectedInput2 = null;
			return;
		}
		
		if (node instanceof Battery) {
			selectedOutput = node;
		} else if (node instanceof Lightbulb) {
			selectedInput1 = node;
		} else {
			int eleX = x % Constants.ENTITY_WIDTH;
			int eleY = y % Constants.ENTITY_HEIGHT;
			
			if (eleX > Constants.ENTITY_WIDTH / 2) {
				selectedOutput = node;
			} else {
				if (node instanceof UnaryGate) {
					selectedInput1 = node;
				} else {
					if (eleY < Constants.ENTITY_HEIGHT / 2) {
						selectedInput1 = node;
						selectedInput2 = null;
					} else {
						selectedInput2 = node;
						selectedInput1 = null;
					}
				}
			}
		}
		
		if (selectedOutput != null) {
			if (selectedInput1 != null) {
				if (selectedInput1 instanceof Lightbulb) {
					((Lightbulb)selectedInput1).setInput(selectedOutput);
				} else {
					if (selectedInput1 instanceof BinaryGate) {
						((BinaryGate)selectedInput1).setInput1(selectedOutput);
					} else {
						((UnaryGate)selectedInput1).setInput(selectedOutput);
					}
				}
				selectedOutput = null;
				selectedInput1 = null;
				selectedInput2 = null;
				selectedNode = null;
			} else if (selectedInput2 != null) {
				((BinaryGate)selectedInput2).setInput2(selectedOutput);
				selectedOutput = null;
				selectedInput1 = null;
				selectedInput2 = null;
				selectedNode = null;
			}
		}
	}
	
	public boolean checkForWin() {
		for (int i = 0; i < lightbulbs.length; i++) {
			if (lightbulbs[i].output() != Signal.TRUE) {
				return false;
			}
		}
		
		// check if any gates aren't even connected to the circuit
		for (int i = 0; i < fixedGates.length; i++) {
			if (fixedGates[i].output() == Signal.NONE) {
				return false;
			}
		}
		
		boolean[] usedGates = new boolean[fixedGates.length];
		for (int i = 0; i < batteries.length; i++) {
			Node output = batteries[i].getOutputNode();
			while (output != null && !(output instanceof Lightbulb)) {
				
				// loop through and mark any gates this battery goes through
				// might be able to reduce this to only extending from the
				// fixed gates, but need to prove that all gates used implies
				// all batteries are used before doing that
				for (int j = 0; j < fixedGates.length; j++) {
					if (output == fixedGates[j]) {
						usedGates[j] = true;
					}
				}
				output = output.getOutputNode();
			}
			if (output == null) { // need all batteries to reach lightbulb
				return false;
			}
		}
		
		for (int i = 0; i < usedGates.length; i++) {
			if (!usedGates[i]) {
				return false;
			}
		}
		return true;
	}
	
	public void deleteNode() {
		
		// only allow user-placed gates to be deleted
		if (selectedNode != null && !selectedNode.isFixed()) {
			if (selectedNode instanceof Gate) {
				
				// make sure coordinates are valid
				if (selectedY < 0 || selectedY >= board.length) {
					return;
				}
				if (selectedX < 0 || selectedX >= board[0].length) {
					return;
				}
				
				((Gate)selectedNode).disconnect();
				
				board[selectedY][selectedX] = null;
				userGates.remove(selectedNode);
				selectedX = -1;
				selectedY = -1;
				selectedNode = null;
				selectedOutput = null;
				selectedInput1 = null;
				selectedInput2 = null;
			}
		}
	}
	
	public boolean placeGate(int x, int y) {
		int boardElementX = x / Constants.ENTITY_WIDTH;
		int boardElementY = y / Constants.ENTITY_HEIGHT;
		Node node = board[boardElementY][boardElementX];
		
		if (node == null) {
			if (selectedGate != null) {
				selectedGate.setXPos(boardElementX);
				selectedGate.setYPos(boardElementY);
				board[boardElementY][boardElementX] = selectedGate;
				userGates.add(selectedGate);
				selectedGate = null;
			}
			return true;
		}
		
		return false;
	}
}
