/*
 *******************************************************************************
 *
 * Purpose: MQTT message arrived task implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.monstrenyatko.butler.sensor.manager.event.EventSensorDataRecv;

public class MqttMessageArrivedTask extends MqttTask {

	private final String					topic;
	private final MqttMessage				message;

	public MqttMessageArrivedTask(MqttManager manager, String topic, MqttMessage message) {
		super(manager);
		this.topic = topic;
		this.message = message;
	}

	@Override public void run() {
		try {
			log.debug("Topic: " + topic);
			log.trace(message.toString());
			// TODO: check topic
			manager.getContext().getEventBus().post(new EventSensorDataRecv(message.getPayload()));
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Throwable e) {
			log.fatal("Critical error", e);
		}
	}
}
