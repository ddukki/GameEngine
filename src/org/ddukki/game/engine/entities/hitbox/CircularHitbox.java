package org.ddukki.game.engine.entities.hitbox;

public class CircularHitbox extends Hitbox {

	public int x = 0;
	public int y = 0;
	public int r = 0;

	public boolean collide(CircularHitbox ch) {
		// Find the distance between the two centers
		final int distX = ch.x - x;
		final int distY = ch.y - y;
		final double distance = Math.sqrt(distX * distX + distY * distY);

		return distance < ch.r + r;
	}

	@Override
	public boolean collide(Hitbox h) {
		if (RectangularHitbox.class.isInstance(h)) {
			// The collision detection is implemented in rectangle
			return h.collide(this);
		} else if (CircularHitbox.class.isInstance(h)) {
			return collide(h);
		}

		return false;
	}

	@Override
	public boolean contains(int x, int y) {
		// Find the distance between the two centers
		final int distX = this.x - x;
		final int distY = this.y - y;
		final double distance = Math.sqrt(distX * distX + distY * distY);

		return distance < r;
	}

}
