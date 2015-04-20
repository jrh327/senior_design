package com.ultrateamgame.game2;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ultrateamgame.game2.nodes.Battery;
import com.ultrateamgame.game2.nodes.BinaryGate;
import com.ultrateamgame.game2.nodes.Gate;
import com.ultrateamgame.game2.nodes.Lightbulb;
import com.ultrateamgame.game2.nodes.Node;
import com.ultrateamgame.game2.nodes.UnaryGate;

public class LogicGates extends ApplicationAdapter {
	private SpriteBatch batch;
	private ResourceController rc;
	private int level;
	private Board board;
	
	private Inventory inven;
	private int invenYPos;
	private Gate selectedGate;
	
	private boolean helpMenuOpen = false;
	private boolean levelWon = false;
	
	private Texture rect;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		rc = ResourceController.getInstance();
		rect = new Texture(Gdx.files.internal("textures/rect.png"));
		
		level = 1;
		board = Board.getInstance();
		invenYPos = Constants.SCREEN_HEIGHT - Constants.BACKPACK_HEIGHT;
		inven = Inventory.getInstance();
		inven.setParameters(invenYPos, Constants.BACKPACK_CLOSED_WIDTH,
				Constants.SCREEN_WIDTH, Constants.ENTITY_WIDTH);
		
		Logging.log(Logging.SMART_MOVE);
		
		LevelLoader.loadLevel(level, board, inven);
		
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		interpretClick();
		interpretKeypress();
		redrawBoard();
		
		if (board.checkForWin()) {
			System.out.println("You won! but you'll never see this message");
			batch.draw(ResourceController.getInstance().getImage(Constants.LEVEL_WIN), 0, 0);
			levelWon = true;
		}
		
