/*
 *******************************************************************************
 *
 * Purpose: MQTT connection retry task implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.mqtt;

import java.util.concurrent.TimeUnit;

public class MqttConnectionRetryTask extends MqttTask {

	private class MqttConnectionRetryTimer implements Runnable {
		@Override public void run() {
			manager.getProcessor().process(new MqttConnectTask(manager));
		}
	}

	public MqttConnectionRetryTask(MqttManager manager) {
		super(manager);
	}
	
	@Override public void run() {
		try {
			log.info("Schedule retry in "
					+ manager.getContext().getConfiguration().net().mqtt().connectionRetryDelaySec + " sec");
			manager.getContext().getTaskExecutorFactory().getScheduled().schedule(
					new MqttConnectionRetryTimer(),
					manager.getContext().getConfiguration().net().mqtt().connectionRetryDelaySec,
					TimeUnit.SECONDS
			);
		} catch (Exception e) {
			log.error("Error", e);
		} catch (Throwable e) {
			log.fatal("Critical error", e);
		}
	}
}
