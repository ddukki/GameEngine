package org.ddukki.game.ui.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.ui.events.Event;
import org.ddukki.game.ui.events.MousedEvent;
import org.ddukki.game.ui.events.reactors.MousedReactor;
import org.ddukki.game.ui.menu.items.MenuItem;

/** A generic class that defines a menu */
public class Menu extends Entity implements MousedReactor {

	/** Menu items */
	public ArrayList<MenuItem> items = new ArrayList<>();

	/**
	 * The width of a fixed menu; if negative or 0, the width will be determined
	 * by the menu items with a minimum width of 200px
	 */
	private int fixedW = 200;

	/**
	 * The height of a fixed menu; if negative or 0, the height will be
	 * determined by the menu items with a minimum height of 400px
	 */
	private int fixedH = 400;

	/**
	 * Position the menu either left, right, center; if negative, the
	 * coordinates specified by the Entity fields will be used
	 */
	private int position = 0;

	public Menu() {
		hbx = new RectangularHitbox(x, y, w, h);
	}

	@Override
	public void react(Event e) {
		if (MousedEvent.class.isInstance(e)) {
			react((MousedEvent) e);
		}
	}

	@Override
	public void react(MousedEvent me) {
		if (me.reacted) {
			return;
		}

		final int mx = me.x;
		final int my = me.y;

		if (hbx.contains(mx, my) && me.type == MousedEvent.EventType.CLICKED) {
			position += 1;
			position %= 3;

			me.reacted = true;
		}
	}

	@Override
	public void update() {
		// Determine the width of the menu
		if (fixedW <= 0) {
			w = 200;

			for (MenuItem m : items) {
				w = w < m.w ? m.w : w;
			}
		} else {
			w = fixedW;
		}

		// Determine the height of the menu
		if (fixedH <= 0) {
			h = 400;

			for (MenuItem m : items) {
				h = h < m.h ? m.h : h;
			}
		} else {
			h = fixedH;
		}

		// determine the position of the menu
		switch (position) {
		// LEFT
		case 0:
			x = y = 10;
			break;

		// RIGHT
		case 1:
			x = Engine.gp.getWidth() - 10 - w;
			y = 10;
			break;

		// CENTER
		case 2:
			x = (Engine.gp.getWidth() - w) / 2;
			y = (Engine.gp.getHeight() - h) / 2;
			break;
		default:
			break;
		}

		hbx = new RectangularHitbox(x, y, w, h);
	}

	@Override
	public void updateGraphic(Graphics2D g) {

		g.setColor(Color.white);
		g.fillRect(x, y, w, h);
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);

		// Draw each menu item separately
		for (MenuItem m : items) {
			m.updateGraphic(g);
		}
	}
}
