package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.AgMIPFunctions;
import org.agmip.util.MapUtil;

/**
 * Data for a single AgMIP crop experiment.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
@SuppressWarnings("rawtypes")
public class Experiment {

	private String name; // exname
	private String fieldName; // fl_name
	private String fieldLocationDescription; // fl_loc
	private double fieldLatitude; // fl_lat
	private double fieldLongitude; // fl_long
	private String institution; // institution
	private String personNotes; // pr_notes
	private String tillageNotes; // ti_notes
	private String fertilizerNotes; // fe_notes
	private String treatmentNotes; // tr_notes
	private String fieldNotes; // fl_notes
	
	protected List<ManagementEvent> events = new ArrayList<ManagementEvent>();

	
	public static Experiment create(Map data) {
		Experiment instance = new Experiment();
		instance.from(data);
		return instance;
	}
	
	
	@SuppressWarnings("unchecked")
	public void from(Map data) {
		name = MapUtil.getValueOr(data, "exname", "Unnamed");
		fieldName = MapUtil.getValueOr(data, "fl_name", "Unnamed");
		
        fieldLatitude = Double.valueOf(MapUtil.getValueOr(data, "fl_lat", "0.0"));
        fieldLongitude = Double.valueOf(MapUtil.getValueOr(data, "fl_long", "0.0"));
		
        institution = MapUtil.getValueOr(data, "institution", "Undefined");
        personNotes = MapUtil.getValueOr(data, "pr_notes", "");
        tillageNotes = MapUtil.getValueOr(data, "ti_notes", "");
        fertilizerNotes = MapUtil.getValueOr(data, "fe_notes", "");
        treatmentNotes = MapUtil.getValueOr(data, "tr_notes", "");
        fieldNotes = MapUtil.getValueOr(data, "fl_notes", "");
        
		// get the bucket of relevant data
		Map<String, Object> bucket = MapUtil.getRawBucket(data, AgMIPFunctions.MANAGEMENT_BUCKET_NAME);
        assert(bucket != null);

        // extract the management events
        events.clear();
        for (HashMap<String, Object> eventData : AgMIPFunctions.getListOrEmptyFor(bucket, AgMIPFunctions.MANAGEMENT_EVENTS_LIST_NAME)) {
        	ManagementEvent event = createEvent(eventData);
        	if (event != null) {
	        	events.add(event);
        	}
        }
	}

	
	@Override
	public String toString() {
		return "Experiment [name=" + name + ", fieldName=" + fieldName
				+ ", fieldLatitude=" + fieldLatitude + ", fieldLongitude="
				+ fieldLongitude + "]";
	}
	
	
	public String getId() {
		String name = getName();
		if (name != null) {
			name = name.replaceAll(" ", "-");
		}
		return name;
	}


	public ManagementEvent createEvent(Map data) {
		String event = AgMIPFunctions.getValueFor(data, "Unknown", "event");
		if (AgMIPFunctions.MANAGEMENT_EVENT_PLANT.equalsIgnoreCase(event)) {
			return PlantingEvent.create(data);
		}
		if (AgMIPFunctions.MANAGEMENT_EVENT_IRRIGATE.equalsIgnoreCase(event)) {
			return IrrigationEvent.create(data);
		}
		if (AgMIPFunctions.MANAGEMENT_EVENT_HARVEST.equalsIgnoreCase(event)) {
			return HarvestingEvent.create(data);
		}
		return null;
	}
	
	
	public List<PlantingEvent> getPlantingEvents() {
		ArrayList<PlantingEvent> result = new ArrayList<PlantingEvent>();
		for (ManagementEvent event : events) {
			if (event instanceof PlantingEvent) {
				result.add((PlantingEvent)event);
			}
		}
		return result;
	}
	
	
	public List<IrrigationEvent> getIrrigationEvents() {
		ArrayList<IrrigationEvent> result = new ArrayList<IrrigationEvent>();
		for (ManagementEvent event : events) {
			if (event instanceof IrrigationEvent) {
				result.add((IrrigationEvent)event);
			}
		}
		return result;
	}
	
	
	/**
	 * Creates and irrigation instance of the management events in the
	 * experiment. if the events can not be parsed into usable input for
	 * the model the method will return null.
	 * 
	 * @return irrigation instance or null
	 */
	public Irrigation getIrrigation() {
		return Irrigation.create(events);
	}
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public String getFieldLocationDescription() {
		return fieldLocationDescription;
	}


	public void setFieldLocationDescription(String fieldLocationDescription) {
		this.fieldLocationDescription = fieldLocationDescription;
	}


	public double getFieldLatitude() {
		return fieldLatitude;
	}


	public void setFieldLatitude(double fieldLatitude) {
		this.fieldLatitude = fieldLatitude;
	}


	public double getFieldLongitude() {
		return fieldLongitude;
	}


	public void setFieldLongitude(double fieldLongitude) {
		this.fieldLongitude = fieldLongitude;
	}


	public String getInstitution() {
		return institution;
	}


	public void setInstitution(String institution) {
		this.institution = institution;
	}


	public String getPersonNotes() {
		return personNotes;
	}


	public void setPersonNotes(String personNotes) {
		this.personNotes = personNotes;
	}


	public String getTillageNotes() {
		return tillageNotes;
	}


	public void setTillageNotes(String tillageNotes) {
		this.tillageNotes = tillageNotes;
	}


	public String getFertilizerNotes() {
		return fertilizerNotes;
	}


	public void setFertilizerNotes(String fertilizerNotes) {
		this.fertilizerNotes = fertilizerNotes;
	}


	public String getTreatmentNotes() {
		return treatmentNotes;
	}


	public void setTreatmentNotes(String treatmentNotes) {
		this.treatmentNotes = treatmentNotes;
	}


	public String getFieldNotes() {
		return fieldNotes;
	}


	public void setFieldNotes(String fieldNotes) {
		this.fieldNotes = fieldNotes;
	}
	
}
