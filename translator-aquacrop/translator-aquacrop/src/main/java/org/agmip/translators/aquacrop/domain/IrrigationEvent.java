package org.agmip.translators.aquacrop.domain;

import java.util.Map;

public class IrrigationEvent {

	private int numberOfDaysAfterSowingOrPlanting; // int
	private int applicationDepth; // mm
	private double electricalConductivityOfIrrigationWater; // dS/m
	
	
	public static IrrigationEvent create(Map data) {
		IrrigationEvent obj = new IrrigationEvent();
		obj.from(data);
		return obj;
	}
	
	
	public void from(Map data) {
		// TODO
	}
}
