/*
 *******************************************************************************
 *
 * Purpose: Event listener interface.
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
 * Event listener interface.
 */
public interface EventListenerI {

	/**
	 * Returns a string representation of the listener. Used for debug purpose.
	 * Please implement!
	 * 
	 * @return a string representation of the listener.
	 */
	@Override String toString();
}
