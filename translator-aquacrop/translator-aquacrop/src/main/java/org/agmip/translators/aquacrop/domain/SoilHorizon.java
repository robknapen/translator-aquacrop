package org.agmip.translators.aquacrop.domain;

import java.util.Map;


@SuppressWarnings({"rawtypes", "unchecked"}) 
public class SoilHorizon {

	private double thickness;
	private double soilWaterContentAtSaturation;
	private double soilWaterContentAtFieldCapacity;
	private double soilWaterContentAtPermanentWiltingPoint;
	private double saturatedHydrolicConductivity;
	private double capillaryRiseEstimationParameterA;
	private double capillaryRiseEstimationParameterB;
	private String description;
	
	
	public static SoilHorizon create(Map data) {
		SoilHorizon sh = new SoilHorizon();
		sh.from(data);
		return sh;
	}
	
	
	public void from(Map data) {
		// TODO
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
