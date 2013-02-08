package org.agmip.translators.aquacrop.tools;

/**
 * Support functions for the mapping of AgMIP to AquaCrop field management
 * data.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
public class FertilizerFunctions {

	/**
	 * This rule is very controversial since the relationship FEN_TOT – stress,
	 * varies with crop type, crop cycle length, cultivar, soil type, initial
	 * conditions in the soil profile, weather conditions, N in applied 
	 * fertilizer, number of fertilizer applications over season, etc.
	 * 
	 * Indicative value for a high fertilizer application = 130 kgN/ha or more. 
	 * This will correspond with 0 % soil fertility stress.
	 * 
	 * @param fentot
	 * @return calculated perc
	 */
	public double calculateSoilFertilityStress(double fentot) {
		double val = 0.5 * (130.0 - fentot);
		if (val < 0.0) {
			return 0.0;
		} else if (val > 100.0) {
			return 100.0;
		}
		return val;
	}
	
}
