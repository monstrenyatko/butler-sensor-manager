/*
 *******************************************************************************
 *
 * Purpose: Logger interface.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.dbg;

public interface LoggerI {

	public void trace(String message);

	public void trace(String message, Throwable e);

	public void debug(String message);

	public void debug(String message, Throwable e);

	public void warn(String message);

	public void warn(String message, Throwable e);

	public void error(String message);

	public void error(String message, Throwable e);

	public void fatal(String message);

	public void fatal(String message, Throwable e);

	public void info(String message);
}
