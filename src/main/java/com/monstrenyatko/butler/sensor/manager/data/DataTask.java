/*
 *******************************************************************************
 *
 * Purpose: Sensor data task abstract implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.data;

import com.monstrenyatko.butler.dbg.LoggerI;

public abstract class DataTask implements Runnable {

	protected final DataManager					manager;
	protected final LoggerI						log;

	public DataTask(DataManager manager) {
		this.manager = manager;
		log = this.manager.getContext().getLoggerFactory().getLogger(this.getClass());
	}
}
