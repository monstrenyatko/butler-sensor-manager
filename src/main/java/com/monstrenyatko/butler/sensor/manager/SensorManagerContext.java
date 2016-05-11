/*
 *******************************************************************************
 *
 * Purpose: Sensor manager context implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager;

import com.monstrenyatko.butler.dbg.LoggerFactoryI;
import com.monstrenyatko.butler.event.EventBusI;
import com.monstrenyatko.butler.sensor.manager.config.Configuration;
import com.monstrenyatko.butler.sensor.manager.data.DataManager;
import com.monstrenyatko.butler.sensor.manager.net.SubscriptionManager;
import com.monstrenyatko.butler.sensor.manager.net.http.HttpManager;
import com.monstrenyatko.butler.sensor.manager.net.mqtt.MqttManager;
import com.monstrenyatko.butler.task.TaskExecutorFactory;
import com.monstrenyatko.butler.utils.ContextI;

public class SensorManagerContext implements ContextI {

	private Configuration					configuration;
	private LoggerFactoryI					loggerFactory;
	private TaskExecutorFactory				taskExecutorFactory;
	private EventBusI						eventBus;
	private MqttManager						mqtt;
	private HttpManager						http;
	private SubscriptionManager				subscription;
	private DataManager						dataManager;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override public LoggerFactoryI getLoggerFactory() {
		return loggerFactory;
	}

	public void setLoggerFactory(LoggerFactoryI loggerFactory) {
		this.loggerFactory = loggerFactory;
	}

	@Override public TaskExecutorFactory getTaskExecutorFactory() {
		return taskExecutorFactory;
	}

	public void setTaskExecutorFactory(TaskExecutorFactory taskExecutorFactory) {
		this.taskExecutorFactory = taskExecutorFactory;
	}

	public EventBusI getEventBus() {
		return eventBus;
	}

	public void setEventBus(EventBusI eventBus) {
		this.eventBus = eventBus;
	}

	public MqttManager getMqtt() {
		return mqtt;
	}

	public void setMqtt(MqttManager mqtt) {
		this.mqtt = mqtt;
	}

	public HttpManager getHttp() {
		return http;
	}

	public void setHttp(HttpManager http) {
		this.http = http;
	}

	public SubscriptionManager getSubscription() {
		return subscription;
	}

	public void setSubscription(SubscriptionManager subscription) {
		this.subscription = subscription;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}
}
