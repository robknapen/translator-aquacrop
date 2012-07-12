package org.agmip.translators.aquacrop.tools;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class EToCalculatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCaseA() {
		Assert.assertEquals(1.4, WeatherDataCalculator.calculateETReference( 16, 22.37, 222,  5.0, -5.0), 0.1);
		Assert.assertEquals(1.5, WeatherDataCalculator.calculateETReference( 47, 22.37, 222,  7.0,  0.0), 0.1);
		Assert.assertEquals(2.3, WeatherDataCalculator.calculateETReference( 75, 22.37, 222, 12.0,  2.0), 0.1);
		Assert.assertEquals(2.8, WeatherDataCalculator.calculateETReference(106, 22.37, 222, 15.0,  5.0), 0.1);
		Assert.assertEquals(3.5, WeatherDataCalculator.calculateETReference(136, 22.37, 222, 19.0,  8.0), 0.1);
		Assert.assertEquals(4.8, WeatherDataCalculator.calculateETReference(167, 22.37, 222, 25.0, 10.0), 0.1);
		Assert.assertEquals(5.4, WeatherDataCalculator.calculateETReference(197, 22.37, 222, 30.0, 15.0), 0.1);
		Assert.assertEquals(6.2, WeatherDataCalculator.calculateETReference(228, 22.37, 222, 35.0, 18.0), 0.1);
		Assert.assertEquals(4.6, WeatherDataCalculator.calculateETReference(259, 22.37, 222, 28.0, 13.0), 0.1);
		Assert.assertEquals(1.3, WeatherDataCalculator.calculateETReference(289, 22.37, 222, 13.0, 10.0), 0.1);
		Assert.assertEquals(0.8, WeatherDataCalculator.calculateETReference(320, 22.37, 222,  7.0,  5.0), 0.1);
		Assert.assertEquals(0.7, WeatherDataCalculator.calculateETReference(350, 22.37, 222,  3.0,  0.0), 0.1);
	}

	
	@Test
	public void testCaseB() {
		Assert.assertEquals(8.0, WeatherDataCalculator.calculateETReference( 15, -52.56, 3500,  40.0, 20.0), 0.1);
		Assert.assertEquals(6.5, WeatherDataCalculator.calculateETReference( 46, -52.56, 3500,  38.0, 18.0), 0.1);
		Assert.assertEquals(4.5, WeatherDataCalculator.calculateETReference( 74, -52.56, 3500,  35.0, 16.0), 0.1);
		Assert.assertEquals(2.4, WeatherDataCalculator.calculateETReference(105, -52.56, 3500,  30.0, 14.0), 0.1);
		Assert.assertEquals(0.8, WeatherDataCalculator.calculateETReference(135, -52.56, 3500,  20.0, 12.0), 0.1);
		Assert.assertEquals(0.4, WeatherDataCalculator.calculateETReference(166, -52.56, 3500,  15.0, 10.0), 0.1);
		Assert.assertEquals(0.4, WeatherDataCalculator.calculateETReference(196, -52.56, 3500,  12.0,  8.0), 0.1);
		Assert.assertEquals(1.0, WeatherDataCalculator.calculateETReference(227, -52.56, 3500,  17.0, 10.0), 0.1);
		Assert.assertEquals(1.9, WeatherDataCalculator.calculateETReference(258, -52.56, 3500,  20.0, 12.0), 0.1);
		Assert.assertEquals(3.6, WeatherDataCalculator.calculateETReference(288, -52.56, 3500,  25.0, 14.0), 0.1);
		Assert.assertEquals(5.6, WeatherDataCalculator.calculateETReference(319, -52.56, 3500,  30.0, 16.0), 0.1);
		Assert.assertEquals(7.2, WeatherDataCalculator.calculateETReference(349, -52.56, 3500,  35.0, 18.0), 0.1);
	}
	
}
