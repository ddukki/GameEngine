package org.ddukki.game.ui.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MousedEvent extends Event {

	/** The type of moused event that occurred */
	public enum EventType {
			CLICKED, BUTTON_DOWN, BUTTON_UP, MOVED, DRAGGED, SCROLLED
	}

	/** Indicates that the CTRL button was held while the event occurred */
	public static final int CTRL_MASK = 0x01;

	/** Indicates that the Shift button was held while the event occurred */
	public static final int SHIFT_MASK = 0x02;

	/** Indicates that the ALT button was held while the event occurred */
	public static final int ALT_MASK = 0x04;

	/** The x coordinate at which the mouse event occurred */
	public int x = 0;

	/** The y coordinate at which the mouse event occurred */
	public int y = 0;

	/** The amount the mouse wheel was scrolled */
	public int s = 0;

	/** Modifier mask for what buttons were held while the event occurred */
	public int mod = 0;

	/** Event type */
	public EventType type = EventType.CLICKED;

	public MousedEvent(Object src) {
		super(src, "MouseEvent");
	}

	public MousedEvent(Object src, MouseEvent me) {
		super(src, "MouseEvent");

		final int metamasks = me.getModifiersEx();
		if ((metamasks & MouseEvent.ALT_DOWN_MASK) != 0) {
			mod |= ALT_MASK;
		}
		if ((metamasks & MouseEvent.SHIFT_DOWN_MASK) != 0) {
			mod |= SHIFT_MASK;
		}
		if ((metamasks & MouseEvent.CTRL_DOWN_MASK) != 0) {
			mod |= CTRL_MASK;
		}

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
