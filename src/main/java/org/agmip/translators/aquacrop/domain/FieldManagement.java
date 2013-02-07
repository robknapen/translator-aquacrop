package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates field management data usable as input for the AquaCrop model,
 * from AgMIP management event data. The specified management events are
 * validated. 
 * 
 * @author Rob Knapen, Alterra Wageningen-UR, The Netherlands
 */
public class FieldManagement {

    private static final Logger LOG = LoggerFactory.getLogger(FieldManagement.class);	
	
	// TODO
    
	private String name;

	private List<FertilizerEvent> items = new ArrayList<FertilizerEvent>();
	
	
	/**
	 * Creates an instance based on the specified list of events. The
	 * instance will be returned, or null when the events could not be
	 * parsed into a valid irrigation instance.
	 * 
	 * @param events to use to populate the instance
	 * @return created instance or null
	 */
	public static FieldManagement create(List<ManagementEvent> events) {
		FieldManagement obj = new FieldManagement();
		if (obj.from(events)) {
			return obj;
		} else {
			return null;
		}
	}
	
	
	public boolean from(List<ManagementEvent> managementEvents) {
		// TODO
		return false;
	}	
	
}
