package org.ddukki.game.engine.entities;

import java.awt.Graphics2D;

import org.ddukki.game.engine.events.reactors.EventReactor;

public abstract class Entity implements EventReactor {
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;

	/**
	 * Should update internal data in relation to the current game state
	 */
	public abstract void update();

	/**
	 * Updates the graphical environment to render whatever this entity should
	 * render (i.e. a sprite, text, button, etc.)
	 */
	public abstract void updateGraphic(final Graphics2D g);
}
