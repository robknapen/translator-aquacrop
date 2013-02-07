package org.agmip.translators.aquacrop.domain;

import java.util.Map;

import org.agmip.translators.aquacrop.tools.DateFunctions;
import org.agmip.util.MapUtil;

/**
 * Daily weather data relevant for AquaCrop, extracted from AgMIP data.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
@SuppressWarnings({"rawtypes"}) 
public class DailyWeather {

	private String date;
	private int[] dayMonthYear;
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
		dayMonthYear = DateFunctions.decodeDateString(date);
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
	

	public int[] getDayMonthYear() {
		return dayMonthYear;
	}


	public void setDayMonthYear(int[] dayMonthYear) {
		this.dayMonthYear = dayMonthYear;
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
