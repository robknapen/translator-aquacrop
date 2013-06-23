package org.agmip.translators.aquacrop.domain;

import java.util.HashMap;
import java.util.Map;

public class SimulationRun {

	public enum FileName {
		CLIMATE, TEMPERATURE, REFERENCE_ET, RAIN, ATMOSPHERIC_CO2, 
		CROP, IRRIGATION, MANAGEMENT, SOIL_PROFILE, GROUNDWATER, 
		INITIAL_CONDITIONS, OFF_SEASON_CONDITIONS
	}

	private long firstDayOfSimulationPeriod; // sdat
	private long lastDayOfSimulationPeriod; // endat
	private long firstDayOfCroppingPeriod; // fdate + 1
	private long lastDayOfCroppingPeriod; // hadat -> can be endat, aquacrop will derive cropping date from climate files
	
	private String name; // exname
	private String notes; // tr_notes
	private String soilId; // soil_id
	private String weatherStationId; // wst_id
	private Double latitude; // fl_lat
	private Double longitude; // fl_long
	private Double area; // farea
	
	private HashMap<FileName, String> fileNames = new HashMap<>();
	
	
	@SuppressWarnings("rawtypes")
	public static SimulationRun create(Map data) {
		SimulationRun instance = new SimulationRun();
		instance.from(data);
		return instance;
	}
	
	
	@SuppressWarnings("rawtypes")
	public void from(Map experimentData) {
		assert (experimentData instanceof HashMap);
		
		// TODO experimentData should be a single entry from the ACE experiments bucket
		
		// add simulation period from experiment data
//		firstDayOfSimulationPeriod = DateFunctions.calculateDayNumberFromAceData(experimentData, "sdat", null, true);
//		lastDayOfSimulationPeriod = DateFunctions.calculateDayNumberFromAceData(experimentData, "endat", null, true);
//		firstDayOfCroppingPeriod = DateFunctions.calculateDayNumberFromAceData(experimentData, "pdate", null, true);
//		firstDayOfCroppingPeriod++;
//		lastDayOfCroppingPeriod = lastDayOfSimulationPeriod;
		
		// get the global experiment data
//        name = MapUtil.getValueOr(experimentData, "exname", "Unknown");
//        notes = MapUtil.getValueOr(experimentData, "tr_notes", "Unknown");
//        soilId = MapUtil.getValueOr(experimentData, "soil_id", "Unknown");
//        weatherStationId = MapUtil.getValueOr(experimentData, "wst_id", "Unknown");
//        latitude = Double.valueOf(MapUtil.getValueOr(experimentData, "fl_lat", "0.0"));
//        longitude = Double.valueOf(MapUtil.getValueOr(experimentData, "fl_long", "0.0"));
//        area = Double.valueOf(MapUtil.getValueOr(experimentData, "farea", "0.0"));
	}
	
	
	public void addFileName(FileName fileNameType, String fileName) {
		fileNames.put(fileNameType, fileName);
	}
	
	
	public String getFileName(FileName fileNameType) {
		return fileNames.get(fileNameType);
	}


	public long getFirstDayOfSimulationPeriod() {
		return firstDayOfSimulationPeriod;
	}


	public void setFirstDayOfSimulationPeriod(long firstDayOfSimulationPeriod) {
		this.firstDayOfSimulationPeriod = firstDayOfSimulationPeriod;
	}


	public long getLastDayOfSimulationPeriod() {
		return lastDayOfSimulationPeriod;
	}


	public void setLastDayOfSimulationPeriod(long lastDayOfSimulationPeriod) {
		this.lastDayOfSimulationPeriod = lastDayOfSimulationPeriod;
	}


	public long getFirstDayOfCroppingPeriod() {
		return firstDayOfCroppingPeriod;
	}


	public void setFirstDayOfCroppingPeriod(long firstDayOfCroppingPeriod) {
		this.firstDayOfCroppingPeriod = firstDayOfCroppingPeriod;
	}


	public long getLastDayOfCroppingPeriod() {
		return lastDayOfCroppingPeriod;
	}


	public void setLastDayOfCroppingPeriod(long lastDayOfCroppingPeriod) {
		this.lastDayOfCroppingPeriod = lastDayOfCroppingPeriod;
	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public String getSoilId() {
		return soilId;
	}


	public void setSoilId(String soilId) {
		this.soilId = soilId;
	}


	public String getWeatherStationId() {
		return weatherStationId;
	}


	public void setWeatherStationId(String weatherStationId) {
		this.weatherStationId = weatherStationId;
	}


	public Double getLatitude() {
		return latitude;
	}


	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	public Double getArea() {
		return area;
	}


	public void setArea(Double area) {
		this.area = area;
	}
	
}
