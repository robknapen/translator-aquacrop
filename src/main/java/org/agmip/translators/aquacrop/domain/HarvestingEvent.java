package org.agmip.translators.aquacrop.domain;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class HarvestingEvent extends ManagementEvent {

	public static HarvestingEvent create(Map data) {
		HarvestingEvent obj = new HarvestingEvent();
		if (obj.from(data)) {
			return obj;
		}
		return null;
	}
	
	
	public boolean from(Map data) {
		if (!super.from(data)) {
			return false;
		}
		
		// TODO: fill custom fields
		return true;
	}
	
}
