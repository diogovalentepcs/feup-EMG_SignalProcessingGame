package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.main.entities.EntityA;
import com.game.src.main.entities.EntityB;

public class Enemy extends GameObject implements EntityB{
	
	private Textures tex;
	private Game game;
	private Controller c;
	private int skinIndex = 0;	
	private int changeDirectionCounter = 0;
	private boolean goLeft = false;
		
	Random r = new Random();
	
	private int speed = r.nextInt(3) + 1;

	public Enemy(double x, double y, Textures tex, Game game, Controller c) {
		super(x,y);
		this.tex = tex;
		this.game = game;
		this.c = c;
		
	}
	
	public void tick() {
		y += speed;
		
		if(changeDirectionCounter > 50) {
			goLeft = true;
		} else if (changeDirectionCounter < 0) {
			goLeft = false;
		}
		
		if(goLeft) {
			x -= speed;
			changeDirectionCounter -= 1;
		} else {
			x += speed;
			changeDirectionCounter += 1;
		}
		
		/*
		 * if (y > Game.HEIGHT * Game.SCALE) { 
		 * y = 0; 
		 * x = r.nextInt(Game.WIDTH * Game.SCALE); }
		 */
		
		for(int i = 0; i < game.ea.size(); i++) {
			EntityA tempEnt = game.ea.get(i);
		
			if(Physics.Collision(this, tempEnt)) {
				c.removeEntity(this);
				c.removeEntity(tempEnt);
				game.setEnemy_killcount(game.getEnemy_killcount() + 1);
				game.setEnemy_respawn(game.getEnemy_respawn() + 1);

			}
			
		}
		
		
	}
	
	public void render(Graphics g) {
		g.drawImage(this.tex.enemy, (int) x, (int) y, null);
	}
	
	public Rectangle getBounds(int width, int height) {
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	
	public double getY() {
		return y;
	}
	
	public void setY(double y ) {
		this.y = y;
	}
	
	public void setX(double x ) {
		this.x = x;
	}

	public double getX() {
		return x;
	}
}

