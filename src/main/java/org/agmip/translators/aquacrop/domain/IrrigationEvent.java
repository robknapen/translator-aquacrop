package org.agmip.translators.aquacrop.domain;

import java.io.IOException;

import org.agmip.ace.AceEvent;
import org.agmip.translators.aquacrop.tools.IrrigationFunctions;


public class IrrigationEvent extends ManagementEvent {

	private String iropCode; // AgMIP irop code
	private double applicationDepthMM; // mm
	private int irrigationMethod; // aquacrop code
	private double electricalConductivityOfIrrigationWater; // dS/m
	private int numberOfDaysAfterSowingOrPlanting; // int
	private double bundHeightMM; // abund, mm
	
	
	public static IrrigationEvent create(AceEvent aceEvent) throws IOException {
		IrrigationEvent obj = new IrrigationEvent();
		if (obj.from(aceEvent)) {
			return obj;
		}
		return null;
	}
	
	
	public boolean from(AceEvent aceEvent) throws IOException {
		if (!super.from(aceEvent)) {
			return false;
		}
		
		applicationDepthMM = Double.valueOf(aceEvent.getValueOr("irval", "0.0"));
		bundHeightMM = Double.valueOf(aceEvent.getValueOr("abund", "0.0"));
		
		iropCode = aceEvent.getValueOr("irop", "");
		
		// TODO: Use AceLookup for this
		irrigationMethod = new IrrigationFunctions().lookUpAquaCropIrrigationCode(iropCode);
		
		// currently not available in AgMIP, set to default
		electricalConductivityOfIrrigationWater = 0.0;
		
		// calculated relatively, fill in later 
		numberOfDaysAfterSowingOrPlanting = -1;
		
		return true;
	}


	@Override
	public String toString() {
		return "IrrigationEvent [iropCode=" + iropCode + ", applicationDepth="
				+ applicationDepthMM + ", irrigationMethod=" + irrigationMethod
				+ ", electricalConductivityOfIrrigationWater="
				+ electricalConductivityOfIrrigationWater
				+ ", numberOfDaysAfterSowingOrPlanting="
				+ numberOfDaysAfterSowingOrPlanting + ", bundHeight="
				+ bundHeightMM + "]";
	}


	public int getNumberOfDaysAfterSowingOrPlanting() {
		return numberOfDaysAfterSowingOrPlanting;
	}


	public void setNumberOfDaysAfterSowingOrPlanting(
			int numberOfDaysAfterSowingOrPlanting) {
		this.numberOfDaysAfterSowingOrPlanting = numberOfDaysAfterSowingOrPlanting;
	}


	public double getApplicationDepthMM() {
		return applicationDepthMM;
	}


	public void setApplicationDepthMM(double applicationDepthMM) {
		this.applicationDepthMM = applicationDepthMM;
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


	public double getBundHeightMM() {
		return bundHeightMM;
	}


	public void setBundHeightMM(double bundHeightMM) {
		this.bundHeightMM = bundHeightMM;
	}
	
}
