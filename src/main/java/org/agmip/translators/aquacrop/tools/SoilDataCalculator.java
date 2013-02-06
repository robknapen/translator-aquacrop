package org.agmip.translators.aquacrop.tools;

import java.util.HashMap;
import java.util.Map;

import org.agmip.translators.aquacrop.domain.SoilLayer;

public class SoilDataCalculator {
	
	private final static double SURFACE_LAYER_Z = 0.04; // meter

	private Map<String, SoilLookupEntry> lookup = new HashMap<String, SoilLookupEntry>();
	
	
	protected class SoilLookupEntry {
		String name;
		double satVolPro;
		double fcVolPro;
		double pwpVolPro;
		double kSatMM;
		
		public SoilLookupEntry(String name, double sat, double fc, double pwp, double ksat) {
			this.name = name;
			satVolPro = sat;
			fcVolPro = fc;
			pwpVolPro = pwp;
			kSatMM = ksat;
		}
	}
	
	
	public SoilDataCalculator() {
		// set up the lookup table
		lookup.put("CSA,FSA,SA,VFSA", new SoilLookupEntry("Sand", 36.0, 13.0, 6.0, 1500.0));
		lookup.put("CLOSA,FLOSA,LOSA,VFLOS", new SoilLookupEntry("Loamy sand", 38.0, 16.0, 8.0, 800.0));
		lookup.put("CSALO,FSALO,VFSAL,SALO", new SoilLookupEntry("Sandy loam", 41.0, 22.0, 10.0, 500.0));
		lookup.put("FLO,LO", new SoilLookupEntry("Loam", 46.0, 31.0, 15.0, 250.0));
		lookup.put("SILO", new SoilLookupEntry("Silt loam", 46.0, 33.0, 13.0, 150.0));
		lookup.put("CSI,SI", new SoilLookupEntry("Silt", 43.0, 33.0, 9.0, 50.0));
		lookup.put("SACLL", new SoilLookupEntry("Sandy clay loam", 47.0, 32.0, 20.0, 125.0));
		lookup.put("CLLO", new SoilLookupEntry("Clay loam", 50.0, 39.0, 23.0, 100.0));
		lookup.put("SICLL", new SoilLookupEntry("Silty clay loam", 52.0, 44.0, 23.0, 120.0));
		lookup.put("SACL", new SoilLookupEntry("Sandy clay", 50.0, 39.0, 27.0, 75.0));
		lookup.put("SICL", new SoilLookupEntry("Silty clay", 54.0, 50.0, 32.0, 15.0));
		lookup.put("CL", new SoilLookupEntry("Clay", 55.0, 54.0, 39.0, 2.0));
	}

	
	public void initSWCFromAgMIPCode(SoilLayer horizon, String sltxCode) {
		if ((horizon == null) || (sltxCode == null)) {
			return;
		}
		for (String key : lookup.keySet()) {
			for (String code : key.split(",")) {
				if (code.equalsIgnoreCase(sltxCode)) {
					SoilLookupEntry entry = lookup.get(key);
					horizon.setDescription(entry.name);
					horizon.setSoilWaterContentAtFieldCapacity(entry.fcVolPro);
					horizon.setSoilWaterContentAtPermanentWiltingPoint(entry.pwpVolPro);
					horizon.setSoilWaterContentAtSaturation(entry.satVolPro);
					return;
				}
			}
		}
	}

	
	public void initKSatFromAgMIPCode(SoilLayer horizon, String sltxCode) {
		if ((horizon == null) || (sltxCode == null)) {
			return;
		}
		for (String key : lookup.keySet()) {
			for (String code : key.split(",")) {
				if (code.equalsIgnoreCase(sltxCode)) {
					SoilLookupEntry entry = lookup.get(key);
					horizon.setDescription(entry.name);
					horizon.setSaturatedHydrolicConductivity(entry.kSatMM);
					return;
				}
			}
		}
	}
	
	
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
