package org.ddukki.game.engine.states;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.entities.TestNumberEntity;

/**
 * This class is responsible for keeping track of a single state space of the
 * game. This may include the main menu, for example, or a level of the game. A
 * state may be paused to freeze any updates until the state is unpaused.
 * Multiple states may be active at one time. It is up to the developer to make
 * sure that states are properly interacting with one another if there are
 * multiple states running simultaneously.
 */
public class State {

	/** The list of entities that this state has */
	public List<Entity> entities = new ArrayList<>();

	/**
	 * Whether or not this state is paused; if {@code true} this state will not
	 * update, even if the update function is called
	 */
	public boolean paused = false;

	public State() {
		entities.add(new TestNumberEntity());
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void update() {
		if (!paused) {
			for (Entity e : entities) {
				e.update();
			}
		}
	}

	public void updateGraphic(Graphics2D g) {
		for (Entity e : entities) {
			e.updateGraphic(g);
		}
	}
}
