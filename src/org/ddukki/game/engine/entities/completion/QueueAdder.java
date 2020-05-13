package org.ddukki.game.engine.entities.completion;

import java.awt.Color;
import java.awt.Graphics2D;

import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.events.Event;
import org.ddukki.game.engine.events.MousedEvent;
import org.ddukki.game.engine.events.reactors.MousedReactor;

/**
 * An entity, that when interacting with, will produce a new queue and add it to
 * the completion queue
 */
public class QueueAdder extends Entity implements MousedReactor {

	public CompletionQueue q;

	public QueueAdder(final CompletionQueue q) {
		x = 115;
		y = 40;

		w = 100;
		h = 100;

		this.q = q;
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

		if (mx > x && mx < x + w && my > y && my < y + h) {

			// Add a completion item
			q.add(new CompletionItem("Item", (int) (1000 * Math.random()), 0));
		}

		me.reacted = true;
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
