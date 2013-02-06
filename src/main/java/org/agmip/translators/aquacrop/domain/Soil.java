package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.AgMIP;
import org.agmip.translators.aquacrop.tools.MapHelper;
import org.agmip.translators.aquacrop.tools.SoilDataCalculator;
import org.agmip.util.MapUtil;


@SuppressWarnings({"rawtypes"})
public class Soil {

	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private String classification;
	private int curveNumber;
	private int readilyEvaporatedWater;
	private List<SoilLayer> layers = new ArrayList<SoilLayer>();

	
	public static Soil create(Map data) {
		Soil s = new Soil();
		s.from(data);
		return s;
	}
	
	
	public void from(Map data) {
        // get the global soil data
        id = MapUtil.getValueOr(data, "soil_id", "Unknown");
        name = MapUtil.getValueOr(data, "soil_name", "Unknown");
        latitude = Double.valueOf(MapUtil.getValueOr(data, "soil_lat", "0.0"));
        longitude = Double.valueOf(MapUtil.getValueOr(data, "soil_long", "0.0"));
        classification = MapUtil.getValueOr(data, "classification", "Unknown");
        curveNumber = Integer.valueOf(MapUtil.getValueOr(data, "slro", "0"));

        // extract the soil layers
        layers.clear();
        double previousSoilLayerBaseDepth = 0.0;
        for (HashMap<String, Object> soilLayerData : MapHelper.getListOrEmptyFor(data, AgMIP.SOILS_LAYERS_LIST_NAME)) {
        	// create a new item
        	SoilLayer item = SoilLayer.create(soilLayerData);
        	
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
        	layers.add(item);
        }
        
        // TODO: reduce number of soil layers to a max of 5
        // repeatedly take average of last two until at 5 layers or less
        
        // calculate readilyEvaporatedWater from top soil horizon
        SoilLayer top = layers.get(0);
        readilyEvaporatedWater = (int)SoilDataCalculator.calculateReadilyEvaporableWater(
        		top.getSoilWaterContentAtFieldCapacity(),
        		top.getSoilWaterContentAtPermanentWiltingPoint());
	}
	

	@Override
	public String toString() {
		return "Soil [id=" + id + ", name=" + name + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
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


	public List<SoilLayer> getLayers() {
		return layers;
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
