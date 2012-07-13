package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.SoilDataCalculator;
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
	private int readilyEvaporatedWater;
	private List<SoilHorizon> horizons = new ArrayList<SoilHorizon>();

	
	public static Soil create(Map data) {
		Soil s = new Soil();
		s.from(data);
		return s;
	}
	
	
	public void from(Map data) {
		// get the bucket of relevant data
        BucketEntry dataBucket = MapUtil.getBucket(data, "soil");
        assert(dataBucket != null);
		
        // get the global soil data
    	Map<String, String> globalData = dataBucket.getValues();
        id = MapUtil.getValueOr(globalData, "soil_id", "Unknown");
        name = MapUtil.getValueOr(globalData, "soil_name", "Unknown");
        latitude = Double.valueOf(MapUtil.getValueOr(globalData, "soil_lat", "0.0"));
        longitude = Double.valueOf(MapUtil.getValueOr(globalData, "soil_long", "0.0"));
        classification = MapUtil.getValueOr(globalData, "classification", "Unknown");
        curveNumber = Integer.valueOf(MapUtil.getValueOr(globalData, "slro", "0"));

        // get the soil horizons data
        List<LinkedHashMap<String, String>> dataItems = dataBucket.getDataList();
        assert(dataItems.size() > 0);

        horizons.clear();
        double previousSoilLayerBaseDepth = 0.0;
        for (Map<String, String> dataItem : dataItems) {
        	// create a new item
        	SoilHorizon item = SoilHorizon.create(dataItem);
        	
        	// fill in derived data
        	item.setThickness(item.getSoilLayerBaseDepth() - previousSoilLayerBaseDepth);
        	previousSoilLayerBaseDepth = item.getSoilLayerBaseDepth();
        	
        	int soilClass = SoilDataCalculator.calculateSoilClass(
        			item.getSoilWaterContentAtSaturation(), 
        			item.getSoilWaterContentAtPermanentWiltingPoint(), 
        			item.getSoilWaterContentAtFieldCapacity(), 
        			item.getSaturatedHydrolicConductivity());
        	
        	item.setDescription(SoilDataCalculator.soilClassDescription(soilClass));
        	
        	item.setCapillaryRiseEstimationParameterA(
        			SoilDataCalculator.calculateCapillaryRiseEstimationParameterA(
        					soilClass, item.getSaturatedHydrolicConductivity()));
        	
        	item.setCapillaryRiseEstimationParameterB(
        			SoilDataCalculator.calculateCapillaryRiseEstimationParameterB(
        					soilClass, item.getSaturatedHydrolicConductivity()));
        	
        	// store it
        	horizons.add(item);
        }
        
        // TODO: reduce number of soil layers to a max of 5
        // repeatedly take average of last two until at 5 layers or less
        
        // calculate readilyEvaporatedWater from top soil horizon
        SoilHorizon top = horizons.get(0);
        readilyEvaporatedWater = (int)SoilDataCalculator.calculateReadilyEvaporableWater(
        		top.getSoilWaterContentAtFieldCapacity(),
        		top.getSoilWaterContentAtPermanentWiltingPoint());
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


	public int getReadilyEvaporatedWater() {
		return readilyEvaporatedWater;
	}


	public void setReadilyEvaporatedWater(int readilyEvaporatedWater) {
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
