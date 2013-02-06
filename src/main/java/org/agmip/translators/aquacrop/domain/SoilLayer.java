package org.agmip.translators.aquacrop.domain;

import java.util.Map;

import org.agmip.translators.aquacrop.tools.MapHelper;
import org.agmip.translators.aquacrop.tools.SoilDataCalculator;


@SuppressWarnings({"rawtypes"}) 
public class SoilLayer {

	// extracted data
	private double baseDepth; // mm
	private double soilWaterContentAtSaturation;
	private double soilWaterContentAtFieldCapacity;
	private double soilWaterContentAtPermanentWiltingPoint;
	private double saturatedHydrolicConductivity;
	
	// derived data
	private double thickness;
	private double capillaryRiseEstimationParameterA;
	private double capillaryRiseEstimationParameterB;
	private String description;
	
	
	public static SoilLayer create(Map data) {
		SoilLayer sh = new SoilLayer();
		sh.from(data);
		return sh;
	}
	
	
	public void from(Map data) {
		// extract AgMIP info
		String sllbVal	= MapHelper.getValueFor(data, "10.0", "sllb"); // cm (default 10.0)
		String sltxVal  = MapHelper.getValueFor(data, "SA", "sltx"); // code (default sand)
		String slsatVal = MapHelper.getValueFor(data, "0.0", "slsat"); // cm3/cm3  (default 0.0)
		String wpVal    = MapHelper.getValueOrNullFor(data, "slwp", "slll"); // cm3/cm3
		String fcVal    = MapHelper.getValueOrNullFor(data, "slfc1", "sldul"); // cm3/cm3
		String ksatVal	= MapHelper.getValueOrNullFor(data, "sksat"); // cm/h
		
		// fill in structure and convert units
		baseDepth = Double.valueOf(sllbVal) / 100; // cm -> m
		soilWaterContentAtSaturation = Double.valueOf(slsatVal) * 100; // cm3/cm3 -> vol%
		
		SoilDataCalculator sdc = null;
		if (ksatVal != null) {
			saturatedHydrolicConductivity = Double.valueOf(ksatVal) * 240; // cm/h -> mm/d
		} else {
			// derive values from lookup table based on the SLTX code
			sdc = new SoilDataCalculator();
			sdc.initKSatFromAgMIPCode(this, sltxVal);
		}
		
		if ((wpVal != null) && (fcVal != null)) {
			soilWaterContentAtPermanentWiltingPoint = Double.valueOf(wpVal) * 100; // cm3/cm3 -> vol%
			soilWaterContentAtFieldCapacity = Double.valueOf(fcVal) * 100; // cm3/cm3 -> vol%
			// TODO: update ksat?
		} else {
			// derive values from lookup table based on the SLTX code
			if (sdc == null) {
				sdc = new SoilDataCalculator();
			}
			sdc.initSWCFromAgMIPCode(this, sltxVal);
			// TODO: update ksat?
		}
	}


	public double getSoilLayerBaseDepth() {
		return baseDepth;
	}


	public void setSoilLayerBaseDepth(double soilLayerBaseDepth) {
		this.baseDepth = soilLayerBaseDepth;
	}


	public double getThickness() {
		return thickness;
	}


	public void setThickness(double thickness) {
		this.thickness = thickness;
	}


	public double getSoilWaterContentAtSaturation() {
		return soilWaterContentAtSaturation;
	}


	public void setSoilWaterContentAtSaturation(double soilWaterContentAtSaturation) {
		this.soilWaterContentAtSaturation = soilWaterContentAtSaturation;
	}


	public double getSoilWaterContentAtFieldCapacity() {
		return soilWaterContentAtFieldCapacity;
	}


	public void setSoilWaterContentAtFieldCapacity(
			double soilWaterContentAtFieldCapacity) {
		this.soilWaterContentAtFieldCapacity = soilWaterContentAtFieldCapacity;
	}


	public double getSoilWaterContentAtPermanentWiltingPoint() {
		return soilWaterContentAtPermanentWiltingPoint;
	}


	public void setSoilWaterContentAtPermanentWiltingPoint(
			double soilWaterContentAtPermanentWiltingPoint) {
		this.soilWaterContentAtPermanentWiltingPoint = soilWaterContentAtPermanentWiltingPoint;
	}


	public double getSaturatedHydrolicConductivity() {
		return saturatedHydrolicConductivity;
	}


	public void setSaturatedHydrolicConductivity(
			double saturatedHydrolicConductivity) {
		this.saturatedHydrolicConductivity = saturatedHydrolicConductivity;
	}


	public double getCapillaryRiseEstimationParameterA() {
		return capillaryRiseEstimationParameterA;
	}


	public void setCapillaryRiseEstimationParameterA(
			double capillaryRiseEstimationParameterA) {
		this.capillaryRiseEstimationParameterA = capillaryRiseEstimationParameterA;
	}


	public double getCapillaryRiseEstimationParameterB() {
		return capillaryRiseEstimationParameterB;
	}


	public void setCapillaryRiseEstimationParameterB(
			double capillaryRiseEstimationParameterB) {
		this.capillaryRiseEstimationParameterB = capillaryRiseEstimationParameterB;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
}
