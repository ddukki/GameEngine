package org.ddukki.game.engine.entities.hitbox;

/** A generic class for hit detection */
public abstract class Hitbox {

	/**
	 * @return whether the given hitbox collides or intersects this box at any
	 *         pixel in the graphics environment
	 */
	public abstract boolean collide(Hitbox h);

	/** @return whether the given 2d point is within this hitbox */
	public abstract boolean contains(int x, int y);
}
