package org.ddukki.game.ui.scroll;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.ui.events.Event;
import org.ddukki.game.ui.events.MousedEvent;
import org.ddukki.game.ui.events.ScrolledEvent;
import org.ddukki.game.ui.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.reactors.ScrolledReactor;

public class ScrollBar extends Entity {

	/** The scroll entity that will be moved inside the scrollbar parent */
	private class Scroll extends Entity implements MousedReactor {

		private void boundsCheck() {
			if (current > total - shown) {
				current = total - shown;
				y = ScrollBar.this.y + ScrollBar.this.h - h;
			}
			if (current < 0) {
				current = 0;
				y = ScrollBar.this.y;
			}
		}

		@Override
		public void react(Event e) {
			if (MousedEvent.class.isInstance(e)) {
				react((MousedEvent) e);
			}
		}

		@Override
		public void react(MousedEvent me) {

			// Get the starting coordinates for the drag
			if (me.type == MousedEvent.EventType.BUTTON_DOWN && !me.reacted
					&& hbx.contains(me.x, me.y)) {
				dx = me.x;
				dy = me.y;

				// Make sure that this component now has mouse focus
				Engine.gp.mouseFocus = this;
				me.reacted = true;
			}

			// If the mouse is released anywhere, this no longer has focus
			if (me.type == MousedEvent.EventType.BUTTON_UP) {
				dx = -1;
				dy = -1;

				if (Engine.gp.mouseFocus == this) {
					Engine.gp.mouseFocus = null;
				}
			}

			if (me.type == MousedEvent.EventType.DRAGGED
					&& Engine.gp.mouseFocus == this
					&& !me.reacted

					// Make sure that the initial drag coordinates are set
					&& dx > -1
					&& dy > -1) {

				// Calculate the new current position based on dragged scroll
				if (horizontal) {
					// Set up required variables
					int deltaX = me.x - dx;
					dx = me.x;
					x += deltaX;

					// Define some needed numbers
					double factor = (double) total / ScrollBar.this.w;
					int scrollX = x - ScrollBar.this.x;

					// Update current scroll position
					current = (int) (factor * scrollX);
				} else {
					// Set up required variables
					int deltaY = me.y - dy;
					dy = me.y;
					y += deltaY;

					// Define some needed numbers
					double factor = (double) total / ScrollBar.this.h;
					int scrollY = y - ScrollBar.this.y;

					// Update current scroll position
					current = (int) (factor * scrollY);
				}

				// Maintain bounds
				boundsCheck();

				// Create scrolled event and fire
				ScrolledEvent se = new ScrolledEvent(this);
				se.total = total;
				se.current = current;
				se.shown = shown;
				se.pscroll = current;
				for (ScrolledReactor sr : scrolledReactors) {
					sr.react(se);
				}

				// Consume this event
				me.reacted = true;
			}
		}

		@Override
		public void update() {
			hbx = new RectangularHitbox(x, y, w, h);
		}

		@Override
		public void updateGraphic(Graphics2D g) {
			if (total < shown) {
				return;
			}
			g.fillRect(x, y, w, h);
		}

	}

	public boolean horizontal = false;

	/**
	 * Whether this scroll bar is set to the left or right of the attached
	 * entity (if vertical) or to the top or bottom (if horizontal)
	 */
	public int position = 0;

	public Scroll scroll = new Scroll();

	/** The entire value range that this bar should represent */
	public int total = 0;

	/** The amount actually visible */
	public int shown = 0;

	/** The current value at which the scroll is positioned */
	public int current = 0;

	/** Mouse drag coordinates */
	private int dx = -1;
	private int dy = -1;

	/** The list of reactors listening for scrolled events from this bar */
	public ArrayList<ScrolledReactor> scrolledReactors = new ArrayList<>();

	@Override
	public void react(Event e) {
		scroll.react(e);
	}

	@Override
	public void update() {
		hbx = new RectangularHitbox(x, y, w, h);

		// Check the size and update the scroll position/size
		if (total > 0 && shown > 0) {
			if (horizontal) {
				// Calculate the height of the scroll
				final int sw = (int) (((double) shown / total) * w);

				scroll.x = x;
				scroll.w = sw;
				scroll.h = h;

				if (scroll.x < x) {
					scroll.x = x;
				}
				if (scroll.x > x + w - sw) {
					scroll.x = x + w - sw;
				}
			} else {
				// Calculate the height of the scroll
				final int sh = (int) (((double) shown / total) * h);

				scroll.x = x;
				scroll.w = w;
				scroll.h = sh;

				if (scroll.y < y) {
					scroll.y = y;
				}
				if (scroll.y > y + h - sh) {
					scroll.y = y + h - sh;
				}
			}
		}

		scroll.update();
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		update();

		if (w == 0 || h == 0) {
			return;
		}

		g.setColor(Color.black);
		g.drawRect(x, y, w, h);

		scroll.updateGraphic(g);
	}
}
