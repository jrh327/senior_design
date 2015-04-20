
package com.ultrateamgame.game2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class ResourceController {
	private static ResourceController instance;
	private Map<String, Texture> images;
	
	private ResourceController() {
		init_images();
	}
	
	public static ResourceController getInstance() {
		if (instance == null) instance = new ResourceController();
		return instance;
	}
	
	private void init_images() {
		images = new HashMap<String, Texture>();
		loadImage(Constants.EMPTY_BOARD);
		loadImage(Constants.AND_GATE);
		loadImage(Constants.AND_GATE_FIXED);
		loadImage(Constants.OR_GATE);
		loadImage(Constants.OR_GATE_FIXED);
		loadImage(Constants.NOT_GATE);
		loadImage(Constants.NOT_GATE_FIXED);
		loadImage(Constants.NAND_GATE);
		loadImage(Constants.NAND_GATE_FIXED);
		loadImage(Constants.NOR_GATE);
		loadImage(Constants.NOR_GATE_FIXED);
		loadImage(Constants.XOR_GATE);
		loadImage(Constants.XOR_GATE_FIXED);
		loadImage(Constants.BATTERY);
		loadImage(Constants.LIGHTBULB_OFF);
		loadImage(Constants.LIGHTBULB_ON);
		//loadImage(Constants.WIRE_NONE_HORIZ);
		//loadImage(Constants.WIRE_FALSE_HORIZ);
		//loadImage(Constants.WIRE_TRUE_HORIZ);
		//loadImage(Constants.WIRE_NONE_VERT);
		//loadImage(Constants.WIRE_FALSE_VERT);
		//loadImage(Constants.WIRE_TRUE_VERT);
		loadImage(Constants.BACKPACK_CLOSED);
		loadImage(Constants.BACKPACK_OPEN);
		loadImage(Constants.HELP_MENU);
		loadImage(Constants.HELP_MENU_HOVER);
		loadImage(Constants.HELP_MENU_NORMAL);
		loadImage(Constants.LEVEL_WIN);
	}
	
	private void loadImage(String imgName) {
		Texture img = new Texture(Gdx.files.internal("textures/" + imgName + ".png"));
		images.put(imgName, img);
	}
	
	public Texture getImage(String entity) {
		return images.get(entity);
	}
}
