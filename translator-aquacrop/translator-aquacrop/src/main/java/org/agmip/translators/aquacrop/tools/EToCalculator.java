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
		//		Patm := EXP(Ln(101.3) + 5.26 * Ln((293-0.0065*AltitudeZ)/293));
		double pAtm = Math.exp(Math.log(101.3) + 5.26 * Math.log((293 - 0.0065 * altitudeZ) / 293));
		
		//		PsyConst := Patm * 0.664742/1000;
		double psyConst = pAtm * 0.664742 / 1000;
		
		//		Tmean := (Tmax+Tmin)/2;
		double tMean = (tMax + tMin) / 2;
		
		//		Delta := 4098 * (0.6108 * EXP((17.27*Tmean)/(Tmean+237.3)))/( (Tmean+237.3)*(Tmean+237.3) )
		double delta = 4098 * (0.6108 * Math.exp((17.27 * tMean) / (tMean + 237.3))) / ((tMean + 237.3) * (tMean + 237.3));
		
		//		EsTx := 0.6108 * EXP((17.27 * Tmax)/(Tmax+237.3));
		double esTx = 0.6108 * Math.exp((17.27 * tMax) / (tMax + 237.3));
		
		//		EsTn := 0.6108 * EXP((17.27 * Tmin)/(Tmin+237.3));
		double esTn = 0.6108 * Math.exp((17.27 * tMin) / (tMin + 237.3));
		
		//		Es := (EsTx + EsTn)/2;
		double es = (esTx + esTn) / 2;
		
		//		Ea := EsTn ;
		double ea = esTn;
		
		//		U2 := 2;
		double u2 = 2.0;
		
		//		CalculateExtraTerrestrialRadiation(Dayi,LatitudeDeg,Ra);
		double ra = calculateExtraTerrestrialRadiation(day, latDeg);
		
		//		Rso := (0.75 + AltitudeZ* 2/100000)*Ra ;
		double rSo = (0.75 + altitudeZ * 2 / 100000) * ra;
		
		//		Rs := 0.16 * SQRT(Tmax-Tmin) * Ra;
		double rs = 0.16 * Math.sqrt(tMax - tMin) * ra;
		
		//		SBval := (4.903/1000000000) * (EXP(4*Ln(Tmax+273.16)) + EXP(4*Ln(Tmin+273.16)))/2;
		double sbVal = (4.903 / 1000000000) * (Math.exp(4 * Math.log(tMax + 237.16)) + Math.exp(4 * Math.log(tMin + 273.16))) / 2;
		
		//		Rnl := SBval * (0.34 - 0.14 * SQRT(Ea)) * (1.35 * (Rs/Rso) - 0.35);
		double rNl = sbVal * (0.34 - 0.14 * Math.sqrt(ea)) * (1.35 * (rs / rSo) - 0.35);
		
		//		Rn := (1 – 0.23)*Rs - Rnl;
		double rN = (1 - 0.23) * rs - rNl;
		
		//		ETReference:= (0.408 * Delta * Rn + PsyConst * (900/(Tmean+273))*U2*(Es-Ea))/(Delta + PsyConst * (1+0.34*U2));
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
		double latRad = Math.PI * latDeg/180.0;
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
		
		double ra = (24 * 60/Math.PI) * 0.0820 * dr * (omegaS * Math.sin(latRad) * Math.sin(delta) + Math.cos(latRad) * Math.cos(delta) * Math.sin(omegaS));
		return ra;
	}
	
}
