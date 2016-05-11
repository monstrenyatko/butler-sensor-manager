package com.monstrenyatko.butler.test.task;

import org.junit.Assert;
import org.junit.Test;

import com.monstrenyatko.butler.task.TaskProcessor;
import com.monstrenyatko.butler.test.Base;

public class TaskProcessorTest extends Base {
	private static final String		processorName = "TestTaskProcessor";

	@Test public void nameTest() {
		TaskProcessor processor = new TaskProcessor(getContext(), processorName);
		Assert.assertNotSame(processorName, processor.getName());
		Assert.assertNotEquals(processorName, processor.getName());
	}

	@Test public void startTest() throws InterruptedException {
		TaskProcessor processor = new TaskProcessor(getContext(), processorName);
		processor.start();
		Thread.sleep(10);
	}

	@Test public void instantShutdownTest() throws InterruptedException {
		TaskProcessor processor = new TaskProcessor(getContext(), processorName);
		processor.start();
		processor.shutdown();
		Assert.assertFalse("Must be terminated", processor.isAlive());
	}

	@Test public void delayedShutdownTest() throws InterruptedException {
		TaskProcessor processor = new TaskProcessor(getContext(), processorName);
		processor.start();
		Thread.sleep(10);
		processor.shutdown();
		Assert.assertFalse("Must be terminated", processor.isAlive());
	}

	private static class IntCounter {
		private int value;

		public IntCounter(int startValue) {
			this.value = startValue;
		}

		public void increment() {
			value++;
		}

		public int get() {
			return value;
		}
	}

	@Test public void processTest() throws InterruptedException {
		IntCounter counter = new IntCounter(0);
		final int qty = 5;
		TaskProcessor processor = new TaskProcessor(getContext(), processorName);
		processor.start();
		Thread.sleep(10);
		for(int i = 0; i < qty; i++) {
			processor.process(new Runnable() {

				private IntCounter counter = null;

				public Runnable init(IntCounter counter) {
					this.counter = counter;
					
					return this;
				}

				@Override public void run() {
					counter.increment();
				}
			}.init(counter));
		}
		processor.shutdown();
		Assert.assertEquals(qty, counter.get());
		Assert.assertFalse("Must be terminated", processor.isAlive());
	}

	@Test public void delayedProcessTest() throws InterruptedException {
		IntCounter counter = new IntCounter(0);
		final int qty = 5;
		TaskProcessor processor = new TaskProcessor(getContext(), processorName);
		for(int i = 0; i < qty; i++) {
			processor.process(new Runnable() {

				private IntCounter counter = null;

				public Runnable init(IntCounter counter) {
					this.counter = counter;
					
					return this;
				}

				@Override public void run() {
					counter.increment();
				}
			}.init(counter));
		}
		processor.start();
		Thread.sleep(10);
		processor.shutdown();
		Assert.assertEquals(qty, counter.get());
		Assert.assertFalse("Must be terminated", processor.isAlive());
	}
}
