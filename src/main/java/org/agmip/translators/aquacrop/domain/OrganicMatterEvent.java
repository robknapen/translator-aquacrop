package org.agmip.translators.aquacrop.domain;

import java.io.IOException;

import org.agmip.ace.AceEvent;

public class OrganicMatterEvent extends ManagementEvent {

	private String applicationMethod; 	// omacd - organic material, application method (code)
	private String identifyingCode;		// omcd - organic material, identifying code
	private double applicationDepthCm;	// omdep - organic material, application depth (cm)
	private double totalNitrogenKgHa;	// omamt * omn% (kg[N]/ha)

	
	public static OrganicMatterEvent create(AceEvent aceEvent) throws IOException {
		OrganicMatterEvent obj = new OrganicMatterEvent();
		if (obj.from(aceEvent)) {
			return obj;
		}
		return null;
	}
	
	
	public boolean from(AceEvent aceEvent) throws IOException {
		if (!super.from(aceEvent)) {
			return false;
		}
		
		applicationMethod = aceEvent.getValueOr("omacd", "Undefined");
		identifyingCode = aceEvent.getValueOr("omcd", "Undefined");
		applicationDepthCm = Double.valueOf(aceEvent.getValueOr("omdep", "0.0"));

		double omamt = Double.valueOf(aceEvent.getValueOr("omamt", "0.0"));
		double omnperc = Double.valueOf(aceEvent.getValueOr("omn%", "0.0"));

		totalNitrogenKgHa = omamt * omnperc / 100.0;
		
		return true;
	}
	

	@Override
	public String toString() {
		return "OrganicMatterEvent [applicationMethod=" + applicationMethod
				+ ", identifyingCode=" + identifyingCode
				+ ", applicationDepthCm=" + applicationDepthCm
				+ ", totalNitrogenKgHa=" + totalNitrogenKgHa + "]";
	}


	public String getApplicationMethod() {
		return applicationMethod;
	}


	public void setApplicationMethod(String applicationMethod) {
		this.applicationMethod = applicationMethod;
	}


	public String getIdentifyingCode() {
		return identifyingCode;
	}


	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
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
