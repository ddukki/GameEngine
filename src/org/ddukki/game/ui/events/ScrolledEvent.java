package org.ddukki.game.ui.events;

public class ScrolledEvent extends Event {

	/** The currently held information by the scrolled entity */
	public int total = 0;

	/** The currently held information by the scrolled entity */
	public int current = 0;

	/** The currently held information by the scrolled entity */
	public int shown = 0;

	/** The number of pixels scrolled through this event */
	public int pscroll = 0;

	public ScrolledEvent(Object data) {
		super(data, "ScrolledEvent");
	}
}
