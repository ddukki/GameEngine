package org.ddukki.game.engine.entities;

import java.awt.Graphics2D;

import org.ddukki.game.engine.entities.hitbox.Hitbox;
import org.ddukki.game.ui.events.reactors.EventReactor;

public abstract class UIEntity implements EventReactor {
	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;

	/**
	 * The hitbox defining this entity; does not necessarily need to follow the
	 * bounds of the entity
	 */
	public Hitbox hbx;

	public void setDimensions(final int nx,
			final int ny,
			final int nw,
			final int nh) {
		x = nx;
		y = ny;
		w = nw;
		h = nh;
	}

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
