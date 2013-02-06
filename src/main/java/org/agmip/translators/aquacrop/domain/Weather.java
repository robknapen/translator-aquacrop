package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.DateFunctions;
import org.agmip.translators.aquacrop.tools.AgMIPFunctions;
import org.agmip.translators.aquacrop.tools.WeatherFunctions;
import org.agmip.util.MapUtil;

@SuppressWarnings({"rawtypes"}) 
public class Weather {

	private String name;
	private String weatherStationId;
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
        // get the global weather station data
        name = MapUtil.getValueOr(data, "wst_name", "Unknown");
        weatherStationId = MapUtil.getValueOr(data, "wst_id", "Unknown");
        latitude = Double.valueOf(MapUtil.getValueOr(data, "wst_lat", "0.0"));
        longitude = Double.valueOf(MapUtil.getValueOr(data, "wst_long", "0.0"));
        elevation = Double.valueOf(MapUtil.getValueOr(data, "elev", "0.0"));
        
        // get the daily weather station data
        List<HashMap<String, Object>> dataItems = AgMIPFunctions.getListOrEmptyFor(data, AgMIPFunctions.WEATHERS_DAILY_LIST_NAME);
        assert(dataItems.size() > 0);
        firstDate = (String) MapUtil.getValueOr(dataItems.get(0), "w_date", "19010101");
            
        daily.clear();
        for (Map<String, Object> dataItem : dataItems) {
        	// create a new item
        	DailyWeather item = DailyWeather.create(dataItem);
        	
        	// fill in derived data
			int day = DateFunctions.calculateDayInYear(item.getDate(), false);
			item.setET0(WeatherFunctions.calculateETReference(day, latitude, elevation, item.getMaxTemp(), item.getMinTemp()));
			
			// store it
        	daily.add(item);
        }
	}


	@Override
	public String toString() {
		return "Weather [name=" + name + ", weatherStationId="
				+ weatherStationId + ", firstDate=" + firstDate + ", latitude="
				+ latitude + ", longitude=" + longitude + ", elevation="
				+ elevation + "]";
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


	public String getWeatherStationId() {
		return weatherStationId;
	}


	public void setWeatherStationId(String weatherStationId) {
		this.weatherStationId = weatherStationId;
	}
	
}
