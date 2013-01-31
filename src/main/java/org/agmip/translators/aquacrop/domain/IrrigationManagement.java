package org.agmip.translators.aquacrop.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.AgMIP;
import org.agmip.util.MapUtil;
import org.agmip.util.MapUtil.BucketEntry;

@SuppressWarnings({"rawtypes", "unchecked"})
public class IrrigationManagement extends Management {

	private String name;
	private String method;
	private double percSoilSurfaceWetted;
	private int irrigationMode;

	
	public static IrrigationManagement create(Map data) {
		IrrigationManagement obj = new IrrigationManagement();
		obj.from(data);
		return obj;
	}
	
	
	public void from(Map data) {
		// get the bucket of relevant data
        BucketEntry dataBucket = MapUtil.getBucket(data, AgMIP.MANAGEMENT_BUCKET_NAME);
        assert(dataBucket != null);
		
        // TODO get the global data
        
        // get the management events data
        List<LinkedHashMap<String, String>> dataItems = dataBucket.getDataList();
        assert(dataItems.size() > 0);
        
        events.clear();
        ManagementEvent startEvent = null;
        
        for (Map<String, String> dataItem : dataItems) {
        	// create a new item
        	ManagementEvent event = createEvent(dataItem);
        	
        	// check for planting / sowing event -> keep date as reference
        	if (event instanceof PlantingManagementEvent) {
        		startEvent = event;
        	}
        	
        	// check for irrigation event -> create IrrigationEvent data from it
        	if ((startEvent != null) && (event instanceof IrrigationManagementEvent)) {
            	// TODO: custom data processing
            	events.add(event);
        	}
        	
        	// check for harvesting event -> stop processing, rotations not supported (yet)
        	if ((startEvent != null) && (event instanceof HarvestingManagementEvent)) {
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
	
}
