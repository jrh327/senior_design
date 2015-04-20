package com.ultrateamgame.game2;

import com.badlogic.gdx.Gdx;
import com.ultrateamgame.game2.nodes.AndGate;
import com.ultrateamgame.game2.nodes.Battery;
import com.ultrateamgame.game2.nodes.Gate;
import com.ultrateamgame.game2.nodes.Lightbulb;
import com.ultrateamgame.game2.nodes.Node;
import com.ultrateamgame.game2.nodes.NandGate;
import com.ultrateamgame.game2.nodes.NorGate;
import com.ultrateamgame.game2.nodes.NotGate;
import com.ultrateamgame.game2.nodes.OrGate;
import com.ultrateamgame.game2.nodes.XorGate;

/*
 * level file format
 * 
 * availableGate1,availableGate2,...
 * numBatteries
 * battery,x,y
 * battery,x,y
 * ...
 * numLightbulbs
 * lightbulb,x,y
 * ...
 * numFixedGates
 * gate1,x,y
 * gate2,x,y
 * ...
 */

public class LevelLoader {
	public static void loadLevel(int level, Board board, Inventory inven) {
		int w = Constants.SCREEN_WIDTH / Constants.ENTITY_WIDTH;
		int h = Constants.SCREEN_HEIGHT / Constants.ENTITY_HEIGHT;
		Node[][] b = new Node[h][w];
		
		String lvl = Gdx.files.internal("levels/level" + level + ".txt").readString();
		String[] nodes = lvl.split("\n");
		
		String[] available = nodes[0].trim().split(",");
		Gate[] availableGates = new Gate[available.length];
		for (int i = 0; i < available.length; i++) {
			availableGates[i] = (Gate)stringToNode(available[i]);
		}
		inven.setGates(availableGates);
		
		int numBatteries = Integer.valueOf(nodes[1].trim());
		Battery[] batteries = new Battery[numBatteries];
		
		int lineCounter = 2;
		for (int i = 0; i < numBatteries; i++) {
			String[] info = nodes[lineCounter].trim().split(",");
			lineCounter++;
			Battery battery = (Battery)stringToNode(info[0]);
			int x = Integer.valueOf(info[1]);
			int y = Integer.valueOf(info[2]);
			
			battery.setXPos(x);
			battery.setYPos(y);
			batteries[i] = battery;
			b[y][x] = battery;
		}
		
		int numLightbulbs = Integer.valueOf(nodes[lineCounter].trim());
		Lightbulb[] lightbulbs = new Lightbulb[numLightbulbs];
		
		lineCounter++;
		for (int i = 0; i < numLightbulbs; i++) {
			String[] info = nodes[lineCounter].trim().split(",");
			lineCounter++;
			Lightbulb lightbulb = (Lightbulb)stringToNode(info[0]);
			int x = Integer.valueOf(info[1]);
			int y = Integer.valueOf(info[2]);
			
			lightbulb.setXPos(x);
			lightbulb.setYPos(y);
			lightbulbs[i] = lightbulb;
			b[y][x] = lightbulb;
		}
		
		int numFixedGates = Integer.valueOf(nodes[lineCounter].trim());
		Gate[] fixedGates = new Gate[numFixedGates];
		
		lineCounter++;
		for (int i = 0; i < numFixedGates; i++) {
			String[] info = nodes[lineCounter].trim().split(",");
			lineCounter++;
			Gate gate = (Gate)stringToNode(info[0]);
			int x = Integer.valueOf(info[1]);
			int y = Integer.valueOf(info[2]);
			
			gate.setXPos(x);
			gate.setYPos(y);
			gate.setFixed();
			fixedGates[i] = gate;
			b[y][x] = gate;
		}
		
		board.setBoard(b);
		board.setBatteries(batteries);
		board.setLightbulbs(lightbulbs);
		board.setFixedGates(fixedGates);
	}
	
	private static Node stringToNode(String node) {
		if ("and".equals(node)) {
			return new AndGate();
		} else if ("or".equals(node)) {
			return new OrGate();
		} else if ("not".equals(node)) {
			return new NotGate();
		} else if ("nand".equals(node)) {
			return new NandGate();
		} else if ("nor".equals(node)) {
			return new NorGate();
		} else if ("xor".equals(node)) {
			return new XorGate();
		} else if ("battery".equals(node)) {
			return new Battery();
		} else if ("lightbulb".equals(node)) {
			return new Lightbulb();
		} else {
			return new AndGate();
		}
	}
}
