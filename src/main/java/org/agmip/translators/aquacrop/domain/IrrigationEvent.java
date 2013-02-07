package org.agmip.translators.aquacrop.domain;

import java.util.Map;

import org.agmip.translators.aquacrop.tools.IrrigationFunctions;
import org.agmip.util.MapUtil;


@SuppressWarnings({"rawtypes"})
public class IrrigationEvent extends ManagementEvent {

	private String iropCode; // AgMIP irop code
	private int applicationDepth; // mm
	private int irrigationMethod; // aquacrop code
	private double electricalConductivityOfIrrigationWater; // dS/m
	private int numberOfDaysAfterSowingOrPlanting; // int
	
	
	public static IrrigationEvent create(Map data) {
		IrrigationEvent obj = new IrrigationEvent();
		obj.from(data);
		return obj;
	}
	
	
	public void from(Map data) {
		super.from(data);
		
		applicationDepth = Integer.valueOf(MapUtil.getValueOr(data, "irval", "0.0"));
		
		iropCode = MapUtil.getValueOr(data, "irop", null);
		irrigationMethod = new IrrigationFunctions().lookUpAquaCropIrrigationCode(iropCode);

		// currently not available in AgMIP, set to default
		electricalConductivityOfIrrigationWater = 0.0;
		
		// calculated relatively, fill in later 
		numberOfDaysAfterSowingOrPlanting = -1;
	}
	

	public int getNumberOfDaysAfterSowingOrPlanting() {
		return numberOfDaysAfterSowingOrPlanting;
	}


	public void setNumberOfDaysAfterSowingOrPlanting(
			int numberOfDaysAfterSowingOrPlanting) {
		this.numberOfDaysAfterSowingOrPlanting = numberOfDaysAfterSowingOrPlanting;
	}


	public int getApplicationDepth() {
		return applicationDepth;
	}


	public void setApplicationDepth(int applicationDepth) {
		this.applicationDepth = applicationDepth;
	}


	public double getElectricalConductivityOfIrrigationWater() {
		return electricalConductivityOfIrrigationWater;
	}


	public void setElectricalConductivityOfIrrigationWater(
			double electricalConductivityOfIrrigationWater) {
		this.electricalConductivityOfIrrigationWater = electricalConductivityOfIrrigationWater;
	}


	public String getIropCode() {
		return iropCode;
	}


	public void setIropCode(String iropCode) {
		this.iropCode = iropCode;
	}


	public int getIrrigationMethod() {
		return irrigationMethod;
	}


	public void setIrrigationMethod(int irrigationMethod) {
		this.irrigationMethod = irrigationMethod;
	}
	
}
