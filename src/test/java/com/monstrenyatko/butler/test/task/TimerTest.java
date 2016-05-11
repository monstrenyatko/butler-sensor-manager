package com.monstrenyatko.butler.test.task;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.monstrenyatko.butler.task.TaskExecutorFactory;
import com.monstrenyatko.butler.test.Base;

public class TimerTest extends Base {

	@Test public void processTest() throws InterruptedException {
		final int timeout1Ms = 10;
		final int timeout2Ms = 20;
		final int checkTimeoutMs = timeout2Ms + 5;
		final int checkQty = 2;
		final Semaphore lock = new Semaphore(0);
		TaskExecutorFactory processor = getContext().getTaskExecutorFactory();
		Runnable timer = new Runnable() {

			@Override public void run() {
				lock.release();
		}};
		processor.getScheduled().schedule(timer, timeout1Ms, TimeUnit.MILLISECONDS);
		processor.getScheduled().schedule(timer, timeout2Ms, TimeUnit.MILLISECONDS);
		Assert.assertTrue("Must be procesed",
			lock.tryAcquire(checkQty, checkTimeoutMs, TimeUnit.MILLISECONDS));
	}

	@Test public void processKoTest() throws InterruptedException {
		final int timeoutMs = 10;
		final int checkTimeoutMs = timeoutMs - 5;
		final Semaphore lock = new Semaphore(0);
		TaskExecutorFactory processor = getContext().getTaskExecutorFactory();
		Runnable timer = new Runnable() {

			@Override public void run() {
				lock.release();
		}};
		processor.getScheduled().schedule(timer, timeoutMs, TimeUnit.MILLISECONDS);
		Assert.assertFalse("Must not be procesed", lock.tryAcquire(checkTimeoutMs, TimeUnit.MILLISECONDS));
	}
}
