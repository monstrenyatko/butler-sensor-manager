package com.monstrenyatko.butler.test.event;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;
import com.monstrenyatko.butler.event.EventBus;
import com.monstrenyatko.butler.event.EventBusI;
import com.monstrenyatko.butler.event.EventI;
import com.monstrenyatko.butler.event.EventListenerI;
import com.monstrenyatko.butler.event.EventSubscriberExceptionHandler;
import com.monstrenyatko.butler.test.Base;

public class EventBusTest extends Base {

	private int exceptionCounter;
	private int eventCounter;
	private int eventCounterString;
	private int eventCounterInteger;
	private String stringData;

	class StringTestEvent implements EventI {
		private String data;

		StringTestEvent(String data) {
			this.data = data;
		}

		@Override public String toString() {
			return "StringTestEvent[data: " + data + "]";
		}

		@Override public Object getType() {
			return null;
		}

		@Override public String getData() {
			return data;
		}


		@Override public long getTs() {
			return 0;
		}
	}

	class IntegerTestEvent implements EventI {
		private Integer data;

		IntegerTestEvent(Integer data) {
			this.data = data;
		}

		@Override public String toString() {
			return "IntegerTestEvent[data: " + data + "]";
		}

		@Override public Object getType() {
			return null;
		}

		@Override public Integer getData() {
			return data;
		}


		@Override public long getTs() {
			return 0;
		}
	}

	@Test public void functionalTest() throws InterruptedException {
		eventCounter = 0;
		stringData = null;
		EventBusI eb = new EventBus(getContext().getTaskExecutorFactory().getScheduled(),
				new EventSubscriberExceptionHandler(getContext()));
		eb.register(new EventListenerI() {

			@Override public String toString() {
				return "TestEventListener";
			}

			@Subscribe public void onEvent(StringTestEvent s) {
				eventCounter++;
				stringData = s.getData();
			}
		});
		eb.post(new StringTestEvent(SOME_STRING_DATA));
		Thread.sleep(TEST_WAIT_SMALL_MS);
		Assert.assertEquals("Wrong received event qty", 1, eventCounter);
		Assert.assertEquals("Wrong received event data", SOME_STRING_DATA, stringData);
	}

	@Test public void generalSubscriberTest() throws InterruptedException {
		eventCounter = 0;
		EventBusI eb = new EventBus(getContext().getTaskExecutorFactory().getScheduled(),
				new EventSubscriberExceptionHandler(getContext()));
		eb.register(new EventListenerI() {
			@Override public String toString() {
				return "TestEventListener";
			}

			@Subscribe public void onEvent(EventI s) {
				eventCounter++;
			}
		});
		eb.post(new StringTestEvent(SOME_STRING_DATA));
		eb.post(new IntegerTestEvent(SOME_INT_DATA));
		eb.post(new StringTestEvent(SOME_STRING_DATA));
		Thread.sleep(TEST_WAIT_SMALL_MS);
		Assert.assertEquals("Wrong received event qty", 3, eventCounter);
	}

	@Test public void multiPointSubscriberTest() throws InterruptedException {
		eventCounter = 0;
		eventCounterString = 0;
		eventCounterInteger = 0;
		EventBusI eb = new EventBus(getContext().getTaskExecutorFactory().getScheduled(),
				new EventSubscriberExceptionHandler(getContext()));
		eb.register(new EventListenerI() {
			@Override public String toString() {
				return "TestEventListener";
			}

			@Subscribe public void onEvent(EventI s) {
				eventCounter++;
			}

			@Subscribe public void onEvent(StringTestEvent s) {
				eventCounterString++;
			}

			@Subscribe public void onEvent(IntegerTestEvent s) {
				eventCounterInteger++;
			}
		});
		eb.post(new StringTestEvent(SOME_STRING_DATA));
		eb.post(new IntegerTestEvent(SOME_INT_DATA));
		Thread.sleep(TEST_WAIT_SMALL_MS);
		Assert.assertEquals("Wrong received event qty", 2, eventCounter);
		Assert.assertEquals("Wrong received String event qty", 1, eventCounterString);
		Assert.assertEquals("Wrong received Integer event qty", 1, eventCounterInteger);
	}

	@Test public void exceptionSubscriberTest() throws InterruptedException {
		exceptionCounter = 0;
		EventBusI eb = new EventBus(getContext().getTaskExecutorFactory().getScheduled(),
				new EventSubscriberExceptionHandler(getContext())
		{
			@Override public void handleException(Throwable e, EventI event, EventListenerI listener) {
				exceptionCounter++;
			}
		});
		eb.register(new EventListenerI() {
			@Override public String toString() {
				return "TestEventListener";
			}

			@Subscribe public void onEvent(EventI s) {
				throw new RuntimeException(IRRELEVANT_EXCEPTION_MSG);
			}
		});
		eb.post(new StringTestEvent(SOME_STRING_DATA));
		Thread.sleep(TEST_WAIT_SMALL_MS);
		Assert.assertEquals("Wrong exception qty", 1, exceptionCounter);
	}
}
