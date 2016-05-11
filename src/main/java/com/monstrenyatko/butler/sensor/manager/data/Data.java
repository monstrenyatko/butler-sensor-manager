/*
 *******************************************************************************
 *
 * Purpose: Sensor data container implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.data;

public class Data {

	private String				sensorId;
	private DataType			type;
	private double				value;

	public Data(String sensorId, DataType type, double d) {
		this.type = type;
		this.sensorId = sensorId;
		this.value = d;
	}

	public String getSensorId() {
		return sensorId;
	}

	public DataType getType() {
		return type;
	}

	public double getValue() {
		return value;
	}

	@Override public String toString() {
		return "[" + "sensorId: " + getSensorId() + ", type: " + getType()
				+ ", value: " + getValue() + "]";
	}
}
