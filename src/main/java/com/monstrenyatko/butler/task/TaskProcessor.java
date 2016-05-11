/*
 *******************************************************************************
 *
 * Purpose: Task processor implementation.
 * Executes tasks one-by-one in single thread.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.task;

import java.util.concurrent.LinkedBlockingQueue;

import com.monstrenyatko.butler.dbg.LoggerI;
import com.monstrenyatko.butler.utils.ContextI;
import com.monstrenyatko.butler.utils.StringUtils;

/**
 * Command Processor pattern implementation.
 */
public class TaskProcessor {
	private static final String		namePrefix = "-TaskProc";
	private static final int		shutdownTmMs = 100;
	private final String			name;
	private final LoggerI			log;
	private final Processor			processor;

	/**
	 * Constructor.
	 * 
	 * @param name base for string name of the processor
	 */
	public TaskProcessor(ContextI ctx, final String name) {
		this.name = name + namePrefix;
		log = ctx.getLoggerFactory().getLogger(getName());
		processor = new Processor(getName());
	}

	/**
	 * Gets name of the processor.
	 * Value based on the {@code name} parameter from {@link #TaskProcessor(String)}
	 * 
	 * @return name of the processor
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds new task to processing queue.
	 * 
	 * @param task new task instance
	 */
	public void process(Runnable task)
	{
		processor.execute(task);
	}

	/**
	 * Starts processing.
	 */
	public void start() {
		processor.start();
	}

	/**
	 * Shuts down processing.
	 */
	public void shutdown() {
		processor.shutdown();
	}

	/**
	 * Tests if this thread is alive.
	 *
	 * @return  <code>true</code> if this thread is alive;
	 *          <code>false</code> otherwise.
	 */
	public boolean isAlive() {
		return processor.isAlive();
	}

	/**
	 * Processor implementation.
	 */
	private class Processor extends Thread {
		private LinkedBlockingQueue<Runnable>	queue = new LinkedBlockingQueue<>();
		private boolean							exit = false;

		private class QueueTakeUnblocker implements Runnable {
			@Override
			public void run() {
			}
		}

		private Processor(final String name) {
			super(StringUtils.makeThreadName(name));
		}

		public void execute(Runnable task) {
			if (task != null && !queue.offer(task)) {
				log.error("Can't push to the queue, size: " + queue.size());
			}
		}

		/** Working thread. */
		@Override
		public void run() {
			log.debug("Started");
			try {
				try {
					while (!exit) {
						processOne((Runnable) queue.take());
					}
					log.debug("Finishing");
				} catch (InterruptedException e) {
					log.debug("Interrupted => Finishing");
				}
				log.debug("Finish all tasks");
				processAll();
			} catch (Exception e) {
				log.error("Error", e);
			} catch (Throwable e) {
				log.fatal("Critical error", e);
			}
			log.debug("Done");
		}

		public void shutdown() {
			log.debug("Shutdown");
			exit = true;
			execute(new QueueTakeUnblocker());
			try {
				join(shutdownTmMs);
				if (isAlive()) {
					log.warn("Thread is not terminated");
				}
			} catch (InterruptedException e) {
				log.warn("join has been interrupted");
			}
		}

		private void processAll() {
			while (!queue.isEmpty()) {
				processOne(queue.poll());
			}
		}

		private void processOne(Runnable task) {
			try {
				process(task);
			} catch (Exception e) {
				log.error("task error", e);
			}
		}

		private void process(Runnable task) throws Exception {
			if (task != null) {
				task.run();
			}
		}
	}
}
