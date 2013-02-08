package org.agmip.translators.aquacrop.domain;

import java.util.Map;

import org.agmip.util.MapUtil;


@SuppressWarnings({"rawtypes"})
public class FertilizerEvent extends ManagementEvent {

	private String applicationMethod; 	// feacd - fertilizer application method (code)
	private String materialCode; 		// fecd - fertilizer material (code)
	private double applicationDepthCm;	// fedep - fertilizer application depth (cm)
	private double totalNitrogenKgHa;	// feamn + feno3 + fenh4 (kg[N]/ha)

	
	public static FertilizerEvent create(Map data) {
		FertilizerEvent obj = new FertilizerEvent();
		if (obj.from(data)) {
			return obj;
		}
		return null;
	}
	
	
	public boolean from(Map data) {
		if (!super.from(data)) {
			return false;
		}
		
		applicationMethod = MapUtil.getValueOr(data, "feacd", "Undefined");
		materialCode = MapUtil.getValueOr(data, "fecd", "Undefined");
		applicationDepthCm = Double.valueOf(MapUtil.getValueOr(data, "fedep", "0.0"));

		double feamn = Double.valueOf(MapUtil.getValueOr(data, "feamn", "0.0"));
		double feno3 = Double.valueOf(MapUtil.getValueOr(data, "feno3", "0.0"));
		double fenh4 = Double.valueOf(MapUtil.getValueOr(data, "fenh4", "0.0"));

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
