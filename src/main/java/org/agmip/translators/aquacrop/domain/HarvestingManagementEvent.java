package org.agmip.translators.aquacrop.domain;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class HarvestingManagementEvent extends ManagementEvent {

	public static HarvestingManagementEvent create(Map data) {
		HarvestingManagementEvent obj = new HarvestingManagementEvent();
		obj.from(data);
		return obj;
	}
	
	
	public void from(Map data) {
		super.from(data);
		// TODO: fill custom fields
	}
	
}
