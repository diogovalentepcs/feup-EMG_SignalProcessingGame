package com.game.src.main;

import java.awt.image.BufferedImage;

public class Textures {

	private SpriteSheet ss = null;
	
	public BufferedImage player[] = new BufferedImage[7];
	public BufferedImage bullet, enemy;
	
	public Textures(Game game) {
		 this.ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
	}
	
	private void getTextures(){

		player[0] = ss.grabImage(3, 50, 50);
		player[1] = ss.grabImage(4, 50, 50);
		player[2] = ss.grabImage(5, 50, 50);
		player[3] = ss.grabImage(6, 50, 50);
		player[4] = ss.grabImage(7, 50, 50);
		player[5] = ss.grabImage(8, 50, 50);
		player[6] = ss.grabImage(9, 50, 50);
		
		bullet = ss.grabImage(2, 50, 50);
		
		enemy = ss.grabImage(1, 50, 50);
		
	}
	
	
	
}
