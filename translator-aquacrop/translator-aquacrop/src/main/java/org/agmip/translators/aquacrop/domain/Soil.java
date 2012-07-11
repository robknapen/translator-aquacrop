package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SuppressWarnings({"rawtypes", "unchecked"}) 
public class Soil {

	private String name;
	private int curveNumber;
	private int readilyEvaporatedWater;
	private List<SoilHorizon> horizons = new ArrayList<SoilHorizon>();

	
	public static Soil create(Map data) {
		Soil s = new Soil();
		s.from(data);
		return s;
	}
	
	
	public void from(Map data) {
		// TODO
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
	
}
