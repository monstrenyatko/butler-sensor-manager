/*
 *******************************************************************************
 *
 * Purpose: Event interface.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.event;

/**
 * Event interface.
 */
public interface EventI {

	/**
	 * Returns a string representation of the event. Used for debug purpose.
	 * Please implement!
	 * 
	 * @return a string representation of the event.
	 */
	@Override String toString();

	/**
	 * Returns the event type.
	 * 
	 * @return event type.
	 */
	Object getType();

	/**
	 * Returns the event creation time-stamp
	 * 
	 * @return creation time-stamp
	 */
	long getTs();

	/**
	 * Returns the event data.
	 * 
	 * @return the event data or <b>null</b> if not available.
	 */
	Object getData();
}
