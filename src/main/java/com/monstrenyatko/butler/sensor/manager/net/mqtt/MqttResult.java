/*
 *******************************************************************************
 *
 * Purpose: MQTT service result interface.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

public interface MqttResult {

	public void onSuccess();

	public void onFailure(Throwable cause);
}
