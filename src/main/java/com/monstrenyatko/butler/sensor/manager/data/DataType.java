/*
 *******************************************************************************
 *
 * Purpose: Sensor data type enumeration.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.data;

public enum DataType {
	TEMPERATURE,
	LIGHT,
	HUMIDITY,
	UNKNOWN;

	static DataType get(String value) {
		switch(value.toUpperCase()) {
			case "TEMPERATURE":
			case "TEMP":
				return TEMPERATURE;
			case "LIGHT":
				return LIGHT;
			case "HUMIDITY":
			case "HUMID":
				return HUMIDITY;
			default:
				return UNKNOWN;
		}
	}
}
