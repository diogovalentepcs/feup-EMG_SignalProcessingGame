package com.game.src.main;

import java.awt.Graphics;

import com.game.src.main.entities.EntityA;

public class Bullet extends GameObject implements EntityA{

	private Textures tex;
	private Game game;
	
	
	public Bullet(double x, double y, Textures tex, Game game) {
		super(x,y);
		this.tex = tex;
		this.game = game;

	}
	
	public void tick() {
		y -= 10;
		
	}
	
	public void render(Graphics g) {
		g.drawImage(this.tex.bullet, (int) x, (int) y, null);
	}
	
	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}
}
