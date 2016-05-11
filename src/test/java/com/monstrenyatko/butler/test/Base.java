package com.monstrenyatko.butler.test;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.monstrenyatko.butler.dbg.Log4jLogger;
import com.monstrenyatko.butler.dbg.LoggerFactoryI;
import com.monstrenyatko.butler.dbg.LoggerI;
import com.monstrenyatko.butler.sensor.manager.SensorManagerContext;
import com.monstrenyatko.butler.task.TaskExecutorFactory;

@RunWith(MockitoJUnitRunner.class)
public abstract class Base {

	private final static int							TEST_EXECUTOR_TASK_QTY_MAX = 10;
	private final static long							TEST_EXEC_MAX_TIME_MS_DEFAULT = 5000;
	public final static long							TEST_WAIT_SMALL_MS = 10;
	public static final String							IRRELEVANT_EXCEPTION_MSG = "Test generated Exception";
	public static final String							FORBIDDEN_EXCEPTION_MSG = "Test generated Forbidden Exception";
	public static final String							SOME_NO_SPACE_NAME = "TestNoSpaceName";
	public static final String							SOME_STRING_DATA = "SomeStringData";
	public static final int								SOME_INT_DATA = 1234;
	public static final String							UNREACHABLE_PLACE_MSG = "We must not be here";

	private final LoggerFactoryI loggerFactory = makeLoggerFactory();
	private final LoggerI LOGGER = loggerFactory.getLogger(this.getClass());
	private final TaskExecutorFactory taskExecutorFactory = makeTaskExecutorFactory();
	private SensorManagerContext ctx;

	private LoggerFactoryI makeLoggerFactory() {
		// Setup log factory
		return new LoggerFactoryI() {

			@Override public LoggerI getLogger(Class<?> c) {
				return getLogger(c.getName());
			}

			@Override public LoggerI getLogger(String className) {
				return new Log4jLogger(className);
			}

			@Override public void configure(String configFile) throws Exception {
			}

			@Override public void shutdown() {
			}
		};
	}

	private TaskExecutorFactory makeTaskExecutorFactory() {
		return new TaskExecutorFactory(TEST_EXECUTOR_TASK_QTY_MAX);
	}

	@Rule public TestWatcher watcher = new TestWatcher()
	{
		@Override
		protected void starting(Description description) {
			LOGGER.info("STARTING TEST: " + description.getMethodName());
		}
	};

	@Rule public Timeout testTimeout;

	private void setTestTimeout(long testExecMaxTimeMs) {
		testTimeout = new Timeout(testExecMaxTimeMs, TimeUnit.MILLISECONDS);
	}

	public Base() {
		setTestTimeout(TEST_EXEC_MAX_TIME_MS_DEFAULT);
	}

	public Base(long testExecMaxTimeMs) {
		setTestTimeout(testExecMaxTimeMs);
	}

	private void reset() {
		ctx = null;
	}

	@Before public void initCommonBase() {
		reset();
		ctx = new SensorManagerContext();
		ctx.setLoggerFactory(loggerFactory);
		ctx.setTaskExecutorFactory(taskExecutorFactory);
	}

	public SensorManagerContext getContext() {
		return ctx;
	}

	public LoggerI getLogger() {
		return LOGGER;
	}
}
