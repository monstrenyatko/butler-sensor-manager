/*
 *******************************************************************************
 *
 * Purpose: Application context interface.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.utils;

import com.monstrenyatko.butler.dbg.LoggerFactoryI;
import com.monstrenyatko.butler.task.TaskExecutorFactory;

public interface ContextI {

	public LoggerFactoryI getLoggerFactory();

	public TaskExecutorFactory getTaskExecutorFactory();
}
