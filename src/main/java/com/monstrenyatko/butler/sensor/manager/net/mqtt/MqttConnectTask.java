/*
 *******************************************************************************
 *
 * Purpose: MQTT connect task implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * Establishes the MQTT connection.
 */
public class MqttConnectTask extends MqttTask {

	public MqttConnectTask(MqttManager manager) {
		super(manager);
	}

	@Override public void run() {
		try {
			if (manager.getContext().getMqtt().getClient().isConnected()) {
				log.info("Already connected");
			} else {
				log.info("Connecting");
				MqttConnectOptions options = new MqttConnectOptions();
				options.setConnectionTimeout(manager.getContext().getConfiguration().net().mqtt().connectionTimeoutSec);
				options.setKeepAliveInterval(manager.getContext().getConfiguration().net().mqtt().keepAliveIntervalSec);
				manager.getContext().getMqtt().getClient().connect(options, new IMqttActionListener() {

					@Override public void onFailure(IMqttToken token, Throwable cause) {
						manager.getContext().getMqtt().getProcessor().process(new MqttConnectionFailureTask(manager));
					}
	
					@Override public void onSuccess(IMqttToken token) {
						manager.getContext().getMqtt().getProcessor().process(new MqttConnectionSuccessTask(manager));
					}
				});
			}
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Throwable e) {
			log.fatal("Critical error", e);
		}
	}
}
