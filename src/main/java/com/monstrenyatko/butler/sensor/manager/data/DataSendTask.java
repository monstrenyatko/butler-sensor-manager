/*
 *******************************************************************************
 *
 * Purpose: Sensor data transmission task implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.data;

public class DataSendTask extends DataTask {

	private Data						data;

	public DataSendTask(DataManager manager, Data data) {
		super(manager);
		this.data = data;
	}

	@Override public void run() {
		try {
			log.debug("Sending data");
			log.trace("Data: " + data);
			verifyDataType(data.getType());
			String payload = data.getType() + ",id=" + data.getSensorId()
				+ " " + "value=" + data.getValue();
			manager.getContext().getHttp().post(
				manager.getContext().getConfiguration().net().dataDb().serverURI + "/write",
				"db=butler", payload.getBytes()
			);
		} catch (Exception e) {
			log.error("Can't send data, error: " + e.getMessage(), e);
		}
	}

	void verifyDataType(DataType v) {
		if (v == DataType.UNKNOWN) {
			throw new RuntimeException("Unknown data type");
		}
	}
}
