/*
 *******************************************************************************
 *
 * Purpose: Sensor data received event implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.event;

public class EventSensorDataRecv extends Event {

	private final byte[]			data;

	public EventSensorDataRecv(byte[] data) {
		super(EventType.SENSOR_DATA_RECV);
		this.data = data;
	}

	@Override public byte[] getData() {
		return data;
	}

	@Override public String toString() {
		return super.toString() + ", data: " + new String(data);
	}
}
