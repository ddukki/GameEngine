package org.ddukki.game.engine.entities.completion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.events.Event;
import org.ddukki.game.ui.events.ScrolledEvent;
import org.ddukki.game.ui.events.reactors.ScrolledReactor;
import org.ddukki.game.ui.scroll.ScrollBar;

/**
 * A collection of completion items currently in progress
 * 
 */
public class CompletionQueue extends Entity implements ScrolledReactor {

	/** The list of completion items in the queue */
	private List<CompletionItem> items = new ArrayList<>();

	/** The maximum height of this object */
	private int maxHeight = 500;

	/** The offset at which the queue items should be drawn */
	public int queueOffset = 0;

	private ScrollBar sb = new ScrollBar();

	public CompletionQueue() {
		sb.attached = this;
	}

	/**
	 * Adds the completion item to the queue and sets its queue reference to
	 * this queue
	 */
	public void add(CompletionItem ci) {
		items.add(ci);
		ci.q = this;
	}

	/** @return the height of the items in the queue list in pixels */
	private int itemsHeight() {
		return items.size() * 55 + 5;
	}

	@Override
	public void react(Event e) {
		if (ScrolledEvent.class.isInstance(e)) {
			react((ScrolledEvent) e);
		}

		sb.react(e);
	}

	@Override
	public void react(ScrolledEvent se) {
		// Calculate the number of pixels to move the offset
		int pixelOffset = (int) ((double) se.total / se.shown * se.pscroll);
		queueOffset = pixelOffset;
	}

	public void remove(CompletionItem ci) {
		items.remove(ci);
		ci.q = null;
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

		if (itemsHeight() > maxHeight) {
			sb.total = itemsHeight();
			sb.shown = maxHeight;

			if (queueOffset > itemsHeight() - maxHeight) {
				queueOffset = itemsHeight() - maxHeight;
			} else if (queueOffset < 0) {
				queueOffset = 0;
			}

			sb.current = queueOffset;
		} else {
			sb.total = 0;
			sb.shown = 0;
			sb.current = 0;
			queueOffset = 0;
		}
	}

	@Override
	public void updateGraphic(Graphics2D g) {

		// Find the widest completion item
		int mw = 100;
		for (CompletionItem i : items) {
			mw = i.w > mw ? i.w : mw;
		}

		w = mw + 10;

		x = Engine.gp.getWidth() - w - 20;
		y = 5;

		h = itemsHeight();

		// Cannot be larger than the max height
		h = h > maxHeight ? maxHeight : h;

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, w, h);

		g.setColor(Color.black);
		g.drawRect(x, y, w, h);

		// Set the clip bounds
		g.setClip(x + 5, y + 5, w - 10, h - 10);

		for (CompletionItem i : items) {
			i.updateGraphic(g);
		}

		// Reset the clip for other entities to be drawn correctly
		g.setClip(null);
		sb.updateGraphic(g);
	}
}
