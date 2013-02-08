package org.agmip.translators.aquacrop.domain;

import java.util.List;

import org.agmip.translators.aquacrop.tools.FertilizerFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates field management data usable as input for the AquaCrop model,
 * from AgMIP management event data. The specified management events are
 * validated. 
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
public class FieldManagement {

    private static final Logger LOG = LoggerFactory.getLogger(FieldManagement.class);	
	
	private String name;
	private double totalNitrogenKgHa;
	private double soilFertilityStressPerc;
	private double soilBundsHeightM;
	private double groundSurfaceCoveredByMulchesPerc;
	private double effectOfMulchesOnReductionOfSoilEvaporationPerc;
	private int surfaceRunOffPrevented;
	
	/**
	 * Creates an instance based on the specified list of events. The
	 * instance will be returned, or null when the events could not be
	 * parsed into a valid irrigation instance.
	 * 
	 * @param events to use to populate the instance
	 * @return created instance or null
	 */
	public static FieldManagement create(List<ManagementEvent> events) {
		FieldManagement obj = new FieldManagement();
		if (obj.from(events)) {
			return obj;
		} else {
			return null;
		}
	}
	
	
	public boolean from(List<ManagementEvent> managementEvents) {
        ManagementEvent startEvent = null;
        int errorCount = 0;
        totalNitrogenKgHa = 0.0;
        soilBundsHeightM = 0.0;
        name = "Generated - ";

        // TODO fen_tot - might be defined globally for the experiment?
    	// TODO icrag - might be defined globally for the experiment?
		
		// loop through the management events and collect useful data
		for (ManagementEvent event : managementEvents) {
	    	// check for planting / sowing event -> keep date as reference
	    	if (event instanceof PlantingEvent) {
	    		if (startEvent != null) {
	    			LOG.error("Multiple planting events within a single experiment can currently not be handled by the translator! Further rotations will be ignored.");
	    			errorCount++;
	    			break;
	    		}
	    		startEvent = event;
	    	}

	    	// check for fertilization event -> sum total N applied (Kg[N]/Ha)
			if ((startEvent != null) && (event instanceof FertilizerEvent)) {
				FertilizerEvent ferEvent = (FertilizerEvent)event;
				totalNitrogenKgHa += ferEvent.getFertilizerTotalNitrogenKgHa();
				name = name + "[" + ferEvent.getFertilizerMaterial() + "]";
			}
			
	    	// check for irrigation event -> create IrrigationEvent data from it
	    	if ((startEvent != null) && (event instanceof IrrigationEvent)) {
	    		IrrigationEvent irrEvent = (IrrigationEvent)event;
	    		double bundHeight = irrEvent.getBundHeightMM();
	    		if ((soilBundsHeightM > 0) && (bundHeight > 0)) {
	    			LOG.warn("Multiple soil bunds heights specified, will use maximum height.");
	    			soilBundsHeightM = Math.max(soilBundsHeightM, bundHeight/1000);
	    		} else {
	    			soilBundsHeightM = bundHeight / 1000;
	    		}
	    	}

	    	// check for harvesting event -> stop processing, rotations not supported (yet)
	    	if ((startEvent != null) && (event instanceof HarvestingEvent)) {
				LOG.warn("Harvesting event in experiment. In case of crop rotations these will be ignored since the current translator does not handle them.");
	    	}
		}
    	
        if (totalNitrogenKgHa <= 0) {
        	LOG.info("No fertilization used in experiment.");
        	name = name + "No fertilizer applied";
        }
        
        // calculate AquaCrop input values
        groundSurfaceCoveredByMulchesPerc = 0.0; // default, can currently not be derived from AgMIP data
        surfaceRunOffPrevented = 0; // default, can currently not be derived from AgMIP data
        effectOfMulchesOnReductionOfSoilEvaporationPerc = 50.0; // default, can currently not be derived from AgMIP data
        
        soilFertilityStressPerc = new FertilizerFunctions().calculateSoilFertilityStress(totalNitrogenKgHa);
        
        return (errorCount == 0);
	}
	
	
	@Override
	public String toString() {
		return "FieldManagement [name=" + name + ", totalNitrogenKgHa="
				+ totalNitrogenKgHa + ", soilFertilityStressPerc="
				+ soilFertilityStressPerc + "]";
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getTotalNitrogenKgHa() {
		return totalNitrogenKgHa;
	}


	public void setTotalNitrogenKgHa(double totalNitrogenKgHa) {
		this.totalNitrogenKgHa = totalNitrogenKgHa;
	}


	public double getSoilFertilityStressPerc() {
		return soilFertilityStressPerc;
	}


	public void setSoilFertilityStressPerc(double soilFertilityStressPerc) {
		this.soilFertilityStressPerc = soilFertilityStressPerc;
	}


	public double getSoilBundsHeightM() {
		return soilBundsHeightM;
	}


	public void setSoilBundsHeightM(double soilBundsHeightM) {
		this.soilBundsHeightM = soilBundsHeightM;
	}


	public double getGroundSurfaceCoveredByMulchesPerc() {
		return groundSurfaceCoveredByMulchesPerc;
	}


	public void setGroundSurfaceCoveredByMulchesPerc(
			double groundSurfaceCoveredByMulchesPerc) {
		this.groundSurfaceCoveredByMulchesPerc = groundSurfaceCoveredByMulchesPerc;
	}


	public int getSurfaceRunOffPrevented() {
		return surfaceRunOffPrevented;
	}


	public void setSurfaceRunOffPrevented(int surfaceRunOffPrevented) {
		this.surfaceRunOffPrevented = surfaceRunOffPrevented;
	}


	public double getEffectOfMulchesOnReductionOfSoilEvaporationPerc() {
		return effectOfMulchesOnReductionOfSoilEvaporationPerc;
	}


	public void setEffectOfMulchesOnReductionOfSoilEvaporationPerc(
			double effectOfMulchesOnReductionOfSoilEvaporationPerc) {
		this.effectOfMulchesOnReductionOfSoilEvaporationPerc = effectOfMulchesOnReductionOfSoilEvaporationPerc;
	}
	
}
