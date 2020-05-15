package org.ddukki.game.ui.scroll;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.engine.events.Event;
import org.ddukki.game.engine.events.MousedEvent;
import org.ddukki.game.engine.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.ScrolledEvent;
import org.ddukki.game.ui.events.reactors.ScrolledReactor;

public class ScrollBar extends Entity {

	/** The scroll entity that will be moved inside the scrollbar parent */
	private class Scroll extends Entity implements MousedReactor {

		@Override
		public void react(Event e) {
			if (MousedEvent.class.isInstance(e)) {
				react((MousedEvent) e);
			}
		}

		@Override
		public void react(MousedEvent me) {
			if (me.type == MousedEvent.EventType.BUTTON_DOWN) {
				if (scroll.hbx.contains(me.x, me.y)) {
					dx = me.x;
					dy = me.y;
				} else {
					dx = -1;
					dy = -1;
				}
			}

			if (me.type == MousedEvent.EventType.DRAGGED && dx > -1
					&& dy > -1) {
				ScrolledEvent se = new ScrolledEvent(null);
				if (horizontal) {
					int deltaX = me.x - dx;

					// Update current scroll position
					current = (int) ((double) total / shown * deltaX);

					se.total = total;
					se.current = current;
					se.shown = shown;

					se.pscroll = current;
				} else {
					int deltaY = me.y - dy;

					// Update current scroll position
					current = (int) ((double) total / shown * deltaY);

					se.total = total;
					se.current = current;
					se.shown = shown;

					se.pscroll = current;
				}

				for (ScrolledReactor sr : scrolledReactors) {
					sr.react(se);
				}
			}
		}

		@Override
		public void update() {
			hbx = new RectangularHitbox(x, y, w, h);
		}

		@Override
		public void updateGraphic(Graphics2D g) {
			g.fillRect(scroll.x, scroll.y, scroll.w, scroll.h);
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
	private int dx = 0;
	private int dy = 0;

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
				scroll.hbx = new RectangularHitbox(x, y, shown, shown);
			} else {
				// Calculate the height of the scroll
				final int sh = (int) (((double) shown / total) * h);

				// Calculate the position of the scroll
				final int sy = (int) (((double) current / total) * h);

				scroll.x = x;
				scroll.y = y + sy;
				scroll.w = w;
				scroll.h = sh;
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
