/*
 *******************************************************************************
 *
 * Purpose: Event bus implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.event;

import java.util.concurrent.Executor;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.SubscriberExceptionHandler;

/**
 * Event Bus implementation.
 * 
 * @see EventBusI
 * @see AsyncEventBus
 */
public class EventBus extends AsyncEventBus implements EventBusI {

	/**
	 * @see AsyncEventBus#AsyncEventBus(Executor, SubscriberExceptionHandler)
	 */
	public EventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
		super(executor, subscriberExceptionHandler);
	}

	/**
	 * @see AsyncEventBus#post
	 */
	@Override public void post(EventI event) {
		super.post(event);
	}

	/**
	 * @see AsyncEventBus#register
	 */

	@Override public void register(EventListenerI subscriber) {
		super.register(subscriber);
		
	}

	/**
	 * @see AsyncEventBus#unregister
	 */
	@Override
	public void unregister(EventListenerI subscriber) {
		super.unregister(subscriber);
	}
}
