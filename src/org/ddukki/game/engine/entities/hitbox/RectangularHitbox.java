package org.ddukki.game.engine.entities.hitbox;

public class RectangularHitbox extends Hitbox {

	public int x = 0;
	public int y = 0;
	public int w = 0;
	public int h = 0;

	public RectangularHitbox(final int x,
			final int y,
			final int w,
			final int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean collide(CircularHitbox ch) {

		// temporary variables to set edges for testing
		double testX = x;
		double testY = y;

		// which edge is closest?
		if (ch.x < x)
			testX = x; // test left edge
		else if (ch.x > x + w)
			testX = x + w; // right edge
		if (ch.y < y)
			testY = y; // top edge
		else if (ch.y > y + h)
			testY = y + h; // bottom edge

		// get distance from closest edges
		double distX = ch.x - testX;
		double distY = ch.y - testY;
		double distance = Math.sqrt((distX * distX) + (distY * distY));

		// if the distance is less than the radius, collision!
		if (distance <= ch.r) {
			return true;
		}
		return false;
	}

	@Override
	public boolean collide(Hitbox h) {

		if (RectangularHitbox.class.isInstance(h)) {
			return collide((RectangularHitbox) h);
		} else if (CircularHitbox.class.isInstance(h)) {
			return collide((CircularHitbox) h);
		}

		return false;
	}

	public boolean collide(RectangularHitbox rh) {

		return x < rh.x + rh.w && x + w > rh.x
				&& y < rh.y + rh.h
				&& y + h > rh.y;
	}

	@Override
	public boolean contains(int x, int y) {
		return x >= this.x && x <= this.x + this.w
				&& y >= this.y
				&& y <= this.y + this.h;
	}

}
