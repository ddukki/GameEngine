package org.ddukki.game.ui.button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.ddukki.game.engine.Engine;
import org.ddukki.game.engine.entities.UIEntity;
import org.ddukki.game.engine.entities.hitbox.RectangularHitbox;
import org.ddukki.game.ui.events.Event;
import org.ddukki.game.ui.events.MousedEvent;
import org.ddukki.game.ui.events.SubmittedEvent;
import org.ddukki.game.ui.events.reactors.MousedReactor;
import org.ddukki.game.ui.events.reactors.SubmittedReactor;

/** A UI that acts as a button entity */
public class ButtonUI extends UIEntity implements MousedReactor {

	/** The list of reactors that are listening to events from this button */
	public List<SubmittedReactor> submittedReactors = new ArrayList<>();

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
		hbx = new RectangularHitbox(x, y, w, h);
	}

	@Override
	public void updateGraphic(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(x, y, w, h);

		g.setColor(Color.black);
		g.drawRect(x, y, w, h);
	}

}
