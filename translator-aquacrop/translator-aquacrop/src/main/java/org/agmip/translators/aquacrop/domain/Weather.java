package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.DayNumbers;
import org.agmip.translators.aquacrop.tools.EToCalculator;
import org.agmip.util.MapUtil;
import org.agmip.util.MapUtil.BucketEntry;

@SuppressWarnings({"rawtypes", "unchecked"}) 
public class Weather {

	private String name;
	private String firstDate;
	private double latitude;
	private double longitude;
	private double elevation;
	private List<DailyWeather> daily = new ArrayList<DailyWeather>();
	
	
	public static Weather create(Map data) {
		Weather w = new Weather();
		w.from(data);
		return w;
	}
	
	
	public void from(Map data) {
		// get the block of weather data
        List<BucketEntry> weatherData = MapUtil.getBucket(data, "weather");
        assert(weatherData.size() == 1);
        
        // get the global weather station data
    	Map<String, String> globalWeatherData = weatherData.get(0).getValues();
        name = MapUtil.getValueOr(globalWeatherData, "wst_name", "Unknown");
        latitude = Double.valueOf(MapUtil.getValueOr(globalWeatherData, "wst_lat", "0.0"));
        longitude = Double.valueOf(MapUtil.getValueOr(globalWeatherData, "wst_long", "0.0"));
        elevation = Double.valueOf(MapUtil.getValueOr(globalWeatherData, "elev", "0.0"));
        
        // get the daily weather station data
        List<LinkedHashMap<String, String>> dailyWeatherData = weatherData.get(0).getDataList();
        assert(dailyWeatherData.size() > 0);
        firstDate = (String) MapUtil.getValueOr(dailyWeatherData.get(0), "w_date", "19010101");
            
        daily.clear();
        for (Map<String, String> dailyData : dailyWeatherData) {
        	daily.add(DailyWeather.create(dailyData));
        }
        
        calculateET0();
	}
	
	
	public void calculateET0() {
		for (DailyWeather dw : daily) {
			int day = DayNumbers.calculateDayInYear(dw.getDate(), false);
			dw.setET0(EToCalculator.calculateETReference(day, latitude, elevation, dw.getMaxTemp(), dw.getMinTemp()));
		}
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFirstDate() {
		return firstDate;
	}


	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
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


	public double getElevation() {
		return elevation;
	}


	public void setElevation(double elevation) {
		this.elevation = elevation;
	}


	public List<DailyWeather> getDaily() {
		return daily;
	}


	public void setDaily(List<DailyWeather> daily) {
		this.daily = daily;
	}
	
}