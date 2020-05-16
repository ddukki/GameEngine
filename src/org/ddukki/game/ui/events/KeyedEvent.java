package org.ddukki.game.ui.events;

import java.awt.event.KeyEvent;

import org.ddukki.game.engine.events.Event;

/**
 * Defines a key event that has occurred
 */
public class KeyedEvent extends Event {

	public enum EventType {
			TYPED, BUTTON_DOWN, BUTTON_UP
	}

	public static int KC_LEFT = 0x000;
	public static int KC_RIGHT = 0x001;
	public static int KC_UP = 0x002;
	public static int KC_DOWN = 0x003;

	public static int KC_ENTER = 0x004;
	public static int KC_BACK_SPACE = 0x005;
	public static int KC_DELETE = 0x006;
	public static int KC_HOME = 0x007;
	public static int KC_END = 0x008;

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
		default:
			break;
		}
	}

	public static boolean isPunctuation(final char c) {
		final String p = ".,/;:'\"#$!%^&*()[] {}|<>?-_=+";
		return p.contains("" + c);
	}
}
