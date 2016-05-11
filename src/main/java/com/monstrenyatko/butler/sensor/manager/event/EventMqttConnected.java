/*
 *******************************************************************************
 *
 * Purpose: MQTT connected event implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.event;

public class EventMqttConnected extends Event {

	public EventMqttConnected() {
		super(EventType.MQTT_CONNECTED);
	}
}
