package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.AgMIP;
import org.agmip.translators.aquacrop.tools.MapHelper;


@SuppressWarnings({"rawtypes"})
public abstract class Management {

	protected List<ManagementEvent> events = new ArrayList<ManagementEvent>();

	
	public ManagementEvent createEvent(Map data) {
		String event = MapHelper.getValueFor(data, "Unknown", "event");
		if (AgMIP.MANAGEMENT_EVENT_PLANT.equalsIgnoreCase(event)) {
			return PlantingEvent.create(data);
		}
		if (AgMIP.MANAGEMENT_EVENT_IRRIGATE.equalsIgnoreCase(event)) {
			return IrrigationEvent.create(data);
		}
		if (AgMIP.MANAGEMENT_EVENT_HARVEST.equalsIgnoreCase(event)) {
			return HarvestingEvent.create(data);
		}
		return null;
	}
	
	
	public abstract void from(Map data);


	public List<ManagementEvent> getEvents() {
		return events;
	}


	public void setEvents(List<ManagementEvent> events) {
		this.events = events;
	}
	
}
