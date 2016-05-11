/*
 *******************************************************************************
 *
 * Purpose: Subscription manager implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net;

import com.google.common.eventbus.Subscribe;
import com.monstrenyatko.butler.dbg.LoggerI;
import com.monstrenyatko.butler.event.EventListenerI;
import com.monstrenyatko.butler.sensor.manager.SensorManagerContext;
import com.monstrenyatko.butler.sensor.manager.event.EventMqttConnected;
import com.monstrenyatko.butler.sensor.manager.net.mqtt.MqttResult;

/**
 * Controls subscription to the message queue.
 *
 */
public class SubscriptionManager {
	private final LoggerI					log;
	private final SensorManagerContext		ctx;
	private final EventListenerI			eventListener = new EventListenerI() {

		@Subscribe public void onMqttConnected(EventMqttConnected event) {
			SubscriptionManager.this.onMqttConnected();
		}
	};
	
	public SubscriptionManager(SensorManagerContext ctx) {
		this.ctx = ctx;
		log = this.ctx.getLoggerFactory().getLogger(this.getClass());
		this.ctx.getEventBus().register(eventListener);
	}

	private void onMqttConnected() {
		// TODO: do it asynchronously
		log.info("Subscribing");
		String[] topicFilters = {"butler/sensor/+/data"};
		int[] qos = {1};
		ctx.getMqtt().subscribe(topicFilters, qos, new MqttResult() {

			@Override
			public void onSuccess() {
				log.info("Subscription success");
			}

			@Override
			public void onFailure(Throwable cause) {
				log.info("Subscription failure");
				// TODO: retry
			}
		});
	}
}
