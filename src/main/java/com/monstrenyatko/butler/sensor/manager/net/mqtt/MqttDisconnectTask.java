/*
 *******************************************************************************
 *
 * Purpose: MQTT disconnect task implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

public class MqttDisconnectTask extends MqttTask {

	public MqttDisconnectTask(MqttManager manager) {
		super(manager);
	}

	@Override public void run() {
		try {
			log.debug("Disconnecting");
			if (manager.getClient().isConnected()) {
				manager.getClient().disconnect().waitForCompletion();
			}
			log.debug("Disconnected");
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Throwable e) {
			log.fatal("Critical error", e);
		}
	}
}
