/*
 *******************************************************************************
 *
 * Purpose: Main function implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager;

import java.io.File;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import com.monstrenyatko.butler.sensor.manager.net.SubscriptionManager;
import com.monstrenyatko.butler.sensor.manager.net.http.HttpManager;
import com.monstrenyatko.butler.sensor.manager.net.mqtt.MqttManager;
import com.monstrenyatko.butler.task.TaskExecutorFactory;
import com.monstrenyatko.butler.task.TaskProcessor;
import com.monstrenyatko.butler.dbg.LoggerFactory;
import com.monstrenyatko.butler.dbg.LoggerI;
import com.monstrenyatko.butler.event.EventBus;
import com.monstrenyatko.butler.event.EventSubscriberExceptionHandler;
import com.monstrenyatko.butler.sensor.manager.config.Configuration;
import com.monstrenyatko.butler.sensor.manager.data.DataManager;
import com.monstrenyatko.butler.utils.StringUtils;

/**
 * Main class of Butler Sensor Manager application.
 */
public class SensorManager extends Thread {

	private LoggerI							log;
	private final Destructor				destructor = new Destructor();
	private final SensorManagerContext		ctx = new SensorManagerContext();

	/**
	 * Application entry point.
	 * See {@link #run()} method.
	 * 
	 * @param args command-line arguments
	 * @throws Exception unexpected execution problem
	 */
	public static void main(String[] args) throws Exception {
		new SensorManager().start();
	}

	/**
	 * Initialize and loads configuration.
	 */
	private void initConfiguration() {
		ctx.setConfiguration(new Configuration());
	}

	/**
	 * Initialize logger.
	 * 
	 * Must be called before first use of {@link LogManager#getLogger}
	 * 
	 * @throws Exception logger configuration problem
	 */
	private void initLogger() throws Exception {
		ctx.setLoggerFactory(new LoggerFactory(ctx));
		String configFile = null;
		if (ctx.getConfiguration().main().configurationDir != null) {
			configFile = ctx.getConfiguration().main().configurationDir
				+ File.separator + ctx.getConfiguration().main().loggerConfigurationFileName;
		}
		ctx.getLoggerFactory().configure(configFile);
		log = ctx.getLoggerFactory().getLogger(this.getClass());
	}

	/**
	 * Shuts down logger.
	 */
	private void shutdownLogger() {
		log.debug("Shutting down logger");
		ctx.getLoggerFactory().shutdown();
	}

	/**
	 * Adds hook on Shutdown event.
	 * Calls {@link SensorManager#onDestroy()} method.
	 */
	private class Destructor {

		private Thread hook = new Thread() {

			public Thread init()
			{
				this.setName(StringUtils.makeThreadName(Destructor.class));
				return this;
			}

			/**
			 * It will be the last thread of the application
			 */
			@Override public void run() {
				try {
					// notify application
					onDestroy();
					// wait until done
					SensorManager.this.join();
					// flush logs
					shutdownLogger();
					// done
					log.debug("Destroyed");
				} catch (Exception e) {
					log.error("Error", e);
				} catch (Throwable e) {
					log.fatal("Critical error", e);
				}
			}
		}.init();

		public synchronized void arm() {
			Runtime.getRuntime().addShutdownHook(hook);
		}
	}

	/**
	 * Constructor
	 * @throws Exception unexpected problem
	 */
	private SensorManager() throws Exception {
		super(StringUtils.makeThreadName(SensorManager.class));
		initConfiguration();
		initLogger();
	}

	/**
	 * Main thread entry point
	 */
	@Override public void run() {
		try {
			log.debug("Initializing");
			onInit();
			log.debug("Starting");
			onStart();
			destructor.arm();
			log.debug("Working");
			work();
			log.debug("Finishing");
			onStop();
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Throwable e) {
			log.fatal("Critical error", e);
		}
		log.debug("Done");
	}

	/**
	 * Called just before application destroy by {@link Destructor}.
	 * Could be used to do graceful shutdown.
	 */
	private synchronized void onDestroy() {
		log.debug("Request to Exit");
		interrupt();
	}

	private void work() {
		try {
			Object idle = new Object();
			synchronized (idle) {
				// wait until end
				idle.wait();
			}
		} catch (InterruptedException e) {
			log.debug("Work has been interrupted");
		}
	}

	private synchronized void onInit() throws Exception {
		ctx.setTaskExecutorFactory(new TaskExecutorFactory(
			ctx.getConfiguration().main().threadPullSize
		));
		ctx.setEventBus(new EventBus(ctx.getTaskExecutorFactory().getScheduled(),
			new EventSubscriberExceptionHandler(ctx)
		));
		ctx.setMqtt(new MqttManager(
			ctx,
			new TaskProcessor(ctx, "MQTT"),
			new MqttAsyncClient(
				ctx.getConfiguration().net().mqtt().serverURI,
				ctx.getConfiguration().net().mqtt().clientId,
				new MqttDefaultFilePersistence
					(ctx.getConfiguration().net().mqtt().persistenceDir)
		)));
		ctx.setHttp(new HttpManager(ctx));
		ctx.setSubscription(new SubscriptionManager(ctx));
		ctx.setDataManager(new DataManager(ctx, new TaskProcessor(ctx, "DATA")));
	}

	private synchronized void onStart() throws Exception {
		ctx.getDataManager().start();
		ctx.getMqtt().start();
	}

	private synchronized void onStop() {
		ctx.getMqtt().stop();
		ctx.getDataManager().stop();
		ctx.getTaskExecutorFactory().shutdown();
	}
}
