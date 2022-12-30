package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.Game.STATE;
import com.game.src.main.entities.EntityA;
import com.game.src.main.entities.EntityB;

public class Player extends GameObject implements EntityA{
	
	private double velX = 0;
	private double velY = 0;
	
	private Textures tex;
	
	public int skinIndex = 0;	
	
	Game game;
	Controller c;
	
	public int health = 100 * 2;

	
	public Player(double x, double y, Textures tex, Game game, Controller c) {
		super(x,y);
		this.tex = tex;
		this.game = game;
		this.c = c;
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		if(x<=0)
			x=0;
		if(x>=game.WIDTH * game.SCALE-50)
			x=game.WIDTH * game.SCALE-50;
		if(y<=0)
			y=0;
		if(y>=game.HEIGHT * game.SCALE -50)
			y=game.HEIGHT * game.SCALE-50;
		
		
		
		for(int i = 0; i < game.eb.size(); i++) {
			EntityB tempEnt = game.eb.get(i);
		
			if(Physics.Collision(this, tempEnt)) {
				this.health -= 20;
				c.removeEntity(tempEnt);
				game.setEnemy_killcount(game.getEnemy_killcount() + 1);
				game.setEnemy_respawn(game.getEnemy_respawn() + 1);
			}
			
		}
		
		if(this.health <= 0) {
			game.reset();
		}
		
	}
	
	public void reset() {
		this.health = 100 * 2;
		this.x = 450;
		this.y = 800;
		this.velX = 0;
		this.velY = 0;
		this.skinIndex = 2;
	}

	public void render(Graphics g) {
			g.drawImage(this.tex.player[skinIndex], (int) x, (int) y, null);
	}
	
	public Rectangle getBounds(int width, int height) {
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	public void nextSkin() {
		
		// next index except if it's already last one
		if(this.skinIndex + 1 >= this.tex.player.length) {
			this.skinIndex = 0;
		} else {
			this.skinIndex++;		
		}
	}
	
	public void setSkinIndex(int index) {
		this.skinIndex = index;
	}
	
	public int getSkinIndex() {
		return this.skinIndex;
	}
	
}
