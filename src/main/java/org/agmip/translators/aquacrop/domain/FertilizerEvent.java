package org.agmip.translators.aquacrop.domain;

import java.util.Map;

import org.agmip.util.MapUtil;


@SuppressWarnings({"rawtypes"})
public class FertilizerEvent extends ManagementEvent {

	private String fertilizerApplicationMethod; 	// feacd - fertilizer application method (code)
	private String fertilizerMaterial; 				// fecd - fertilizer material (code)
	private double fertilizerApplicationDepthCm;	// fedep - fertilizer application depth (cm)
	private double fertilizerTotalNitrogenKgHa;		// feamn + feno3 + fenh4 (kg[N]/ha)

	
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
		
		fertilizerApplicationMethod = MapUtil.getValueOr(data, "feacd", "Undefined");
		fertilizerMaterial = MapUtil.getValueOr(data, "fecd", "Undefined");
		fertilizerApplicationDepthCm = Double.valueOf(MapUtil.getValueOr(data, "fedep", "0.0"));

		double feamn = Double.valueOf(MapUtil.getValueOr(data, "feamn", "0.0"));
		double feno3 = Double.valueOf(MapUtil.getValueOr(data, "feno3", "0.0"));
		double fenh4 = Double.valueOf(MapUtil.getValueOr(data, "fenh4", "0.0"));

		fertilizerTotalNitrogenKgHa = feamn + feno3 + fenh4;
		
		return true;
	}


	@Override
	public String toString() {
		return "FertilizerEvent [fertilizerApplicationMethod="
				+ fertilizerApplicationMethod + ", fertilizerMaterial="
				+ fertilizerMaterial + ", fertilizerApplicationDepthCm="
				+ fertilizerApplicationDepthCm
				+ ", fertilizerTotalNitrogenKgHa="
				+ fertilizerTotalNitrogenKgHa + "]";
	}


	public String getFertilizerApplicationMethod() {
		return fertilizerApplicationMethod;
	}


	public void setFertilizerApplicationMethod(String fertilizerApplicationMethod) {
		this.fertilizerApplicationMethod = fertilizerApplicationMethod;
	}


	public String getFertilizerMaterial() {
		return fertilizerMaterial;
	}


	public void setFertilizerMaterial(String fertilizerMaterial) {
		this.fertilizerMaterial = fertilizerMaterial;
	}


	public double getFertilizerApplicationDepthCm() {
		return fertilizerApplicationDepthCm;
	}


	public void setFertilizerApplicationDepthCm(double fertilizerApplicationDepthCm) {
		this.fertilizerApplicationDepthCm = fertilizerApplicationDepthCm;
	}


	public double getFertilizerTotalNitrogenKgHa() {
		return fertilizerTotalNitrogenKgHa;
	}


	public void setFertilizerTotalNitrogenKgHa(double fertilizerTotalNitrogenKgHa) {
		this.fertilizerTotalNitrogenKgHa = fertilizerTotalNitrogenKgHa;
	}
	
}
