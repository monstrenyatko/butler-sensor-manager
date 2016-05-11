/*
 *******************************************************************************
 *
 * Purpose: MQTT task abstract implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

import com.monstrenyatko.butler.dbg.LoggerI;

public abstract class MqttTask implements Runnable {

	protected final MqttManager					manager;
	protected final LoggerI						log;

	public MqttTask(MqttManager manager) {
		this.manager = manager;
		log = this.manager.getContext().getLoggerFactory().getLogger(this.getClass());
	}
}
