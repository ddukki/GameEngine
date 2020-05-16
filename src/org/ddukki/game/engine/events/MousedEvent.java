package org.ddukki.game.engine.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MousedEvent extends Event {

	public enum EventType {
			CLICKED, BUTTON_DOWN, BUTTON_UP, MOVED, DRAGGED, SCROLLED
	}

	/** The x coordinate at which the mouse event occurred */
	public int x = 0;

	/** The y coordinate at which the mouse event occurred */
	public int y = 0;

	/** The amount the mouse wheel was scrolled */
	public int s = 0;

	/** Event type */
	public EventType type = EventType.CLICKED;

	public MousedEvent(Object src) {
		super(src, "MouseEvent");
	}

	public MousedEvent(Object src, MouseEvent me) {
		super(src, "MouseEvent");

		x = me.getX();
		y = me.getY();

		switch (me.getID()) {
		case MouseEvent.MOUSE_CLICKED:
			type = EventType.CLICKED;
			break;
		case MouseEvent.MOUSE_PRESSED:
			type = EventType.BUTTON_DOWN;
			break;
		case MouseEvent.MOUSE_RELEASED:
			type = EventType.BUTTON_UP;
			break;
		case MouseEvent.MOUSE_MOVED:
			type = EventType.MOVED;
			break;
		case MouseEvent.MOUSE_DRAGGED:
			type = EventType.DRAGGED;
			break;
		default:
			type = null;
			break;
		}
	}

	public MousedEvent(Object src, MouseWheelEvent mwe) {
		super(src, "MouseEvent");

		x = mwe.getX();
		y = mwe.getY();
		s = mwe.getScrollAmount();

		type = EventType.SCROLLED;
	}
}
