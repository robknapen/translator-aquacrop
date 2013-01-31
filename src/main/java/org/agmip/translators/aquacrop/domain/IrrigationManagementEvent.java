package org.agmip.translators.aquacrop.domain;

import java.util.Map;


@SuppressWarnings({"rawtypes"})
public class IrrigationManagementEvent extends ManagementEvent {

	private int numberOfDaysAfterSowingOrPlanting; // int
	private int applicationDepth; // mm
	private double electricalConductivityOfIrrigationWater; // dS/m
	
	
	public static IrrigationManagementEvent create(Map data) {
		IrrigationManagementEvent obj = new IrrigationManagementEvent();
		obj.from(data);
		return obj;
	}
	
	
	public void from(Map data) {
		super.from(data);
		// TODO fill in specific fields
        // "irop":"IR001", // irrigation operation (code)
        // "irval":"13", // irrigation amount, depth of water (mm)
        // "ireff":"1"
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
	
}
