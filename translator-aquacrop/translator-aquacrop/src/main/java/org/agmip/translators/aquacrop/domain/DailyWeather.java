package org.agmip.translators.aquacrop.domain;

import java.util.Map;

import org.agmip.util.MapUtil;

@SuppressWarnings({"rawtypes", "unchecked"}) 
public class DailyWeather {

	private String date;
	private int day;
	private int month;
	private int year;
	private double minTemp;
	private double maxTemp;
	private double rain;
	private double ET0;
	
	
	public static DailyWeather create(Map data) {
		DailyWeather dw = new DailyWeather();
		dw.from(data);
		return dw;
	}

	
	public void from(Map data) {
		date = MapUtil.getValueOr(data, "w_date", "19010101");
        day = Integer.valueOf(date.substring(6, 8));
        month = Integer.valueOf(date.substring(4, 6));
        year = Integer.valueOf(date.substring(0, 4));
        minTemp = Double.valueOf(MapUtil.getValueOr(data, "tmin", "12.0"));
        maxTemp = Double.valueOf(MapUtil.getValueOr(data, "tmax", "28.0"));
        rain = Double.valueOf(MapUtil.getValueOr(data, "rain", "0.0"));
        ET0 = Double.valueOf(MapUtil.getValueOr(data, "evap", "5.0"));
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getDay() {
		return day;
	}


	public void setDay(int day) {
		this.day = day;
	}


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public double getMinTemp() {
		return minTemp;
	}


	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}


	public double getMaxTemp() {
		return maxTemp;
	}


	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}


	public double getRain() {
		return rain;
	}


	public void setRain(double rain) {
		this.rain = rain;
	}


	public double getET0() {
		return ET0;
	}


	public void setET0(double eT0) {
		ET0 = eT0;
	}
	
}
