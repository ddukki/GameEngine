package org.ddukki.game.engine.states;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.events.Event;
import org.ddukki.game.engine.events.reactors.EventReactor;

/**
 * This class is responsible for keeping track of a single state space of the
 * game. This may include the main menu, for example, or a level of the game. A
 * state may be paused to freeze any updates until the state is unpaused.
 * Multiple states may be active at one time. It is up to the developer to make
 * sure that states are properly interacting with one another if there are
 * multiple states running simultaneously.
 */
public class State implements EventReactor {

	/** The list of entities that this state has */
	public List<Entity> entities = new ArrayList<>();

	/**
	 * Whether or not this state is paused; if {@code true} this state will not
	 * update, even if the update function is called
	 */
	public boolean paused = false;

	/**
	 * Whether or not this state is active. If the state is not active, it will
	 * not respond to any actions or inputs, and it will also not be visible.
	 * This, however, does not mean that the state is paused
	 */
	public boolean active = true;

	public State() {
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	@Override
	public void react(Event e) {
		/*
		 * Loop through the list of entities, going in reverse order (in order
		 * of elements visible on screen). It is up to individual entities to
		 * check whether the event has already been reacted to or not
		 */
		final int l = entities.size();
		for (int i = l - 1; i >= 0; i--) {
			entities.get(i).react(e);
		}
	}

	public void update() {
		if (paused) {
			return;
		}

		for (Entity e : entities) {
			e.update();
		}
	}

	public void updateGraphic(Graphics2D g) {
		// Only update the graphics/draw if the state is active
		if (!active) {
			return;
		}

		for (Entity e : entities) {
			e.updateGraphic(g);
		}
	}
}
