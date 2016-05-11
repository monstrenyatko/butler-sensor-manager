/*
 *******************************************************************************
 *
 * Purpose: Event bus interface.
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
 * Event Bus interface.
 */
public interface EventBusI {

	/**
	 * Posts an event to all registered subscribers.
	 * 
	 * @param event event to post.
	 */
	void post(EventI event);

	/**
	 * Registers all subscriber methods on object to receive events.
	 * 
	 * @param subscriber object whose subscriber methods should be registered.
	 */
	void register(EventListenerI subscriber);

	/**
	 * Unregisters all subscriber methods on a registered object.
	 * 
	 * @param subscriber object whose subscriber methods should be unregistered.
	 */
	void unregister(EventListenerI subscriber);
}
