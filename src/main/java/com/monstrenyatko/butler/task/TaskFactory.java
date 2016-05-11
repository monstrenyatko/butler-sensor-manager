/*
 *******************************************************************************
 *
 * Purpose: Task factory implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.task;

import java.util.concurrent.ThreadFactory;

import com.monstrenyatko.butler.utils.StringUtils;

/**
 * Task factory implementation.
 */
public class TaskFactory implements ThreadFactory {

	private int						counter = 0;

	public TaskFactory() {
	}

	@Override public Thread newThread(Runnable task) {
		return new Thread(null, task, StringUtils.makeThreadName(task.getClass()) + "-" + counter++);
	}
}
