package org.ddukki.game.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.ui.events.Event;
import org.ddukki.game.ui.events.MousedEvent;
import org.ddukki.game.ui.events.ScrolledEvent;
import org.ddukki.game.ui.events.SelectedEvent;
import org.ddukki.game.ui.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.reactors.ScrolledReactor;
import org.ddukki.game.ui.scroll.ScrollBar;
import org.ddukki.game.util.ArrayUtil;

/** The UI element that displays an array of entities in a vertical list */
public class ListUI extends Entity implements MousedReactor, ScrolledReactor {

	/** List of strings that represent each index */
	public List<String> strings = new ArrayList<>();

	/** The list of selected elements */
	public List<Boolean> selected = new ArrayList<>();

	/** Whether or not multi-select is enabled */
	public boolean multi = false;

	/** The offset of the list rendering */
	public int fOffset = 0;

	/** String heights */
	public int sh = 0, fsh = 0;

	/***/
	private ScrollBar sb = new ScrollBar();

	public ListUI() {
		sb.scrolledReactors.add(this);
	}

	@Override
	public void react(Event e) {
		if (MousedEvent.class.isInstance(e)) {
			react((MousedEvent) e);
		}
		if (ScrolledEvent.class.isInstance(e) && e.getSource() == sb) {
			react((ScrolledEvent) e);
		}
	}

	@Override
	public void react(MousedEvent me) {
		sb.react(me);

		if (me.type == MousedEvent.EventType.BUTTON_UP
				&& hbx.contains(me.x, me.y)
				&& !me.reacted) {

			// Normalize the mouse click to the list
			final int my = me.y - y + fOffset;

			// Find the index of the mouse click
			final int i = my / fsh;

			// Set the selection
			if (!multi || (me.mod & MousedEvent.CTRL_MASK) == 0) {
				for (int j = 0; j < selected.size(); j++) {
					selected.set(j, false);
				}
			}
			selected.set(i, true);

			// Emit selection event
			final SelectedEvent se = new SelectedEvent(this);
			se.selection = ArrayUtil.toArray(selected);
			Engine.l.react(se);
		}
	}

	@Override
	public void react(ScrolledEvent se) {
		fOffset = se.pscroll;
	}

	@Override
	public void update() {
		sh = Engine.gp.fm.getHeight();
		fsh = Engine.gp.fm.getDescent() + sh + 4;
		hbx = new RectangularHitbox(x, y, w, h);

		// Update scrollbar settings
		sb.total = fsh * strings.size();
		sb.shown = h;
		sb.x = x + w;
		sb.y = y;
		sb.w = 10;
		sb.h = h;
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		g.drawRect(x, y, w, h);
		g.setClip(x, y, w, h);

		boolean[] sel = ArrayUtil.toArray(selected);
		for (int i = 0; i < strings.size(); i++) {

			if (sel[i]) {
				g.setColor(Color.lightGray);
				g.fillRect(x, y + fsh * i - fOffset, w, fsh);
			}

			g.setColor(Color.black);
			String s = strings.get(i);
			g.drawString(s, x, y + fsh * i + sh - fOffset);
		}
		g.setClip(null);
		sb.updateGraphic(g);
	}
}
