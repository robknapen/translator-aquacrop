package org.agmip.translators.aquacrop.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.agmip.ace.AceRecord;
import org.agmip.ace.AceRecordCollection;
import org.agmip.ace.AceSoil;
import org.agmip.translators.aquacrop.tools.SoilFunctions;

/**
 * Soil data relevant for AquaCrop, extracted from AgMIP data.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
public class Soil {

	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private String classification;
	private int curveNumber;
	private int readilyEvaporatedWater;
	private List<SoilLayer> layers = new ArrayList<SoilLayer>();

	
	public static Soil create(AceSoil aceSoil) throws IOException {
		Soil s = new Soil();
		s.from(aceSoil);
		return s;
	}
	
	
	public void from(AceSoil aceSoil) throws IOException {
        // get the global soil data
		id = aceSoil.getId();
		name = aceSoil.getValueOr("soil_name", "Unknown");
		latitude = Double.valueOf(aceSoil.getValueOr("soil_lat", "0.0"));
		longitude = Double.valueOf(aceSoil.getValueOr("soil_long", "0.0"));
		classification = aceSoil.getValueOr("classification", "Unknown");
		curveNumber = Integer.valueOf(aceSoil.getValueOr("slro", "0"));
		
        // extract the soil layers
        layers.clear();
        double previousSoilLayerBaseDepth = 0.0;
        AceRecordCollection aceSoilLayers = aceSoil.getSoilLayers();
        for (AceRecord aceSoilLayer : aceSoilLayers) {
        	SoilLayer item = SoilLayer.create(aceSoilLayer);
        	
        	// fill in derived data
        	item.setThickness(item.getSoilLayerBaseDepth() - previousSoilLayerBaseDepth);
        	previousSoilLayerBaseDepth = item.getSoilLayerBaseDepth();
        	
        	int soilClass = SoilFunctions.calculateSoilClass(
        			item.getSoilWaterContentAtSaturation(), 
        			item.getSoilWaterContentAtPermanentWiltingPoint(), 
        			item.getSoilWaterContentAtFieldCapacity(), 
        			item.getSaturatedHydrolicConductivity());
        	
        	item.setDescription(SoilFunctions.soilClassDescription(soilClass));
        	
        	item.setCapillaryRiseEstimationParameterA(
        			SoilFunctions.calculateCapillaryRiseEstimationParameterA(
        					soilClass, item.getSaturatedHydrolicConductivity()));
        	
        	item.setCapillaryRiseEstimationParameterB(
        			SoilFunctions.calculateCapillaryRiseEstimationParameterB(
        					soilClass, item.getSaturatedHydrolicConductivity()));
        	
        	// store it
        	layers.add(item);
        }
        
        // TODO: reduce number of soil layers to a max of 5
        // repeatedly take average of last two until at 5 layers or less
        
        // calculate readilyEvaporatedWater from top soil horizon
        SoilLayer top = layers.get(0);
        readilyEvaporatedWater = (int)SoilFunctions.calculateReadilyEvaporableWater(
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
