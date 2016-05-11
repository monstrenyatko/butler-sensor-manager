package com.monstrenyatko.butler.test.utils;

import org.junit.Assert;
import org.junit.Test;

import com.monstrenyatko.butler.test.Base;
import com.monstrenyatko.butler.utils.StringUtils;


public class StringUtilsTest extends Base {

	class TestName {}

	@Test public void makeThreadNameTest() {
		final String name1 = "name1";
		final String thName1 = StringUtils.makeThreadName(name1);
		Assert.assertNotSame(name1, thName1);
		Assert.assertNotEquals(name1, thName1);

		final String thName2 = StringUtils.makeThreadName(TestName.class);
		Assert.assertNotEquals(TestName.class.getName(), thName2);
		Assert.assertNotEquals(TestName.class.getSimpleName(), thName2);
	}
}
