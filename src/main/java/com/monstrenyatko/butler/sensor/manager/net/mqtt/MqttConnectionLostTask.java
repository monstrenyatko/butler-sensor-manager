/*
 *******************************************************************************
 *
 * Purpose: MQTT connection lost task implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

public class MqttConnectionLostTask extends MqttTask {
	private final Throwable					cause;

	public MqttConnectionLostTask(MqttManager manager, Throwable cause) {
		super(manager);
		this.cause = cause;
	}

	@Override public void run() {
		try {
			log.info("Connection lost, cause: " + cause.getMessage());
			manager.getProcessor().process(new MqttConnectionRetryTask(manager));
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Throwable e) {
			log.fatal("Critical error", e);
		}
	}
}
