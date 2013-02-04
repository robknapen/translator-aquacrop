package org.agmip.translators.aquacrop.domain;

import java.util.Map;

import org.agmip.translators.aquacrop.tools.DayNumbers;
import org.agmip.translators.aquacrop.tools.MapHelper;


@SuppressWarnings({"rawtypes"})
public class ManagementEvent {

	private String event;
	private String date;
	private int[] dayMonthYear;

	
	public void from(Map data) {
		event = MapHelper.getValueFor(data, "Unknown", "event");
		date = MapHelper.getValueFor(data, "190101", "date");
		dayMonthYear = DayNumbers.decodeDateString(date);
	}
	
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
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

}
