/*
 *******************************************************************************
 *
 * Purpose: String utils implementation.
 *
 *******************************************************************************
 * Copyright Monstrenyatko 2016.
 *
 * Distributed under the MIT License.
 * (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT)
 *******************************************************************************
 */

package com.monstrenyatko.butler.utils;

public class StringUtils {

	public static String makeThreadName(Class<?> c) {
		return makeThreadName(c.getSimpleName());
	}

	public static String makeThreadName(String name) {
		return name + "-Th";
	}
}
