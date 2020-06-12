package org.ddukki.game.ui.scroll;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.UIEntity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.ui.button.ButtonUI;
import org.ddukki.game.ui.events.Event;
import org.ddukki.game.ui.events.MousedEvent;
import org.ddukki.game.ui.events.ScrolledEvent;
import org.ddukki.game.ui.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.reactors.ScrolledReactor;

public class ScrollBar extends UIEntity {

	private class Bar extends UIEntity implements MousedReactor {

		@Override
		public void react(Event e) {
		}

		@Override
		public void react(MousedEvent me) {
		}

		@Override
		public void update() {
			hbx = new RectangularHitbox(x, y, w, h);
		}

		@Override
		public void updateGraphic(Graphics2D g) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, w, h);

			g.setColor(Color.black);
			g.drawRect(x, y, w, h);
		}
	}

	private class DownArrow extends ButtonUI implements MousedReactor {

		@Override
		public void react(MousedEvent me) {
			if (hbx.contains(me.x, me.y) && !me.reacted
					&& me.type == MousedEvent.EventType.CLICKED) {
				current += total / 10;
				if (current > total - shown + 1) {
					current = total - shown + 1;
				}

				scroll.updateFromCurrent();

				// Create scrolled event and fire
				ScrolledEvent se = new ScrolledEvent(this);
				se.total = total;
				se.current = current;
				se.shown = shown;
				se.pscroll = current;
				for (ScrolledReactor sr : scrolledReactors) {
					sr.react(se);
				}

				me.reacted = true;
			}
		}

		@Override
		public void update() {
			hbx = new RectangularHitbox(x, y, w, h);
		}

		@Override
		public void updateGraphic(Graphics2D g) {
			g.setColor(Color.white);
			g.fillRect(x, y, w, h);

			g.setColor(Color.black);
			g.drawRect(x, y, w, h);

			// Calculate location and size of triangle
			final int tw = h / 3;
			final int tx = x + (h / 6) + 1;
			final int ty = y + tw;

			final int[] txpl = new int[] { tx, tx + tw, tx + tw };
			final int[] typl = new int[] { ty, ty + tw, ty };

			final int[] txpr = new int[] { tx + tw, tx + tw, tx + 2 * tw };
			final int[] typr = new int[] { ty, ty + tw, ty };

			// Draw two triangles, one from the left and one from the right
			g.fillPolygon(txpl, typl, 3);
			g.fillPolygon(txpr, typr, 3);
		}
	}

	/** The scroll entity that will be moved inside the scrollbar parent */
	private class Scroll extends UIEntity implements MousedReactor {

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
					double factor = (double) total / bar.h;
					int scrollY = y - bar.y;

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

		/**
		 * Updates the position from the current variable in the main scrollbar
		 * class
		 */
		public void updateFromCurrent() {
			// Define some needed numbers
			double factor = (double) total / bar.h;

			// Update current scroll position
			y = (int) (current / factor) + bar.y;

		}

		@Override
		public void updateGraphic(Graphics2D g) {
			if (total < shown) {
				return;
			}
			g.fillRect(x, y, w, h);
		}
	}

	private class UpArrow extends ButtonUI implements MousedReactor {

		@Override
		public void react(MousedEvent me) {
			if (hbx.contains(me.x, me.y) && !me.reacted
					&& me.type == MousedEvent.EventType.CLICKED) {
				current -= total / 10;
				if (current < 0) {
					current = 0;
				}

				scroll.updateFromCurrent();

				// Create scrolled event and fire
				ScrolledEvent se = new ScrolledEvent(this);
				se.total = total;
				se.current = current;
				se.shown = shown;
				se.pscroll = current;
				for (ScrolledReactor sr : scrolledReactors) {
					sr.react(se);
				}

				me.reacted = true;
			}
		}

		@Override
		public void update() {
			hbx = new RectangularHitbox(x, y, w, h);
		}

		@Override
		public void updateGraphic(Graphics2D g) {
			g.setColor(Color.white);
			g.fillRect(x, y, w, h);

			g.setColor(Color.black);
			g.drawRect(x, y, w, h);

			// Calculate location and size of triangle
			final int tw = h / 3;
			final int tx = x + (h / 6) + 1;
			final int ty = y + tw;

			final int[] txpl = new int[] { tx, tx + tw, tx + tw };
			final int[] typl = new int[] { ty + tw, ty, ty + tw };

			final int[] txpr = new int[] { tx + tw, tx + tw, tx + 2 * tw };
			final int[] typr = new int[] { ty + tw, ty, ty + tw };

			// Draw two triangles, one from the left and one from the right
			g.fillPolygon(txpl, typl, 3);
			g.fillPolygon(txpr, typr, 3);
		}
	}

	public boolean horizontal = false;

	/**
	 * Whether this scroll bar is set to the left or right of the attached
	 * entity (if vertical) or to the top or bottom (if horizontal)
	 */
	public int position = 0;

	public Bar bar = new Bar();

	public Scroll scroll = new Scroll();

	public UpArrow upBtn = new UpArrow();

	public DownArrow downBtn = new DownArrow();

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
		upBtn.react(e);
		scroll.react(e);
		downBtn.react(e);
	}

	public void setCurrent(int nc) {
		current = nc;

		// Calculate scroll position using current value
		double factor = (double) total / bar.h;

		// Update current scroll position
		scroll.y = (int) (bar.y + current / factor);
	}

	@Override
	public void update() {
		hbx = new RectangularHitbox(x, y, w, h);

		bar.x = x;
		bar.y = y + w;
		bar.w = w;
		bar.h = h - 2 * w;

		bar.update();

		// Check the size and update the scroll position/size
		if (total > 0 && shown > 0) {
			if (horizontal) {
				// Calculate the height of the scroll
				final int sw = (int) (((double) shown / total) * w);

				scroll.x = x;
				scroll.w = sw;
				scroll.h = bar.h;

				if (scroll.x < x) {
					scroll.x = x;
				}
				if (scroll.x > x + w - sw) {
					scroll.x = x + w - sw;
				}
			} else {
				// Calculate the height of the scroll
				final int sh = (int) (((double) shown / total) * bar.h);

				scroll.x = x;
				scroll.w = w;
				scroll.h = sh;

				if (scroll.y < bar.y) {
					scroll.y = bar.y;
				}
				if (scroll.y > bar.y + bar.h - sh) {
					scroll.y = bar.y + bar.h - sh;
				}
			}
		}

		scroll.update();

		upBtn.x = x;
		upBtn.y = y;
		upBtn.w = w;
		upBtn.h = w;
		upBtn.update();

		downBtn.x = x;
		downBtn.y = y + upBtn.h + bar.h;
		downBtn.w = w;
		downBtn.h = w;
		downBtn.update();
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		update();

		if (w == 0 || h == 0) {
			return;
		}

		bar.updateGraphic(g);
		scroll.updateGraphic(g);
		upBtn.updateGraphic(g);
		downBtn.updateGraphic(g);
	}
}
