package org.agmip.translators.aquacrop.domain;

import java.io.IOException;

import org.agmip.ace.AceEvent;


public class FertilizerEvent extends ManagementEvent {

	private String applicationMethod; 	// feacd - fertilizer application method (code)
	private String materialCode; 		// fecd - fertilizer material (code)
	private double applicationDepthCm;	// fedep - fertilizer application depth (cm)
	private double totalNitrogenKgHa;	// feamn + feno3 + fenh4 (kg[N]/ha)

	
	public static FertilizerEvent create(AceEvent aceEvent) throws IOException {
		FertilizerEvent obj = new FertilizerEvent();
		if (obj.from(aceEvent)) {
			return obj;
		}
		return null;
	}
	
	
	public boolean from(AceEvent aceEvent) throws IOException {
		if (!super.from(aceEvent)) {
			return false;
		}
		
		applicationMethod = aceEvent.getValueOr("feacd", "Undefined");
		materialCode = aceEvent.getValueOr("fecd", "Undefined");
		applicationDepthCm = Double.valueOf(aceEvent.getValueOr("fedep", "0.0"));

		double feamn = Double.valueOf(aceEvent.getValueOr("feamn", "0.0"));
		double feno3 = Double.valueOf(aceEvent.getValueOr("feno3", "0.0"));
		double fenh4 = Double.valueOf(aceEvent.getValueOr("fenh4", "0.0"));

		totalNitrogenKgHa = feamn + feno3 + fenh4;
		
		return true;
	}


	@Override
	public String toString() {
		return "FertilizerEvent [applicationMethod="
				+ applicationMethod + ", materialCode="
				+ materialCode + ", applicationDepthCm="
				+ applicationDepthCm
				+ ", totalNitrogenKgHa="
				+ totalNitrogenKgHa + "]";
	}


	public String getApplicationMethod() {
		return applicationMethod;
	}


	public void setApplicationMethod(String applicationMethod) {
		this.applicationMethod = applicationMethod;
	}


	public String getMaterialCode() {
		return materialCode;
	}


	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}


	public double getApplicationDepthCm() {
		return applicationDepthCm;
	}


	public void setApplicationDepthCm(double applicationDepthCm) {
		this.applicationDepthCm = applicationDepthCm;
	}


	public double getTotalNitrogenKgHa() {
		return totalNitrogenKgHa;
	}


	public void setTotalNitrogenKgHa(double totalNitrogenKgHa) {
		this.totalNitrogenKgHa = totalNitrogenKgHa;
	}
	
}
