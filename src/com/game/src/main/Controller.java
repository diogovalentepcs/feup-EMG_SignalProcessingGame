package com.game.src.main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import com.game.src.main.entities.EntityA;
import com.game.src.main.entities.EntityB;

public class Controller {
	
	private LinkedList<EntityA> ea = new LinkedList<EntityA>();
	private LinkedList<EntityB> eb = new LinkedList<EntityB>();
	
	Random r = new Random();

	EntityA ent_a;
	EntityB ent_b;
	Enemy TempEnemy;
	
	private Game game;
	Textures tex;
	
	public Controller(Game game, Textures tex) {
		this.game = game;
		this.tex = tex;
		addEntity(new Enemy(r.nextInt(Game.WIDTH * Game.SCALE), 0, tex, game, this));
	}
	
	public void createEnemy(int enemy_count) {
		for(int i = 0; i < enemy_count; i++) {
			addEntity(new Enemy(r.nextInt(Game.WIDTH * Game.SCALE), 0, tex, game, this));
		}
	}
	
	public void tick() {
		// A ENTITY
		for(int i=0;i<ea.size();i++) {
			ent_a = ea.get(i);
			
			if(ent_a.getY()<0)
				removeEntity(ent_a);
			
			ent_a.tick();
		}
		
		// A ENTITY
		for(int i=0;i<eb.size();i++) {
			ent_b = eb.get(i);
			
			if(ent_b.getY() > Game.HEIGHT * Game.SCALE) {
				ent_b.setY(0);
				ent_b.setX((double) r.nextInt(Game.WIDTH * Game.SCALE));
				//game.setEnemy_respawn(game.getEnemy_respawn()+1);
			}
			
			ent_b.tick();
		}
	}
	
	public void render(Graphics g) {
		// A ENTITY
		for(int i = 0;i < ea.size(); i++ ) {
			ent_a = ea.get(i);
			ent_a.render(g);
		}
		
		// B ENTITY
		for(int i = 0;i < eb.size(); i++ ) {
			ent_b = eb.get(i);
			ent_b.render(g);
		}
	}
	
	public void addEntity(EntityA block) {
		ea.add(block);
	}
	
	public void removeEntity(EntityA block) {
		ea.remove(block);
	}
	
	public void addEntity(EntityB block) {
		eb.add(block);
	}
	
	public void removeEntity(EntityB block) {
		eb.remove(block);
	}
	
	public LinkedList<EntityA> getEntityA(){
		return ea;
	}
	
	public LinkedList<EntityB> getEntityB(){
		return eb;
	}
	public void reset() {
		this.ea.clear();
		this.eb.clear();
	}
}
	
