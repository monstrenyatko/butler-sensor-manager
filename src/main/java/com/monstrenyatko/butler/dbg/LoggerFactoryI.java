/*
 *******************************************************************************
 *
 * Purpose: Logger factory interface.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.dbg;

public interface LoggerFactoryI {

	/**
	 * Configures logger.
	 * 
	 * Must be called before first use of {@link #getLogger}
	 * 
	 * @param configFile path to logger configuration file. If null the default
	 * is used
	 * 
	 * @throws Exception logger configuration problem
	 */
	void configure(final String configFile) throws Exception;

	/**
	 * Shuts down logger.
	 */
	void shutdown();

	/**
	 * Return logger instance for class
	 * 
	 * @param className
	 * @return LoggerI
	 */
	LoggerI getLogger(String className);

	/**
	 * Return logger instance for class
	 * 
	 * @param c instance of the class
	 * @return LoggerI
	 */
	LoggerI getLogger(Class<?> c);
}
