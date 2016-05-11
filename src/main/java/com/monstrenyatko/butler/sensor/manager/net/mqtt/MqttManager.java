/*
 *******************************************************************************
 *
 * Purpose: MQTT service implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.monstrenyatko.butler.dbg.LoggerI;
import com.monstrenyatko.butler.sensor.manager.SensorManagerContext;
import com.monstrenyatko.butler.task.TaskProcessor;

public class MqttManager {

	private final SensorManagerContext			ctx;
	private final LoggerI						log;
	private final TaskProcessor					processor;
	private final IMqttAsyncClient				mqtt;

	public MqttManager(SensorManagerContext ctx, TaskProcessor processor, IMqttAsyncClient mqtt) {
		this.ctx = ctx;
		log = this.ctx.getLoggerFactory().getLogger(this.getClass());
		this.processor = processor;
		this.mqtt = mqtt;
	}

	public synchronized void start() throws MqttException {
		log.debug("Start");
		mqtt.setCallback(new MqttCallback() {

			@Override public void connectionLost(Throwable cause) {
				processor.process(new MqttConnectionLostTask(MqttManager.this, cause));
			}

			@Override public void deliveryComplete(IMqttDeliveryToken token) {
			}

			@Override public void messageArrived(String topic, MqttMessage message) throws Exception {
				processor.process(new MqttMessageArrivedTask(MqttManager.this, topic, message));
			}
		});
		processor.start();
		processor.process(new MqttConnectTask(this));
	}

	public synchronized void stop() {
		log.debug("Stop");
		processor.process(new MqttDisconnectTask(this));
		processor.shutdown();
	}

	public IMqttAsyncClient getClient() {
		return mqtt;
	}

	public TaskProcessor getProcessor() {
		return processor;
	}

	public SensorManagerContext getContext() {
		return ctx;
	}

	public void subscribe(String[] topicFilters, int[] qos, MqttResult resultCallback) {
		processor.process(new MqttSubscribeTask(this, topicFilters, qos, resultCallback));
	}
}
