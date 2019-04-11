package org.ddukki.game.engine.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.ddukki.game.engine.Engine;

public class TestNumberEntity extends Entity implements MouseListener {
	public int number = 0;

	public TestNumberEntity() {
		x = 10;
		y = 40;

		w = 100;
		h = 100;

		Engine.gp.addMouseListener(this);
	}

	public void updateGraphic(final Graphics2D g) {
		g.setColor(Color.black);
		g.drawString(String.valueOf(number), x, y);

		g.drawRect(x, y, w, h);
	}

	@Override
	public void update() {
		number++;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		final int mx = e.getX();
		final int my = e.getY();

		if (mx > x && mx < x + w && my > y && my < y + h) {
			number = 0;
			
			// Add a completion item
			
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
