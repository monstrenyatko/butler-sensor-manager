/*
 *******************************************************************************
 *
 * Purpose: MQTT subscribe task implementation.
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

public class MqttSubscribeTask extends MqttTask {
	private final String[]					topicFilters;
	private final int[]						qos;
	private final MqttResult				resultCallback;
	
	public MqttSubscribeTask(MqttManager manager,
			String[] topicFilters, int[] qos, MqttResult resultCallback)
	{
		super(manager);
		this.topicFilters = topicFilters.clone();
		this.qos = qos.clone();
		this.resultCallback = resultCallback;
	}

	private class MqttSubscribeCommandFailure implements Runnable {

		private final Throwable				cause;

		public MqttSubscribeCommandFailure(Throwable cause) {
			this.cause = cause;
		}

		@Override public void run() {
			try {
				resultCallback.onFailure(cause);
			} catch (Exception e) {
				log.error("Error", e);
			} catch (Throwable e) {
				log.fatal("Critical error", e);
			}
		}
	}

	private class MqttSubscribeCommandSuccess implements Runnable {

		@Override public void run() {
			try {
				resultCallback.onSuccess();
			} catch (Exception e) {
				log.error("Error", e);
			} catch (Throwable e) {
				log.fatal("Critical error", e);
			}
		}
	}

	@Override public void run() {
		try {
			manager.getClient().subscribe(topicFilters, qos, null, new IMqttActionListener() {

				@Override public void onFailure(IMqttToken token, Throwable cause) {
					manager.getProcessor().process(new MqttSubscribeCommandFailure(cause));
				}

				@Override public void onSuccess(IMqttToken token) {
					manager.getProcessor().process(new MqttSubscribeCommandSuccess());
				}
			});
		} catch (Exception e) {
			log.error("Error", e);
			resultCallback.onFailure(e);
		} catch (Throwable e) {
			log.fatal("Critical error", e);
			resultCallback.onFailure(e);
		}
	}
}
