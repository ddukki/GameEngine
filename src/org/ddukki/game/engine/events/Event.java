package org.ddukki.game.engine.events;

/**
 * The class that defines a notifying event; this event can be passed with
 * information to notify listeners that the event occurred
 */
public class Event {

	public String eventName;
	public Object data;

	public Event(Object data, String name) {
		this.data = data;
		this.eventName = name;
	}

	public Object getData() {
		return data;
	}

	public String getName() {
		return eventName;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
