package org.ddukki.game.engine.entities.completion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.Entity;

/**
 * An entity, that when interacting with, will produce a new queue and add it to
 * the completion queue
 */
public class QueueAdder extends Entity implements MouseListener {

	public CompletionQueue q;

	public QueueAdder(final CompletionQueue q) {
		x = 115;
		y = 40;

		w = 100;
		h = 100;

		this.q = q;

		Engine.gp.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		final int mx = e.getX();
		final int my = e.getY();

		if (mx > x && mx < x + w && my > y && my < y + h) {

			// Add a completion item
			q.add(new CompletionItem("Item", (int) (1000 * Math.random()), 0));
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void update() {
	}

	@Override
	public void updateGraphic(final Graphics2D g) {
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);

		g.drawString("Add Item", x + 5, y + 15);
	}
}
