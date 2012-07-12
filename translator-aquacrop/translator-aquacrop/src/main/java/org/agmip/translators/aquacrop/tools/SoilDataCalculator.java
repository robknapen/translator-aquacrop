package org.agmip.translators.aquacrop.tools;

public class SoilDataCalculator {
	
	private final static double SURFACE_LAYER_Z = 0.04; // meter
	
	
	public static int calculateSoilClass(double satVolPro, double pwpVolPro, double fcVolPro, double kSatMM) {
		if (satVolPro <= 55.0) {
			if (pwpVolPro >= 20.0) {
				if ((satVolPro >= 49.0) && (fcVolPro >= 40.0)) {
					return 4; // silty clayey soils
				} else {
					return 3; // sandy clayey soils
				}
			} else {
				if (fcVolPro < 23.0) {
					return 1; // sandy soils
				} else {
					if ((pwpVolPro > 16.0) && (kSatMM < 100)) {
						return 3; // sandy clayey soils
					} else {
						if ((pwpVolPro < 6.0) && (fcVolPro < 28.0) && (kSatMM > 750)) {
							return 1; // sandy soils;
						} else {
							return 2; // loamy soils;
						}
					}
				}
			}
		}
		return 4; // silty clayey soils
	}
	
	
	public static String soilClassDescription(int soilClass) {
		switch (soilClass) {
			case 1: return "sandy";
			case 2: return "loamy";
			case 3: return "sandy clayey";
			case 4: return "silty clayey";
			default : return "unknown";
		}
	}

	
	public static double calculateCapillaryRiseEstimationParameterA(int soilClass, double kSatMM) {
		switch (soilClass) {
			case 1: return -0.3112 - kSatMM/100000; // sandy soils
			case 2: return -0.4986 - 9*kSatMM/100000; // loamy soils
			case 3: return -0.5677 - 4*kSatMM/100000; // sandy clayey soils
			default: return -0.6366 + 8*kSatMM/10000; // silty clayey soils
		}
	}

	
	public static double calculateCapillaryRiseEstimationParameterB(int soilClass, double kSatMM) {
		switch (soilClass) {
			case 1: return -1.4936 + 0.2416*Math.log(kSatMM); // sandy soils
			case 2: return -2.1320 + 0.4778*Math.log(kSatMM); // loamy soils
			case 3: return -3.7189 + 0.5922*Math.log(kSatMM); // sandy clayey soils
			default: return -1.9165 + 0.7063*Math.log(kSatMM); // silty clayey soils
		}
	}
	
	
	public static int calculateCurveNumber(double kSatMMOfTopSoilHorizon) {
		if (kSatMMOfTopSoilHorizon >= 250.0) {
			return 65;
		} else if (kSatMMOfTopSoilHorizon >= 50.0) {
			return 75;
		} else if (kSatMMOfTopSoilHorizon >= 10.0) {
			return 80;
		} else {
			return 85;
		}
	}
	
	
	public static double calculateReadilyEvaporableWater(double fcVolPro, double pwpVolPro) {
		double rew = Math.round(10 * (fcVolPro - (pwpVolPro / 2)) * SURFACE_LAYER_Z);
		if (rew < 0.0) {
			rew = 0.0; // minimum volume
		}
		if (rew > 15.0) {
			rew = 15.0; // maximum volume
		}
		return rew;
	}
	
}
