/*
 *******************************************************************************
 *
 * Purpose: Logger factory implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.dbg;

import com.monstrenyatko.butler.utils.ContextI;

public class LoggerFactory implements LoggerFactoryI {

	protected ContextI ctx;
	
	public LoggerFactory(ContextI ctx) {
		this.ctx = ctx;
	}

	/**
	 * @see LoggerFactoryI#configure()
	 */
	@Override public void configure(String configFile) throws Exception {
		Log4jLogger.configure(configFile);
	}

	/**
	 * @see LoggerFactoryI#shutdown()
	 */
	@Override public void shutdown() {
		Log4jLogger.shutdown();
	}

	/**
	 * @see LoggerFactoryI#getLogger(String)
	 */
	@Override public LoggerI getLogger(String className) {
		return new Log4jLogger(className);
	}

	/**
	 * @see LoggerFactoryI#getLogger(Class)
	 */
	@Override public LoggerI getLogger(Class<?> c) {
		return new Log4jLogger(c.getName());
	}

}
