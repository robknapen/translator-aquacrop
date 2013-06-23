package org.agmip.translators.aquacrop.domain;

import java.io.IOException;

import org.agmip.ace.AceEvent;
import org.agmip.translators.aquacrop.tools.DateFunctions;


public class ManagementEvent {

	private String event;
	private String date;
	private int[] dayMonthYear;

	
	public boolean from(AceEvent aceEvent) throws IOException {
		event = aceEvent.getValueOr("event", "Unknown");
		date = aceEvent.getValueOr("date", "19010101");
		dayMonthYear = DateFunctions.decodeDateString(date);
		return true;
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
