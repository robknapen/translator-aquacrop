package org.agmip.translators.aquacrop.tools;


public class EToCalculator {

	/**
	 * Calculate ET reference.
	 * 
	 * @param day number (1 = 1 January … 365 = 31 December)
	 * @param latDeg Latitude in decimal degrees (+ for Northern hemisphere, - for Southern hemisphere)
	 * @param altitudeZ meter above sea level
	 * @param tMax Maximum air temperature (degrees C)
	 * @param tMin Minimum air temperature (degrees C)
	 * @return ET reference
	 */
	public static double calculateETReference(int day, double latDeg, int altitudeZ, double tMax, double tMin) {
		double pAtm = Math.exp(Math.log(101.3) + 5.26 * Math.log((293 - 0.0065 * altitudeZ) / 293));
		double psyConst = pAtm * 0.664742 / 1000;
		double tMean = (tMax + tMin) / 2;
		double delta = 4098 * (0.6108 * Math.exp((17.27 * tMean) / (tMean + 237.3))) / ((tMean + 237.3) * (tMean + 237.3));
		double esTx = 0.6108 * Math.exp((17.27 * tMax) / (tMax + 237.3));
		double esTn = 0.6108 * Math.exp((17.27 * tMin) / (tMin + 237.3));
		double es = (esTx + esTn) / 2;
		double ea = esTn;
		double u2 = 2.0;
		
		double ra = calculateExtraTerrestrialRadiation(day, latDeg);
		double rSo = (0.75 + (double) altitudeZ * 2.0 / 100000.0) * ra;
		double rs = 0.16 * Math.sqrt(tMax - tMin) * ra;
		
		double sbVal = (4.903 / 1000000000) * (Math.exp(4 * Math.log(tMax + 273.16)) + Math.exp(4 * Math.log(tMin + 273.16))) / 2;
		double rNl = sbVal * (0.34 - 0.14 * Math.sqrt(ea)) * (1.35 * (rs / rSo) - 0.35);
		double rN = (1 - 0.23) * rs - rNl;
		
		double etRef = (0.408 * delta * rN + psyConst * (900 / (tMean + 273)) * u2 * (es - ea)) / (delta + psyConst * (1 + 0.34 * u2));
		return etRef;
	}
	
	
	/**
	 * Calculate extra terrestrial radiation.
	 * 
	 * @param day number (1 = 1 January … 365 = 31 December)
	 * @param latDeg latitude in decimal degrees (+ for Northern hemisphere, - for Southern hemisphere)
	 * @return calculated extra terrestrial radiation (Ra)
	 */
	public static double calculateExtraTerrestrialRadiation(int day, double latDeg) {
		double latRad = Math.PI * latDeg/180;
		double dr = 1 + 0.033 * Math.cos(day * 2 * Math.PI / 365);
		double delta = 0.409 * Math.sin(day * 2 * Math.PI / 365 - 1.39);
		double tanTan = -Math.tan(latRad) * Math.tan(delta);
		
		double omegaS;
		if (tanTan > 1.0) {
			omegaS = 0.0;
		} else if (tanTan < -1.0) {
			omegaS = Math.PI;
		} else {
			omegaS = Math.acos(tanTan);
		}
		
		double ra = (24 * 60/Math.PI) * 0.0820 * dr * 
				(omegaS * Math.sin(latRad) * Math.sin(delta) + Math.cos(latRad) * Math.cos(delta) * Math.sin(omegaS));
		return ra;
	}
	
}
