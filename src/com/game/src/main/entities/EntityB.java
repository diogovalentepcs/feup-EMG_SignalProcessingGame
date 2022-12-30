package com.game.src.main.entities;

import java.awt.Graphics;
import java.awt.Rectangle;


// entity B are enemies 
public interface EntityB {
	
	public void tick();
	public void render(Graphics g);
	public Rectangle getBounds(int width, int height);

	
	public double getX();
	public double getY();
	public void setX(double x);
	public void setY(double y);
}
