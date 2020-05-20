package org.ddukki.game.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.UIEntity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.ui.events.Event;
import org.ddukki.game.ui.events.KeyedEvent;
import org.ddukki.game.ui.events.MousedEvent;
import org.ddukki.game.ui.events.ScrolledEvent;
import org.ddukki.game.ui.events.SelectedEvent;
import org.ddukki.game.ui.events.reactors.KeyedReactor;
import org.ddukki.game.ui.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.reactors.ScrolledReactor;
import org.ddukki.game.ui.events.reactors.SelectedReactor;
import org.ddukki.game.ui.scroll.ScrollBar;
import org.ddukki.game.util.ArrayUtil;

/** The UI element that displays an array of entities in a vertical list */
public class ListUI extends UIEntity
		implements MousedReactor, ScrolledReactor, KeyedReactor {

	/** List of strings that represent each index */
	private List<String> strings = new ArrayList<>();

	/** The list of selected elements */
	private List<Boolean> selected = new ArrayList<>();

	/** Whether or not multi-select is enabled */
	public boolean multi = false;

	/** The offset of the list rendering */
	public int fOffset = 0;

	/** String heights */
	public int sh = 0, fsh = 0;

	/** Selected reactors that are listening to this entity */
	public List<SelectedReactor> selectedReactors = new ArrayList<>();

	/***/
	private ScrollBar sb = new ScrollBar();

	public ListUI() {
		sb.scrolledReactors.add(this);
	}

	public void addItem(String s) {
		strings.add(s);
		selected.add(false);
	}

	/**
	 * A single-use way of ensuring that the selected index (if non-multi) is
	 * visible given the offset; updates the offset if that is not the case
	 */
	private void checkSelectionView() {
		// Find the number of selected components
		int[] indices = ArrayUtil.indices(ArrayUtil.toArray(selected));
		if (multi || indices.length == 0) {
			return;
		}

		// Get the selected index
		final int si = indices[0];

		// Get the min and max y of the selected index
		int minY = y + si * fsh - fOffset;
		int maxY = minY + fsh;

		if (minY < y) {
			fOffset -= y - minY;
		} else if (maxY > y + h) {
			fOffset += maxY - (y + h);
		}

		// Update the scrollbar
		sb.setCurrent(fOffset);
	}

	public int count() {
		return strings.size();
	}

	@Override
	public void react(Event e) {
		if (MousedEvent.class.isInstance(e)) {
			react((MousedEvent) e);
		}
		if (ScrolledEvent.class.isInstance(e) && e.getSource() == sb) {
			react((ScrolledEvent) e);
		}
		if (KeyedEvent.class.isInstance(e)) {
			react((KeyedEvent) e);
		}
	}

	@Override
	public void react(KeyedEvent ke) {
		if (Engine.keyFocus != this) {
			return;
		}

		if (ke.type == KeyedEvent.EventType.BUTTON_DOWN) {
			final boolean[] sb = ArrayUtil.toArray(selected);
			final int[] indices = ArrayUtil.indices(sb);
			if (indices.length == 0 || multi == true) {
				return;
			}

			switch (ke.kc) {
			case KeyedEvent.KC_UP:
				if (indices[0] < 0) {
					return;
				}

				select(indices[0] - 1, false);
				checkSelectionView();
				break;
			case KeyedEvent.KC_DOWN:
				if (indices[0] >= strings.size()) {
					return;
				}

				select(indices[0] + 1, false);
				checkSelectionView();
				break;
			}
		}
	}

	@Override
	public void react(MousedEvent me) {
		sb.react(me);

		if (me.type == MousedEvent.EventType.BUTTON_UP) {
			if (hbx.contains(me.x, me.y) && !me.reacted) {

				Engine.keyFocus = this;

				// Normalize the mouse click to the list
				final int my = me.y - y + fOffset;

				// Find the index of the mouse click
				final int i = my / fsh;

				// Control held
				final boolean m = (me.mod & MousedEvent.CTRL_MASK) != 0;

				select(i, m);

			} else if (Engine.keyFocus == this) {
				Engine.keyFocus = null;
			}
		}
	}

	@Override
	public void react(ScrolledEvent se) {
		fOffset = se.pscroll;
	}

	public void removeItem(int i) {
		strings.remove(i);
		selected.remove(false);
	}

	public void select(final int i, final boolean m) {
		if (i >= selected.size() || i < 0) {
			return;
		}

		// Set the selection
		if (!multi || !m) {
			for (int j = 0; j < selected.size(); j++) {
				selected.set(j, false);
			}
		}
		selected.set(i, true);

		// Emit selection event
		final SelectedEvent se = new SelectedEvent(this);
		se.selection = ArrayUtil.toArray(selected);
		for (SelectedReactor sr : selectedReactors) {
			sr.react(se);
		}
	}

	public void setItem(int i, String s) {
		strings.set(i, s);
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
		g.setColor(Engine.keyFocus == this ? Color.blue : Color.black);
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
			g.drawString(s, x + 10, y + fsh * i + sh - fOffset);
		}
		g.setClip(null);
		sb.updateGraphic(g);
	}
}
