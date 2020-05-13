package org.ddukki.game.ui.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.events.Event;
import org.ddukki.game.ui.menu.items.MenuItem;

/** A generic class that defines a menu */
public class Menu extends Entity {

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

	@Override
	public void react(Event e) {

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGraphic(Graphics2D g) {
		// Determine the width of the menu
		int width;
		if (fixedW <= 0) {
			width = 200;

			for (MenuItem m : items) {
				if (width < m.w) {
					width = m.w;
				}
			}
		} else {
			width = fixedW;
		}

		// Determine the height of the menu
		int height;
		if (fixedH <= 0) {
			height = 400;

			for (MenuItem m : items) {
				if (height < m.h) {
					height = m.h;
				}
			}
		} else {
			height = fixedH;
		}

		// Draw the outline of the menu
		int menuX, menuY;
		switch (position) {
		// LEFT
		case 0:
			menuX = menuY = 10;
			break;

		// RIGHT
		case 1:
			menuX = Engine.gp.getWidth() - 10 - width;
			menuY = 10;
			break;

		// CENTER
		case 2:
			menuX = (Engine.gp.getWidth() - width) / 2;
			menuY = (Engine.gp.getHeight() - height) / 2;
			break;
		default:
			menuX = x;
			menuY = y;
			break;
		}

		g.setColor(Color.white);
		g.fillRect(menuX, menuY, width, height);
		g.setColor(Color.black);
		g.drawRect(menuX, menuY, width, height);

		// Draw each menu item seperately
		for (MenuItem m : items) {
			m.updateGraphic(g);
		}
	}
}
