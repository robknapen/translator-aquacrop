package org.agmip.translators.aquacrop.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agmip.ace.AceEvent;
import org.agmip.ace.AceEventCollection;
import org.agmip.ace.AceExperiment;
import org.agmip.translators.aquacrop.tools.AgMIPFunctions;

/**
 * Data for a single AgMIP crop experiment.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
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

	
	public static Experiment create(AceExperiment aceExperiment) throws IOException {
		Experiment instance = new Experiment();
		instance.from(aceExperiment);
		return instance;
	}
	
	
	public void from(AceExperiment aceExperiment) throws IOException {
		name = aceExperiment.getValueOr("exname", "Unnamed");
		fieldName = aceExperiment.getValueOr("fl_name", "Unnamed");
        fieldLatitude = Double.valueOf(aceExperiment.getValueOr("fl_lat", "0.0"));
        fieldLongitude = Double.valueOf(aceExperiment.getValueOr("fl_long", "0.0"));
        institution = aceExperiment.getValueOr("institution", "Undefined");
        personNotes = aceExperiment.getValueOr("pr_notes", "");
        tillageNotes = aceExperiment.getValueOr("ti_notes", "");
        fertilizerNotes = aceExperiment.getValueOr("fe_notes", "");
        treatmentNotes = aceExperiment.getValueOr("tr_notes", "");
        fieldNotes = aceExperiment.getValueOr("fl_notes", "");
        
		// Example: AceEventCollection aceIrrigationEvents = ((AceExperiment) data).getEvents().filterByEvent(AceEventType.ACE_IRRIGATION_EVENT).sort();
        
        // get all management events, sorted
        AceEventCollection aceEvents = aceExperiment.getEvents().sort();
        events.clear();
        for (AceEvent aceEvent : aceEvents) {
        	ManagementEvent event = createEvent(aceEvent);
        	if (event != null) {
	        	events.add(event);
        	}
        }
	}


	/**
	 * Sort comparator for sorting the management events by date.
	 */
	public class SortEventByDate implements Comparator<ManagementEvent> {
		public int compare(ManagementEvent e1, ManagementEvent e2) {
			return e1.getDate().compareTo(e2.getDate());
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


	public ManagementEvent createEvent(AceEvent aceEvent) throws IOException {
		String event = aceEvent.getValueOr("event", "Unknown");
		if (AgMIPFunctions.MANAGEMENT_EVENT_PLANT.equalsIgnoreCase(event)) {
			return PlantingEvent.create(aceEvent);
		}
		if (AgMIPFunctions.MANAGEMENT_EVENT_IRRIGATE.equalsIgnoreCase(event)) {
			return IrrigationEvent.create(aceEvent);
		}
		if (AgMIPFunctions.MANAGEMENT_EVENT_FERTILIZE.equalsIgnoreCase(event)) {
			return FertilizerEvent.create(aceEvent);
		}
		if (AgMIPFunctions.MANAGEMENT_EVENT_ORGANIC.equalsIgnoreCase(event)) {
			return OrganicMatterEvent.create(aceEvent);
		}
		if (AgMIPFunctions.MANAGEMENT_EVENT_HARVEST.equalsIgnoreCase(event)) {
			return HarvestingEvent.create(aceEvent);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T extends ManagementEvent> List<T> getEvents(Class<T> clazz) {
		ArrayList<T> result = new ArrayList<T>();
		for (ManagementEvent event : events) {
			if (event.getClass() == clazz) {
				result.add((T)event);
			}
		}
		return result;
	}
	
	
	/**
     * Method to extract all management events, sorted in a Map with as key 
     * the different class types and a list of the management event. In some 
     * cases it will be only one event, for example, sowing, but in other 
     * cases it can be many events (irrigation, fertilization).
     *
     * @return Map of created events
     */
     public Map<Class<? extends ManagementEvent>, List<ManagementEvent>> getEvents () {
        Map<Class<? extends ManagementEvent>, List<ManagementEvent>> resultSet = new HashMap<Class<? extends ManagementEvent>, List<ManagementEvent>>();

		for (ManagementEvent event : events) {
			if (resultSet.containsKey(event.getClass())) {
			   List<ManagementEvent> magSet = resultSet.get(event.getClass());
			   magSet.add(event);
			} else {
			   List<ManagementEvent> newMagSet = new ArrayList<ManagementEvent>();
			   newMagSet.add(event);
			   resultSet.put(event.getClass(), newMagSet);
			}
		}
     
        return resultSet;
     }	
	
	
	/**
	 * Creates an Irrigation instance from the management events in the
	 * experiment. if the events can not be parsed into usable input for
	 * the model the method will return null.
	 * 
	 * @return Irrigation instance or null
	 */
	public Irrigation getIrrigation() {
		return Irrigation.create(events);
	}
	
	
	/**
	 * Creates a FieldManagement instance from the management events in the
	 * experiment. if the events can not be parsed into usable input for
	 * the model the method will return null.
	 * 
	 * @return FieldManagement instance or null
	 */
	public FieldManagement getFieldManagement() {
		return FieldManagement.create(events);
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
