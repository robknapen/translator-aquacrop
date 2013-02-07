package org.agmip.translators.aquacrop.domain;

import java.util.Map;

import org.agmip.util.MapUtil;


@SuppressWarnings("rawtypes")
public class PlantingEvent extends ManagementEvent {

	private String cropId; // crid
	private String cultivarName; // cul_name
	private String aquaCropCultivarId; // aquacrop_cul_id
	
	
	public static PlantingEvent create(Map data) {
		PlantingEvent obj = new PlantingEvent();
		obj.from(data);
		return obj;
	}

	
	public void from(Map data) {
		super.from(data);
		cropId = MapUtil.getValueOr(data, "crid", "Undefined");
		aquaCropCultivarId = MapUtil.getValueOr(data, "aquaCrop_cul_id", "Undefined");
		cultivarName = MapUtil.getValueOr(data, "cul_name", "Undefined");
	}


	@Override
	public String toString() {
		return "PlantingEvent [cropId=" + cropId + ", cultivarName="
				+ cultivarName + ", aquaCropCultivarId=" + aquaCropCultivarId
				+ "]";
	}
	
}
