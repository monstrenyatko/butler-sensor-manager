/*
 *******************************************************************************
 *
 * Purpose: MQTT connection success task implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

import com.monstrenyatko.butler.sensor.manager.event.EventMqttConnected;

public class MqttConnectionSuccessTask extends MqttTask {

	public MqttConnectionSuccessTask(MqttManager manager) {
		super(manager);
	}

	@Override public void run() {
		try {
			log.info("Connected");
			manager.getContext().getEventBus().post(new EventMqttConnected());
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Throwable e) {
			log.fatal("Critical error", e);
		}
	}
}
