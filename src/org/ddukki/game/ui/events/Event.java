package org.ddukki.game.ui.events;

/**
 * The class that defines a notifying event; this event can be passed with
 * information to notify listeners that the event occurred
 */
public class Event {

	public String eventName;
	public Object source;

	/** Whether or not this event has reacted with some reactive entity */
	public boolean reacted = false;

	public Event(Object src, String name) {
		this.source = src;
		this.eventName = name;
	}

	public String getName() {
		return eventName;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object src) {
		this.source = src;
	}
}
