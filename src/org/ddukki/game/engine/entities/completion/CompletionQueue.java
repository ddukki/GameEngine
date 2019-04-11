package org.ddukki.game.engine.entities.completion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.Entity;

/**
 * A collection of completion items currently in progress
 * 
 */
public class CompletionQueue extends Entity {

	private List<CompletionItem> items = new ArrayList<>();

	public CompletionQueue() {
	}

	public void add(CompletionItem ci) {
		items.add(ci);
		ci.q = this;
	}

	public void remove(CompletionItem ci) {
		items.remove(ci);
		ci.q = null;
	}

	@Override
	public void updateGraphic(Graphics2D g) {

		// Find the widest completion item
		int mw = 100;
		for (CompletionItem i : items) {
			mw = i.w > mw ? i.w : mw;
		}

		w = mw + 10;

		x = Engine.gp.getWidth() - w - 5;
		y = 5;

		h = items.size() * 55 + 5;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, w, h);

		g.setColor(Color.black);
		g.drawRect(x, y, w, h);

		for (CompletionItem i : items) {
			i.updateGraphic(g);
		}
	}

	@Override
	public void update() {
		final int nItems = items.size();

		// Update the list
		for (int i = nItems - 1; i >= 0; i--) {
			final CompletionItem item = items.get(i);
			item.queueIndex = i;

			item.update();
		}
	}
}
