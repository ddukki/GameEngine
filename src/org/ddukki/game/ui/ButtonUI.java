package org.ddukki.game.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.Entity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.ui.events.Event;
import org.ddukki.game.ui.events.MousedEvent;
import org.ddukki.game.ui.events.SubmittedEvent;
import org.ddukki.game.ui.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.reactors.SubmittedReactor;

/** A UI that acts as a button entity */
public class ButtonUI extends Entity implements MousedReactor {

	/** The list of reactors that are listening to events from this button */
	public List<SubmittedReactor> submittedReactors = new ArrayList<>();

	/** The name of the button and also the string that is displayed */
	public String name = "Button";

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

		if (hbx.contains(me.x, me.y)) {
			if (me.type == MousedEvent.EventType.BUTTON_DOWN) {
				Engine.gp.mouseFocus = this;
			}

			// Check if moused event was inside the hitbox
			if (me.type == MousedEvent.EventType.BUTTON_UP
					&& Engine.gp.mouseFocus == this) {

				SubmittedEvent se = new SubmittedEvent(this);
				for (SubmittedReactor sr : submittedReactors) {
					sr.react(se);
				}

				me.reacted = true;
			}
		}

		if (me.type == MousedEvent.EventType.BUTTON_UP) {
			if (Engine.gp.mouseFocus == this) {
				Engine.gp.mouseFocus = null;
			}
		}
	}

	@Override
	public void update() {
		final FontMetrics fm = Engine.gp.fm;

		// Get the required dimensions of the string and pad the button
		h = fm.getHeight() + fm.getDescent() + 8;
		w = fm.stringWidth(name) + 8;

		hbx = new RectangularHitbox(x, y, w, h);
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		final FontMetrics fm = Engine.gp.fm;
		g.setColor(Color.white);
		g.fillRect(x, y, w, h);

		g.setColor(Color.black);
		g.drawRect(x, y, w, h);

		// Draw the string centered in the button, adjusted for padding
		g.drawString(name, x + 4, y + 4 + fm.getHeight());
	}

}
