package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.AgMIP;
import org.agmip.util.MapUtil;
import org.agmip.util.MapUtil.BucketEntry;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Irrigation {

	private String name;
	private String method;
	private double percSoilSurfaceWetted;
	private int irrigationMode;
	private List<IrrigationEvent> events = new ArrayList<IrrigationEvent>();

	
	public static Irrigation create(Map data) {
		Irrigation obj = new Irrigation();
		obj.from(data);
		return obj;
	}

	
	public void from(Map data) {
		// get the bucket of relevant data
        BucketEntry dataBucket = MapUtil.getBucket(data, AgMIP.MANAGEMENT_BUCKET_NAME);
        assert(dataBucket != null);
		
        // TODO get the global data
        
        // TODO get the management events data
        List<LinkedHashMap<String, String>> dataItems = dataBucket.getDataList();
        assert(dataItems.size() > 0);
        
        events.clear();
        for (Map<String, String> dataItem : dataItems) {
        	// create a new item
        	IrrigationEvent item = IrrigationEvent.create(dataItem);
        	
        	// TODO: custom data processing
        	
        	// check for planting / sowing event -> keep date as reference
        	// check for harvesting event -> stop processing, rotations not supported (yet)
        	// check for irrigation event -> create IrrigationEvent data from it
        	
        	events.add(item);
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


	public List<IrrigationEvent> getEvents() {
		return events;
	}


	public void setEvents(List<IrrigationEvent> events) {
		this.events = events;
	}
	
}
