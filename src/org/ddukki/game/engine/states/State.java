package org.ddukki.game.engine.states;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.entities.TestNumberEntity;

public class State {
	public List<Entity> entities = new ArrayList<>();
	
	public boolean paused = false;
	
	public State() {
		entities.add(new TestNumberEntity());
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void updateGraphic(Graphics2D g) {
		for (Entity e : entities) {
			e.updateGraphic(g);
		}
	}

	public void update() {
		if (!paused) {
			for (Entity e : entities) {
				e.update();
			}
		}
	}
}
