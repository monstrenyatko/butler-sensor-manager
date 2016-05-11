/*
 *******************************************************************************
 *
 * Purpose: Sensor data processor implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.data;

import com.google.common.eventbus.Subscribe;
import com.monstrenyatko.butler.dbg.LoggerI;
import com.monstrenyatko.butler.event.EventListenerI;
import com.monstrenyatko.butler.sensor.manager.SensorManagerContext;
import com.monstrenyatko.butler.sensor.manager.event.EventSensorDataRecv;
import com.monstrenyatko.butler.task.TaskProcessor;

public class DataManager {

	private final SensorManagerContext			ctx;
	private final LoggerI						log;
	private final TaskProcessor					processor;
	private final EventListenerI				eventListener = new EventListenerI() {

		@Subscribe public void onSensorDataRecv(EventSensorDataRecv event) {
			log.debug("Sensor data is received");
			log.trace("Add event to process queue: " + event);
			getProcessor().process(new DataProcessTask(DataManager.this, event.getData()));
		}
	};

	public DataManager(SensorManagerContext ctx, TaskProcessor processor) {
		this.ctx = ctx;
		this.processor = processor;
		log = this.ctx.getLoggerFactory().getLogger(this.getClass());
		this.ctx.getEventBus().register(eventListener);
	}

	public synchronized void start() {
		log.debug("Start");
		processor.start();
	}

	public synchronized void stop() {
		log.debug("Stop");
		processor.shutdown();
	}

	public TaskProcessor getProcessor() {
		return processor;
	}

	public SensorManagerContext getContext() {
		return ctx;
	}
}
