/*
 *******************************************************************************
 *
 * Purpose: HTTP service implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.sensor.manager.net.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.monstrenyatko.butler.dbg.LoggerI;
import com.monstrenyatko.butler.sensor.manager.SensorManagerContext;

public class HttpManager {

	private final SensorManagerContext			ctx;
	private final LoggerI						log;

	public HttpManager(SensorManagerContext ctx) {
		this.ctx = ctx;
		log = this.ctx.getLoggerFactory().getLogger(this.getClass());
	}

	public void post(String url, String urlParams, byte[] data)
			throws MalformedURLException, IOException
	{
		HttpURLConnection connection = (HttpURLConnection)
				new URL(url + "?" + urlParams).openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.write(data);
		wr.flush();
		wr.close();

		int responseCode = connection.getResponseCode();
		log.debug("Sending 'POST' request to URL : " + url);
		log.trace("Post parameters : " + urlParams);
		log.trace("Post body : " + new String(data));
		log.debug("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		log.trace("Response : " + response.toString());
	}
}
