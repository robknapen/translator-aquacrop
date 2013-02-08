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
		if (obj.from(data)) {
			return obj;
		}
		return null;
	}

	
	public boolean from(Map data) {
		if (!super.from(data)) {
			return false;
		}
		cropId = MapUtil.getValueOr(data, "crid", "Undefined");
		aquaCropCultivarId = MapUtil.getValueOr(data, "aquaCrop_cul_id", "Undefined");
		cultivarName = MapUtil.getValueOr(data, "cul_name", "Undefined");
		
		return true;
	}


	@Override
	public String toString() {
		return "PlantingEvent [cropId=" + cropId + ", cultivarName="
				+ cultivarName + ", aquaCropCultivarId=" + aquaCropCultivarId
				+ "]";
	}
	
}
