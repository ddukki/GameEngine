package org.ddukki.game.engine.events;

import java.awt.event.MouseEvent;

public class MousedEvent extends Event {

	/** The x coordinate at which the mouse event occurred */
	public int x = 0;

	/** The y coordinate at which the mouse event occurred */
	public int y = 0;

	public MousedEvent() {
		super(null, "MouseEvent");
	}

	public MousedEvent(MouseEvent me) {
		super(me, "MouseEvent");

		x = me.getX();
		y = me.getY();
	}
}
