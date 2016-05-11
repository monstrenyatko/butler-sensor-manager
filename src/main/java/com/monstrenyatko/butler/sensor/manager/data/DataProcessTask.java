/*
 *******************************************************************************
 *
 * Purpose: Sensor data processing task implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.data;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataProcessTask extends DataTask {

	private byte[]						data;

	public DataProcessTask(DataManager manager, byte[] data) {
		super(manager);
		this.data = data;
	}

	@Override public void run() {
		log.debug("Process data");
		log.trace("Data: " + new String(data));
		try {
			JSONObject json = new JSONObject(new String(data));
			verifyVersion(json.getInt("v"));
			String id = json.getString("id");
			JSONArray dataArray = json.getJSONArray("data");
			for (int i = 0; i < dataArray.length(); i++) {
				JSONObject data = dataArray.getJSONObject(i);
				for (String k: data.keySet()) {
					manager.getProcessor().process(
						new DataSendTask(manager, new Data(id, DataType.get(k), data.getDouble(k)))
					);
				}
			}
		} catch (Exception e) {
			log.error("Can't process data, error: " + e.getMessage(), e);
		}
	}

	private void verifyVersion(int version) {
		if (version != 1) {
			throw new RuntimeException("Unsupported version");
		}
	}
}
