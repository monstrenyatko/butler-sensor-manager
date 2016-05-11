/*
 *******************************************************************************
 *
 * Purpose: Task executor factory implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.task;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Task executors factory implementation.
 */
public final class TaskExecutorFactory {

	private final ScheduledThreadPoolExecutor		scheduledExecutor;
	private static final int						shutdownTmMs = 100;

	public TaskExecutorFactory(int minTaskQty) {
		scheduledExecutor = new ScheduledThreadPoolExecutor(minTaskQty, new TaskFactory());
	}

	public ScheduledThreadPoolExecutor getScheduled() {
		return scheduledExecutor;
	}

	public void shutdown() {
		shutdownScheduled();
	}

	private void shutdownScheduled() {
		// Cancel scheduled but not started task, and avoid new ones
		getScheduled().shutdown();
		// Wait for the running tasks
		try {
			getScheduled().awaitTermination(shutdownTmMs, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// ignore and stop executor right now
		}
		// Interrupt the threads and shutdown the scheduler
		getScheduled().shutdownNow();
	}
}