		batch.end();
	}
	
	public void redrawBoard() {
		int w = Constants.ENTITY_WIDTH;
		int h = Constants.ENTITY_HEIGHT;
		
		if (helpMenuOpen) {
			batch.draw(rc.getImage(Constants.HELP_MENU), 0, 0);
			return;
		}
		
		Node[][] b = board.getBoard();
		Node selectedNode = board.getSelectedNode();
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[i].length; j++) {
				Node n = b[i][j];
				batch.draw(rc.getImage(Constants.EMPTY_BOARD), j * w, Constants.SCREEN_HEIGHT - i * h - Constants.ENTITY_HEIGHT);
				if (n != null) {
					if (n == selectedNode) {
						batch.draw(rect, j * w, Constants.SCREEN_HEIGHT - i * h - Constants.ENTITY_HEIGHT, Constants.ENTITY_WIDTH, Constants.ENTITY_HEIGHT);
					}
					batch.draw(rc.getImage(n.toString()), j * w, Constants.SCREEN_HEIGHT - i * h - Constants.ENTITY_HEIGHT);
				}
			}
		}
		
		//*
		// draw wires between nodes
		for (int i = 0; i < board.batteries.length; i++) {
			drawWire(board.batteries[i], board.batteries[i].getOutputNode());
		}
		
		for (int i = 0; i < board.fixedGates.length; i++) {
			drawWire(board.fixedGates[i], board.fixedGates[i].getOutputNode());
		}
		
		for (int i = 0; i < board.userGates.size(); i++) {
			drawWire(board.userGates.get(i), board.userGates.get(i).getOutputNode());
		}
		
		/*/ loop through again and add any necessary effects
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[i].length; j++) {
				Node n = b[i][j];
				if (n != null) {
					batch.setColor(new Color(128, 128, 0, 150));
					int glowDiameter = w / 2;
					int glowX = 0;
					int glowY = 0;
					
					if (n == selectedInput1) {
						if (n instanceof BinaryGate) {
							glowX = j * w - glowDiameter / 2;
							glowY = i * h;
						} else if (n instanceof UnaryGate) {
							glowX = j * w - glowDiameter / 2;
							glowY = i * h + ((h - glowDiameter) / 2);
						} else if (n instanceof Lightbulb) {
							glowX = j * w + ((w - glowDiameter) / 2);
							glowY = i * h + (h - glowDiameter / 2);
						}
						bufferGraphics.fillOval(glowX, glowY, glowDiameter, glowDiameter);
					} else if (n == selectedInput2) {
						if (n instanceof BinaryGate) {
							glowX = j * w - glowDiameter / 2;
							glowY = i * h + (h / 2);
						}
						bufferGraphics.fillOval(glowX, glowY, glowDiameter, glowDiameter);
					} else if (n == selectedOutput) {
						if (n instanceof Gate) {
							glowX = j * w + (w - glowDiameter / 2);
							glowY = i * h + ((h - glowDiameter) / 2);
						} else if (n instanceof Battery) {
							glowX = j * w + ((w - glowDiameter) / 2);
							glowY = i * h - glowDiameter / 2;
						}
						bufferGraphics.fillOval(glowX, glowY, glowDiameter, glowDiameter);
					}
				}
			}
		}
		//*/
		
		int backpackWidth = (inven.isOpen() ? Constants.BACKPACK_OPEN_WIDTH : Constants.BACKPACK_CLOSED_WIDTH);
		int backpackHeight = Constants.BACKPACK_HEIGHT;
		int gateWidth = Constants.ENTITY_WIDTH;
		int gateHeight = Constants.ENTITY_HEIGHT;
		
		//batch.setColor(Color.GRAY);
		if (inven.isOpen()) {
			fillRect(0, 0, Constants.SCREEN_WIDTH, backpackHeight);
			batch.draw(rc.getImage(Constants.BACKPACK_OPEN), 0, 0);
			Gate[] gates = inven.getGates();
			int gateYPos = (backpackHeight / 2 - gateHeight / 2); // to center in inventory
			for (int i = 0; i < gates.length; i++) {
				int buffer = Constants.INVENTORY_BUFFER;
				int gateXPos = backpackWidth + gateWidth * i + buffer * i + buffer;
				batch.draw(rc.getImage(gates[i].toString()), gateXPos, gateYPos);
			}
			
			int helpLeft = Constants.SCREEN_WIDTH - Constants.ENTITY_WIDTH * 2;
			int helpTop = Constants.SCREEN_HEIGHT - Constants.BACKPACK_HEIGHT;
			
			if (prevX > helpLeft && prevY > helpTop) {
				batch.draw(rc.getImage(Constants.HELP_MENU_HOVER), helpLeft - 10, Constants.ENTITY_HEIGHT / 2,
						Constants.ENTITY_WIDTH * 2, Constants.ENTITY_HEIGHT);
			} else {
				batch.draw(rc.getImage(Constants.HELP_MENU_NORMAL), helpLeft - 10, Constants.ENTITY_HEIGHT / 2,
						Constants.ENTITY_WIDTH * 2, Constants.ENTITY_HEIGHT);
			}
		} else {
			//bufferGraphics.fillRect(0, invenYPos, backpackWidth, backpackHeight);
			batch.draw(rc.getImage(Constants.BACKPACK_CLOSED), 0, 0);
			selectedGate = null;
			board.setSelectedGate(null);
		}
		//*/
		
		if (dragging) {
			if (selectedGate != null) {
				batch.draw(rc.getImage(selectedGate.toString()), prevX - Constants.ENTITY_WIDTH / 2,
						Constants.SCREEN_HEIGHT - prevY - Constants.ENTITY_HEIGHT / 2);
			}
		}
	}
	
	public void drawWire(Node n1, Node n2) {
		if (n1 == null || n2 == null) {
			return;
		}
		
		int xNode1 = n1.getXPos();
		int xNode2 = n2.getXPos();
		int yNode1 = n1.getYPos();
		int yNode2 = n2.getYPos();
		int dx = Math.abs(xNode1 - xNode2);
		int dy = Math.abs(yNode1 - yNode2);
		int x1, x2, x3, x4;
		int y1, y2, y3, y4;
		
		int offsetX = Constants.ENTITY_WIDTH;
		int offsetY = Constants.ENTITY_HEIGHT;
		
		x1 = xNode1 * offsetX;
		y1 = yNode1 * offsetY;
		if (n1 instanceof Battery) {
			x1 += offsetX / 2; // output of battery in middle of top
		} else {
			x1 += offsetX; // output of gates is in middle of right side
			y1 += offsetY / 2;
		}
		
		x4 = xNode2 * offsetX;
		y4 = yNode2 * offsetY;
		if (n2 instanceof UnaryGate) {
			y4 += offsetY / 2; // input of unary gate is in the middle
		} else if (n2 instanceof Lightbulb) {
			x4 += offsetX / 2; // input of lightbulb in middle of bottom
			y4 += offsetY;
		} else {
			// needs to be if node1 is input1 or input2
			if (yNode1 > yNode2) {
				y4 += offsetY;
			}
		}
		
		if (dx < dy) {
			x2 = x1;
			y2 = y1 + dy * offsetY / 2;
			x3 = x4;
			y3 = y2;
		} else {
			x2 = x1 + dx * offsetY / 2;
			y2 = y1;
			x3 = x2;
			y3 = y4;
		}
		
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(x1, y1));
		points.add(new Point(x2, y2));
		points.add(new Point(x3, y3));
		points.add(new Point(x4, y4));
		
		for (int i = 0; i < points.size() - 1; i++) {
			Point p1 = points.get(i);
			Point p2 = points.get(i + 1);
			drawLine(p1.x, p1.y, p2.x, p2.y, 2);
		}
	}
	
	public void interpretKeypress() {
		// check if pressed delete or backspace
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
		//if (Gdx.input.isKeyPressed(Input.Keys.DEL) || Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
		//if (Gdx.input.isKeyPressed(46) || Gdx.input.isKeyPressed(8)) {
			board.deleteNode();
		}
	}
	
	private boolean mouseDown = false;
	private boolean dragging = false;
	private int prevX = 0, prevY = 0;
	private void interpretClick() {
		
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		
		if (mouseDown && prevX != x || prevY != y) {
			dragging = true;
		}
		
		prevX = x;
		prevY = y;
		
		if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (mouseDown && dragging) {
				if (selectedGate != null) {
					if (!inven.isOpen() || prevY < Constants.SCREEN_HEIGHT - Constants.BACKPACK_HEIGHT) {
						board.placeGate(prevX, prevY);
					}
					selectedGate = null;
				}
			}
			mouseDown = false; // no longer pressing the mouse
			dragging = false;
			return;
	    }
		
		if (mouseDown) {
			return;
		}
		
		mouseDown = true;
		
		if (levelWon) {
			if (x > 395 && x < 484 && y > 274 && y < 324) {
				Logging.log(level, 0);
				
				if (level == 1) {
					Logging.log(Logging.TRUE_FALSE);
				} else if (level == 5) {
					Logging.log(Logging.GETTING_STARTED);
				} else if (level == 10) {
					Logging.log(Logging.LOGIC_CAPTAIN);
				} else if (level == 15) {
					Logging.log(Logging.COMPUTATIONAL_WIZARD);
				}
				
				levelWon = false;
				level++;
				LevelLoader.loadLevel(level, board, inven);
				
			}
			return;
		}
		
		if (helpMenuOpen) {
			helpMenuOpen = false;
			return;
		}
		
		if (inven.clickedInInventory(x, y)) {
			boolean isOpen = inven.isOpen();
			Gate g = inven.interpretClick(x, y);
			
			if (!helpMenuOpen) {
				if (x > Constants.SCREEN_WIDTH - Constants.ENTITY_WIDTH * 2) {
					helpMenuOpen = true;
					return;
				}
			}
			
			if (g != null) {
				selectedGate = g.newGate();
			} else if (isOpen == inven.isOpen()) {
				// clicked empty area of inventory but didn't close it
				selectedGate = null;
			}
			board.setSelectedGate(selectedGate);
			return;
		}
		
		board.interpretClick(x, y);
		
		return;
	}
	
	private void fillRect(int x, int y, int width, int height) {
		batch.draw(rect, x, y, width, height);
	}
	
	private void drawRect(int x, int y, int width, int height, int thickness) {
		batch.draw(rect, x, y, width, thickness);
		batch.draw(rect, x, y, thickness, height);
		batch.draw(rect, x, y + height - thickness, width, thickness);
		batch.draw(rect, x + width - thickness, y, thickness, height);
	}
	
	private void drawLine(int x1, int y1, int x2, int y2, int thickness) {
		int tmp;
		if (x1 < x2) {
			tmp = x2;
			x2 = x1;
			x1 = tmp;
		}
		
		int dx = x2 - x1;
		int dy = y2 - y1;
		
		if (x1 == x2) {
			dy = -dy;
		}
		
		batch.draw(rect, x1, Constants.SCREEN_HEIGHT - y1 - thickness, dx + thickness, dy + thickness);
	}
}
