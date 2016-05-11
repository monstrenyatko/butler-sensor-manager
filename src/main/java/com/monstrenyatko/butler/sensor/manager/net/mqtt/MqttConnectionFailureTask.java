/*
 *******************************************************************************
 *
 * Purpose: MQTT connection failure task implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

public class MqttConnectionFailureTask extends MqttTask {

	public MqttConnectionFailureTask(MqttManager manager) {
		super(manager);
	}

	@Override public void run() {
		try {
			log.info("Connection failure");
			manager.getProcessor().process(new MqttConnectionRetryTask(manager));
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Throwable e) {
			log.fatal("Critical error", e);
		}
	}
}
