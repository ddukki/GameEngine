package org.ddukki.game.ui.scroll;

import java.awt.Color;
import java.awt.Graphics2D;

import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.engine.events.Event;
import org.ddukki.game.engine.events.MousedEvent;
import org.ddukki.game.engine.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.ScrolledEvent;
import org.ddukki.game.ui.events.reactors.ScrolledReactor;

public class ScrollBar extends Entity implements MousedReactor {

	public boolean horizontal = false;
	public int position = 0;

	public ScrolledReactor attached = null;

	public RectangularHitbox rhbx = new RectangularHitbox(0, 0, 0, 0);

	/** The entire value range that this bar should represent */
	public int total = 0;

	/** The amount actually visible */
	public int shown = 0;

	/** The current value at which the scroll is positioned */
	public int current = 0;

	private int dx = 0;
	private int dy = 0;

	public ScrollBar() {
		w = 5;
	}

	@Override
	public void react(Event e) {
		if (MousedEvent.class.isInstance(e)) {
			react((MousedEvent) e);
		}
	}

	@Override
	public void react(MousedEvent me) {
		if (me.type == MousedEvent.EventType.BUTTON_DOWN) {
			if (rhbx.contains(me.x, me.y)) {
				dx = me.x;
				dy = me.y;
			} else {
				dx = -1;
				dy = -1;
			}
		}

		if (me.type == MousedEvent.EventType.DRAGGED && dx > -1 && dy > -1) {
			ScrolledEvent se = new ScrolledEvent(null);
			if (horizontal) {
				int deltaX = me.x - dx;
				se.total = total;
				se.current = current;
				se.shown = shown;

				se.pscroll = deltaX;

			} else {
				int deltaY = me.y - dy;
				se.total = total;
				se.current = current;
				se.shown = shown;

				se.pscroll = deltaY;
			}

			attached.react(se);
		}
	}

	@Override
	public void update() {
		if (attached == null) {
			return;
		}

		// Check the position and update this bar's position/size
		switch (position) {
		case 0:
			if (Entity.class.isInstance(attached)) {
				Entity aEntity = (Entity) attached;
				if (horizontal) {
					w = aEntity.w;
					h = 5;

					x = aEntity.x;
					y = aEntity.y + aEntity.h;
				} else {
					x = aEntity.x + aEntity.w;
					w = 5;

					y = aEntity.y;
					h = aEntity.h;
				}
			}
			break;
		}

		hbx = new RectangularHitbox(x, y, w, h);

		// Check the size and update the scroll position/size
		if (total > 0 && shown > 0) {
			if (horizontal) {
				rhbx = new RectangularHitbox(x, y, shown, shown);
			} else {
				// Calculate the height of the scroll
				final int sh = (int) (((double) shown / total) * h);

				// Calculate the position of the scroll
				final int sy = (int) (((double) current / total) * h);

				rhbx = new RectangularHitbox(x, y + sy, w, sh);
			}
		}
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		update();

		if (attached == null) {
			return;
		}

		g.setColor(Color.black);
		g.drawRect(x, y, w, h);
		g.fillRect(rhbx.x, rhbx.y, rhbx.w, rhbx.h);
	}
}
