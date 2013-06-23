package org.agmip.translators.aquacrop.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.agmip.ace.AceRecord;
import org.agmip.ace.AceRecordCollection;
import org.agmip.ace.AceWeather;
import org.agmip.translators.aquacrop.tools.WeatherFunctions;

/**
 * Weather data relevant for AquaCrop, extracted from AgMIP data.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
public class Weather {

	private String name;
	private String weatherStationId;
	private String firstDate;
	private double latitude;
	private double longitude;
	private double elevation;
	private List<DailyWeather> daily = new ArrayList<DailyWeather>();
	
	
	public static Weather create(AceWeather aceWeather) throws IOException {
		Weather w = new Weather();
		w.from(aceWeather);
		return w;
	}
	
	
	public void from(AceWeather aceWeather) throws IOException {

		// get the global weather station data
		name = aceWeather.getValueOr("wst_name", "Unknown");
        weatherStationId = aceWeather.getValueOr("wst_id", "Unknown");
        latitude = Double.valueOf(aceWeather.getValueOr("wst_lat", "0.0"));
        longitude = Double.valueOf(aceWeather.getValueOr("wst_long", "0.0"));
        elevation = Double.valueOf(aceWeather.getValueOr("elev", "0.0"));
        
        // TODO: get daily ET0
        // HashMap<String, ArrayList<String>> dailyEt0 = WeatherHelper.getEto((HashMap)data);

        // get the daily weather station data, should be chronologically sorted
        AceRecordCollection aceDailyWeathers = aceWeather.getDailyWeather();
        assert(aceDailyWeathers.size() > 0);
        
        for (AceRecord aceDailyWeather : aceDailyWeathers) {
        	DailyWeather item = DailyWeather.create(aceDailyWeather);
        	// fill in derived data
			item.setET0(WeatherFunctions.calculateETReference(item.getDayNumber(), latitude, elevation, item.getMaxTemp(), item.getMinTemp()));
			// store it
        	daily.add(item);
        }
        
        firstDate = daily.get(0).getDate();
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
