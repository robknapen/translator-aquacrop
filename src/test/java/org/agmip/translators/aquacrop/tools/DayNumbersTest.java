package org.agmip.translators.aquacrop.tools;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DayNumbersTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDayNumberCalculations() {
		Assert.assertEquals(1, DateFunctions.calculateDayNumber(1, 1, 1901, true));
		Assert.assertEquals(31, DateFunctions.calculateDayNumber(31, 1, 1901, true));
		Assert.assertEquals(365, DateFunctions.calculateDayNumber(31, 12, 1901, true));
		Assert.assertEquals(366, DateFunctions.calculateDayNumber(1, 1, 1902, true));
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testYearOutOfRange() {
		DateFunctions.calculateDayNumber(1, 1, 1900, true);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testDayOutOfRange() {
		DateFunctions.calculateDayNumber(0, 1, 1901, true);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDayInMonth() {
		DateFunctions.calculateDayNumber(29, 2, 1901, true);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testMonthOutOfRange() {
		DateFunctions.calculateDayNumber(1, 0, 1901, true);
	}
	
}
