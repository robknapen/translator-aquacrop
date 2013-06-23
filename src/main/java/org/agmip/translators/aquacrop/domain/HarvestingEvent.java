package org.agmip.translators.aquacrop.domain;

import java.io.IOException;

import org.agmip.ace.AceEvent;

public class HarvestingEvent extends ManagementEvent {

	public static HarvestingEvent create(AceEvent aceEvent) throws IOException {
		HarvestingEvent obj = new HarvestingEvent();
		if (obj.from(aceEvent)) {
			return obj;
		}
		return null;
	}
	
	
	public boolean from(AceEvent aceEvent) throws IOException {
		if (!super.from(aceEvent)) {
			return false;
		}
		
		// TODO: fill custom fields
		return true;
	}
	
}
