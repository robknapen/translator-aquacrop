package org.agmip.translators.aquacrop.domain;

import java.util.Map;


@SuppressWarnings("rawtypes")
public class PlantingManagementEvent extends ManagementEvent {

	public static PlantingManagementEvent create(Map data) {
		PlantingManagementEvent obj = new PlantingManagementEvent();
		obj.from(data);
		return obj;
	}

	
	public void from(Map data) {
		super.from(data);
		// TODO: fill custom fields
	}
	
}
