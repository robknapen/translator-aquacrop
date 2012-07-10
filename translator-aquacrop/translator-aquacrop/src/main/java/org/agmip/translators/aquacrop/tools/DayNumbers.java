package org.agmip.translators.aquacrop.tools;

public class DayNumbers {

	private static final double ELAPSED_DAYS[] = { 0.0, 31.0, 59.25, 90.25,
			120.25, 151.25, 181.25, 212.25, 243.25, 273.25, 304.25, 334.25 };

	/**
	 * AquaCrop uses day numbers to specify the start and end of the simulation
	 * period and of the growing cycle.
	 * 
	 * The day number refers to the days elapsed since 0th January 1901 at 0 am.
	 * The method is valid from 1901 to 2099.
	 * 
	 * @param day
	 *            in month (starts at 1)
	 * @param month
	 *            of year (starts at 1)
	 * @param year
	 * @return calculated day number for AquaCrop
	 */
	public static long dayNumberFor(int day, int month, int year) {
		return (long) ((year - 1901) * 365.25 + ELAPSED_DAYS[month - 1] + day + 0.05);
	}

}
