package org.agmip.translators.aquacrop.domain;

import java.io.IOException;

import org.agmip.ace.AceEvent;


public class PlantingEvent extends ManagementEvent {

	private String cropId; // crid
	private String cultivarName; // cul_name
	private String aquaCropCultivarId; // aquacrop_cul_id
	
	
	public static PlantingEvent create(AceEvent aceEvent) throws IOException {
		PlantingEvent obj = new PlantingEvent();
		if (obj.from(aceEvent)) {
			return obj;
		}
		return null;
	}

	
	public boolean from(AceEvent aceEvent) throws IOException {
		if (!super.from(aceEvent)) {
			return false;
		}
		cropId = aceEvent.getValueOr("crid", "Undefined");
		aquaCropCultivarId = aceEvent.getValueOr("aquaCrop_cul_id", "Undefined");
		cultivarName = aceEvent.getValueOr("cul_name", "Undefined");
		
		return true;
	}


	@Override
	public String toString() {
		return "PlantingEvent [cropId=" + cropId + ", cultivarName="
				+ cultivarName + ", aquaCropCultivarId=" + aquaCropCultivarId
				+ "]";
	}
	
}
