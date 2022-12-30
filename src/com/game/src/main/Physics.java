package com.game.src.main;

import java.util.LinkedList;

import com.game.src.main.entities.EntityA;
import com.game.src.main.entities.EntityB;

public class Physics {

	// check if A Entity collides into B Enttity
	public static boolean Collision(EntityA ent_a, EntityB ent_b) {
			
		if(ent_a.getBounds(50, 50).intersects(ent_b.getBounds(50, 50))) {
				return true;
		}
		
		return false;
	}
	
	// check if B Entity collides into A Enttity
	public static boolean Collision(EntityB ent_b, EntityA ent_a) {
		
			
		if(ent_b.getBounds(50, 50).intersects(ent_a.getBounds(50, 50))) {
				return true;			
		}
		
		return false;
	}
}
