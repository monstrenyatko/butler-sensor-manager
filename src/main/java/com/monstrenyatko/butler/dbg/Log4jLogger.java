/*
 *******************************************************************************
 *
 * Purpose: Logger implementation, based on Log4j.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.dbg;

import java.io.File;
import java.io.FileInputStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;


public class Log4jLogger implements LoggerI {

	private final Logger logger;

	public Log4jLogger(String className) {
		logger = LogManager.getLogger(className);
	}

	public static void configure(String configFile) throws Exception {
		ConfigurationSource source = new ConfigurationSource(
			ClassLoader.getSystemResourceAsStream("log4j2.xml")
		);
		if (configFile != null) {
			File file = new File(configFile);
			if (file.exists()) {
				source = new ConfigurationSource(
					new FileInputStream(file), file
				);
			}
		}
		Configurator.initialize(null, source);
	}

	public static void shutdown() {
		if( LogManager.getContext() instanceof LoggerContext ) {
			Configurator.shutdown((LoggerContext)LogManager.getContext());
		}
	}

	@Override public void trace(String message) {
		logger.log(Level.TRACE, message);
	}

	@Override public void trace(String message, Throwable e) {
		logger.log(Level.TRACE, message, e);
	}

	@Override public void debug(String message) {
		logger.log(Level.DEBUG, message);
	}

	@Override public void debug(String message, Throwable e) {
		logger.log(Level.DEBUG, message, e);
	}

	@Override public void warn(String message) {
		logger.log(Level.WARN, message);
	}

	@Override public void warn(String message, Throwable e) {
		logger.log(Level.WARN, message, e);
	}

	@Override public void error(String message) {
		logger.log(Level.ERROR, message);
	}

	@Override public void error(String message, Throwable e) {
		logger.log(Level.ERROR, message, e);
	}

	@Override public void fatal(String message) {
		logger.log(Level.FATAL, message);
	}

	@Override public void fatal(String message, Throwable e) {
		logger.log(Level.FATAL, message, e);
	}

	@Override public void info(String message) {
		logger.log(Level.INFO, message);
	}

}
