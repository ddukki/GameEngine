package org.ddukki.game.ui.events;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Defines a key event that has occurred
 */
public class KeyedEvent extends Event {

	public enum EventType {
			TYPED, BUTTON_DOWN, BUTTON_UP
	}

	/** Indicates that the CTRL button was held while the event occurred */
	public static final int CTRL_MASK = 0x01;

	/** Indicates that the Shift button was held while the event occurred */
	public static final int SHIFT_MASK = 0x02;

	/** Indicates that the ALT button was held while the event occurred */
	public static final int ALT_MASK = 0x04;

	public static final int KC_LEFT = 0x000;
	public static final int KC_RIGHT = 0x001;
	public static final int KC_UP = 0x002;
	public static final int KC_DOWN = 0x003;

	public static final int KC_ENTER = 0x004;
	public static final int KC_BACK_SPACE = 0x005;
	public static final int KC_DELETE = 0x006;
	public static final int KC_HOME = 0x007;
	public static final int KC_END = 0x008;

	public static final int KC_TAB = 0x009;

	/** Modifier mask for what buttons were held while the event occurred */
	public int mod = 0;

	/** The type of keyed event that occurred */
	public EventType type = EventType.TYPED;

	/** The character that was typed, if the event type is TYPED */
	public char tc = 0;

	public int kc = -1;

	public KeyedEvent(Object src) {
		super(src, "Keyed Event");
	}

	public KeyedEvent(Object src, KeyEvent ke) {
		super(src, "Keyed Event");
		tc = ke.getKeyChar();

		final int metamasks = ke.getModifiersEx();
		if ((metamasks & MouseEvent.ALT_DOWN_MASK) != 0) {
			mod |= ALT_MASK;
		}
		if ((metamasks & MouseEvent.SHIFT_DOWN_MASK) != 0) {
			mod |= SHIFT_MASK;
		}
		if ((metamasks & MouseEvent.CTRL_DOWN_MASK) != 0) {
			mod |= CTRL_MASK;
		}

		switch (ke.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			kc = KC_LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			kc = KC_RIGHT;
			break;
		case KeyEvent.VK_UP:
			kc = KC_UP;
			break;
		case KeyEvent.VK_DOWN:
			kc = KC_DOWN;
			break;
		case KeyEvent.VK_ENTER:
			kc = KC_ENTER;
			break;
		case KeyEvent.VK_BACK_SPACE:
			kc = KC_BACK_SPACE;
			break;
		case KeyEvent.VK_DELETE:
			kc = KC_DELETE;
			break;
		case KeyEvent.VK_HOME:
			kc = KC_HOME;
			break;
		case KeyEvent.VK_END:
			kc = KC_END;
			break;
		case KeyEvent.VK_TAB:
			kc = KC_TAB;
			break;
		default:
			break;
		}
	}

	public static boolean isPunctuation(final char c) {
		final String p = ".,/;:'\"#$!%^&*()[] {}|<>?-_=+";
		return p.contains("" + c);
	}
}
