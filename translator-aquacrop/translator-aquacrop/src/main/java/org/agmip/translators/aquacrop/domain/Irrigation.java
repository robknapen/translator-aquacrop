package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        BucketEntry dataBucket = MapUtil.getBucket(data, "irrigation");
        assert(dataBucket != null);
		
        // TODO get the global data
        
        // TODO get the irrigation events data
        List<LinkedHashMap<String, String>> dataItems = dataBucket.getDataList();
        assert(dataItems.size() > 0);
        
        events.clear();
        for (Map<String, String> dataItem : dataItems) {
        	// create a new item
        	IrrigationEvent item = IrrigationEvent.create(dataItem);
        	
        	// TODO: custom data processing
        	
        	events.add(item);
        }
        
    	// TODO: global custom data processing
	}
}
