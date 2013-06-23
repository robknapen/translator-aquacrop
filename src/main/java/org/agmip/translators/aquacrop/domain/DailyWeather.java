package org.agmip.translators.aquacrop.domain;

import java.io.IOException;

import org.agmip.ace.AceRecord;
import org.agmip.translators.aquacrop.tools.DateFunctions;

/**
 * Daily weather data relevant for AquaCrop, extracted from AgMIP data.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
public class DailyWeather {

	// ace data
	private String date;
	private int[] dayMonthYear;
	private double minTemp;
	private double maxTemp;
	private double rain;
	private double ET0;
	
	// derived data
	private long dayNumber;
	
	
	public static DailyWeather create(AceRecord aceDailyWeather) throws IOException {
		DailyWeather dw = new DailyWeather();
		dw.from(aceDailyWeather);
		return dw;
	}

	
	public void from(AceRecord aceDailyWeather) throws IOException {
		setDate(aceDailyWeather.getValueOr("w_date", "19010101"));
		dayMonthYear = DateFunctions.decodeDateString(date);
        minTemp = Double.valueOf(aceDailyWeather.getValueOr("tmin", "12.0"));
        maxTemp = Double.valueOf(aceDailyWeather.getValueOr("tmax", "28.0"));
        rain = Double.valueOf(aceDailyWeather.getValueOr("rain", "0.0"));
        ET0 = Double.valueOf(aceDailyWeather.getValueOr("evap", "5.0"));
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
		this.dayNumber = DateFunctions.calculateDayInYear(this.date, false);
	}
	

	public long getDayNumber() {
		return dayNumber;
	}


	public void setDayNumber(long dayNumber) {
		this.dayNumber = dayNumber;
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
