/*
 *******************************************************************************
 *
 * Purpose: Event bus listener exception handler implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.event;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.monstrenyatko.butler.dbg.LoggerI;
import com.monstrenyatko.butler.utils.ContextI;

public class EventSubscriberExceptionHandler implements SubscriberExceptionHandler {

	private final LoggerI log;

	public EventSubscriberExceptionHandler(ContextI ctx) {
		log = ctx.getLoggerFactory().getLogger(this.getClass().getSimpleName());
	}

	@Override public void handleException(Throwable e, SubscriberExceptionContext eCtx) {
		handleException(e, (EventI)eCtx.getEvent(), (EventListenerI)eCtx.getSubscriber());
	}

	public void handleException(Throwable e, EventI event, EventListenerI listener) {
		log.error("Event, name: " + event + ", client: " + listener
		+ ", error: " + e.getMessage(), e);
	}
}
