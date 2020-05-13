package org.ddukki.game.engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import org.ddukki.game.engine.events.Event;

public class TestNumberEntity extends Entity {
	public int number = 0;

	public TestNumberEntity() {
		x = 10;
		y = 40;

		w = 100;
		h = 100;
	}

	@Override
	public void react(Event e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		number++;
	}

	@Override
	public void updateGraphic(final Graphics2D g) {
		g.setColor(Color.black);
		g.drawString(String.valueOf(number), x, y);

		g.drawRect(x, y, w, h);
	}
}
