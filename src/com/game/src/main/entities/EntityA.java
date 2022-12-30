package com.game.src.main.entities;

import java.awt.Graphics;
import java.awt.Rectangle;


// entity A are firendly (players and bullets)
public interface EntityA {
	
	public void tick();
	public void render(Graphics g);
	public Rectangle getBounds(int width, int height);
	
	public double getX();
	public double getY();
}
