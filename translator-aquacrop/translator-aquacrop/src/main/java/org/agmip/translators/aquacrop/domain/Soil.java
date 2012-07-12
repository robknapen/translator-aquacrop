package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.agmip.util.MapUtil;
import org.agmip.util.MapUtil.BucketEntry;


@SuppressWarnings({"rawtypes", "unchecked"}) 
public class Soil {

	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private String classification;
	private int curveNumber;
	private double readilyEvaporatedWater;
	private List<SoilHorizon> horizons = new ArrayList<SoilHorizon>();

	
	public static Soil create(Map data) {
		Soil s = new Soil();
		s.from(data);
		return s;
	}
	
	
	public void from(Map data) {
		// get the bucket of relevant data
        List<BucketEntry> dataBucket = MapUtil.getBucket(data, "soil");
        assert(dataBucket.size() == 1);
		
        // get the global soil data
    	Map<String, String> globalData = dataBucket.get(0).getValues();
        id = MapUtil.getValueOr(globalData, "soil_id", "Unknown");
        name = MapUtil.getValueOr(globalData, "soil_name", "Unknown");
        latitude = Double.valueOf(MapUtil.getValueOr(globalData, "soil_lat", "0.0"));
        longitude = Double.valueOf(MapUtil.getValueOr(globalData, "soil_long", "0.0"));
        classification = MapUtil.getValueOr(globalData, "classification", "Unknown");
        curveNumber = Integer.valueOf(MapUtil.getValueOr(globalData, "slro", "0"));

        // TODO readilyEvaporatedWater -> needs to be calculated
        readilyEvaporatedWater = 0.0;
        
        // get the soil horizons data
        List<LinkedHashMap<String, String>> dataItems = dataBucket.get(0).getDataList();
        assert(dataItems.size() > 0);
            
        horizons.clear();
        double previousSoilLayerBaseDepth = 0.0;
        for (Map<String, String> dataItem : dataItems) {
        	// create a new item
        	SoilHorizon item = SoilHorizon.create(dataItem);
        	
        	// fill in derived data
        	item.setThickness(item.getSoilLayerBaseDepth() - previousSoilLayerBaseDepth);
        	previousSoilLayerBaseDepth = item.getSoilLayerBaseDepth();
        	
        	// TODO
        	item.setDescription("");
        	item.setCapillaryRiseEstimationParameterA(0.0);
        	item.setCapillaryRiseEstimationParameterB(0.0);
        	
        	// store it
        	horizons.add(item);
        }
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getCurveNumber() {
		return curveNumber;
	}


	public void setCurveNumber(int curveNumber) {
		this.curveNumber = curveNumber;
	}


	public double getReadilyEvaporatedWater() {
		return readilyEvaporatedWater;
	}


	public void setReadilyEvaporatedWater(double readilyEvaporatedWater) {
		this.readilyEvaporatedWater = readilyEvaporatedWater;
	}


	public List<SoilHorizon> getHorizons() {
		return horizons;
	}


	public void setHorizons(List<SoilHorizon> horizons) {
		this.horizons = horizons;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public String getClassification() {
		return classification;
	}


	public void setClassification(String classification) {
		this.classification = classification;
	}
	
}
