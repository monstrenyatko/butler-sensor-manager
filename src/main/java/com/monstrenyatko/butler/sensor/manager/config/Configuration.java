/*
 *******************************************************************************
 *
 * Purpose: Sensor manager configuration implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

/**
 * Reads and keeps configuration.
 */
public class Configuration {

	private final Main						main = new Main();
	private final Net						net = new Net();

	public static class Main {
		public final String					configurationDir = System.getenv("BUTLER_SENSOR_MANAGER_CONFIG_DIR");
		public final String					configurationFileName = "config.json";
		public final String					loggerConfigurationFileName = "log4j2.xml";
		public int							threadPullSize = 5;
	}

	public static class Net {
		private final Mqtt					mqtt = new Mqtt();
		private final DataDb				dataDb = new DataDb();

		public static class Mqtt {
			public String					serverURI = "tcp://pi";
			public String					clientId = "BUTLER_SENSOR_MANAGER";
			public String					persistenceDir = System.getProperty("user.dir")
											+ File.separator + "target" + File.separator + "mqtt";
			public int						connectionTimeoutSec = 15;
			public int						connectionRetryDelaySec = 5;
			public int						keepAliveIntervalSec = 60;
		}

		public static class DataDb {
			public String					serverURI = "http://pi:8086";
		}

		public Mqtt mqtt() {
			return mqtt;
		}

		public DataDb dataDb() {
			return dataDb;
		}
	}

	/**
	 * Gets {@code main} configuration.
	 * 
	 * @return {@code main} configuration.
	 */
	public Main main() {
		return main;
	}

	/**
	 * Gets {@code net} configuration.
	 * 
	 * @return {@code net} configuration.
	 */
	public Net net() {
		return net;
	}

	/**
	 * Constructor.
	 */
	public Configuration() {
		load();
	}

	private void load() {
		try {
			File file = new File(
				main().configurationDir
				+ File.separator + main().configurationFileName
			);
			if (file.exists()) {
				JSONObject json = new JSONObject(new String(Files.readAllBytes(Paths.get(file.getAbsolutePath()))));
				if (json.has("net")) {
					JSONObject net = json.getJSONObject("net");
					if (net.has("mqtt")) {
						JSONObject mqtt = net.getJSONObject("mqtt");
						net().mqtt().persistenceDir = mqtt.getString("persistence_dir");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
