/*
 *******************************************************************************
 *
 * Purpose: Event abstract implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.event;

import com.monstrenyatko.butler.event.EventI;

public abstract class Event implements EventI {

	private final long				ts = System.currentTimeMillis();
	private final EventType			type;

	public Event(EventType type) {
		this.type = type;
	}

	@Override public String toString() {
		return "Event-" + getType() + ", ts: " + getTs();
	}

	@Override final public EventType getType() {
		return type;
	}

	@Override final public long getTs() {
		return ts;
	}

	@Override public Object getData() {
		return null;
	}
}
