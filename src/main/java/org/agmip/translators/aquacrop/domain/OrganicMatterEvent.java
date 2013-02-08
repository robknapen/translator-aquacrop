package org.agmip.translators.aquacrop.domain;

import java.util.Map;

import org.agmip.util.MapUtil;

@SuppressWarnings({"rawtypes"})
public class OrganicMatterEvent extends ManagementEvent {

	private String applicationMethod; 	// omacd - organic material, application method (code)
	private String identifyingCode;		// omcd - organic material, identifying code
	private double applicationDepthCm;	// omdep - organic material, application depth (cm)
	private double totalNitrogenKgHa;	// omamt * omn% (kg[N]/ha)

	
	public static OrganicMatterEvent create(Map data) {
		OrganicMatterEvent obj = new OrganicMatterEvent();
		if (obj.from(data)) {
			return obj;
		}
		return null;
	}
	
	
	public boolean from(Map data) {
		if (!super.from(data)) {
			return false;
		}
		
		applicationMethod = MapUtil.getValueOr(data, "omacd", "Undefined");
		identifyingCode = MapUtil.getValueOr(data, "omcd", "Undefined");
		applicationDepthCm = Double.valueOf(MapUtil.getValueOr(data, "omdep", "0.0"));

		double omamt = Double.valueOf(MapUtil.getValueOr(data, "omamt", "0.0"));
		double omnperc = Double.valueOf(MapUtil.getValueOr(data, "omn%", "0.0"));

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
