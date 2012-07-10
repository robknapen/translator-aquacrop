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
		Assert.assertEquals(1, DayNumbers.calculateDayNumber(1, 1, 1901));
		Assert.assertEquals(31, DayNumbers.calculateDayNumber(31, 1, 1901));
		Assert.assertEquals(365, DayNumbers.calculateDayNumber(31, 12, 1901));
		Assert.assertEquals(366, DayNumbers.calculateDayNumber(1, 1, 1902));
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testYearOutOfRange() {
		DayNumbers.calculateDayNumber(1, 1, 1900);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testDayOutOfRange() {
		DayNumbers.calculateDayNumber(0, 1, 1901);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDayInMonth() {
		DayNumbers.calculateDayNumber(29, 2, 1901);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testMonthOutOfRange() {
		DayNumbers.calculateDayNumber(1, 0, 1901);
	}
	
}
