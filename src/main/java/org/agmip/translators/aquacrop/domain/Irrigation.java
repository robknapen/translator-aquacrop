package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Creates irrigation data usable as input for the AquaCrop model, from
 * AgMIP management event data. The specified management events are
 * validated. 
 * 
 * @author Rob Knapen, Alterra Wageningen-UR, The Netherlands
 */
public class Irrigation {

    private static final Logger LOG = LoggerFactory.getLogger(Irrigation.class);	
	
	private String name;
	private String method;
	private double percSoilSurfaceWetted;
	private int irrigationMode;

	private List<IrrigationEvent> items = new ArrayList<IrrigationEvent>();
	
	
	public static Irrigation create(List<ManagementEvent> events) {
		Irrigation obj = new Irrigation();
		obj.from(events);
		return obj;
	}
	
	
	public void from(List<ManagementEvent> managementEvents) {

		// TODO get the global data
		LOG.error("Het ging niet goed");
        
        items.clear();
        ManagementEvent startEvent = null;
        
        for (ManagementEvent event : managementEvents) {
        	
        	// check for planting / sowing event -> keep date as reference
        	if (event instanceof PlantingEvent) {
        		startEvent = event;
        	}
        	
        	// check for irrigation event -> create IrrigationEvent data from it
        	if ((startEvent != null) && (event instanceof IrrigationEvent)) {
            	// TODO: custom data processing
            	items.add((IrrigationEvent)event);
        	}
        	
        	// check for harvesting event -> stop processing, rotations not supported (yet)
        	if ((startEvent != null) && (event instanceof HarvestingEvent)) {
        		break;
        	}
        }
        
    	// TODO: global custom data processing
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public double getPercSoilSurfaceWetted() {
		return percSoilSurfaceWetted;
	}


	public void setPercSoilSurfaceWetted(double percSoilSurfaceWetted) {
		this.percSoilSurfaceWetted = percSoilSurfaceWetted;
	}


	public int getIrrigationMode() {
		return irrigationMode;
	}


	public void setIrrigationMode(int irrigationMode) {
		this.irrigationMode = irrigationMode;
	}


	public List<IrrigationEvent> getItems() {
		return items;
	}
	
}
