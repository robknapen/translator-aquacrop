package org.agmip.translators.aquacrop;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.agmip.ace.AceBaseComponentType;
import org.agmip.ace.AceDataset;
import org.agmip.ace.translators.io.TranslatorOutput;
import org.agmip.translators.aquacrop.domain.Experiment;
import org.agmip.translators.aquacrop.domain.Experiments;
import org.agmip.translators.aquacrop.domain.PlantingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translates AgMIP data into input for the AquaCrop model.
 * 
 * This translator takes the AgMIP experiments data and creates a Project
 * file for AquaCrop that can be used to run it for the multiple cropping
 * sequences defined.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
public class AquaCropTranslatorOutput extends FileCreator implements TranslatorOutput {

    private static final Logger LOG = LoggerFactory.getLogger(AquaCropTranslatorOutput.class);
	
	// TODO: AquaCrop model installation path? - Environment variable?
	
	// TODO
	// Variable for file path
	// Field management event "planting", will get aquacrop_cul_id field, eg Sorghum_320.cro
	// otherwise use cul_id and if unknown cultivar do crash (and log)
	// Use aquacrop_cul field to select proper AquaCrop crop file
	// Get cropping season length (in calendar days) from filename (encoded)
	// Use cropping season length to set harvesting data (in AquaCrop project file)
	
	// Data can have multiple experiments (experiments bucket) or only a single experiment (no experiments bucket)
	// {experiment[].}management.events
	// management.events[] can contain multiple planting events 
    
    // TODO: Process all soils
    // TODO: Process all weathers
    // TODO: Select soil for each experiment based on specified soil id
    // TODO: Select climate for each experiment based on specified weather station id

    private boolean containsComponentType(AceBaseComponentType[] components, AceBaseComponentType component) {
    	for (AceBaseComponentType c : components) {
    		if (c.equals(component)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    
	public void write(File outputDirectory, AceDataset data, AceBaseComponentType... components) {
		// TODO: create AquaCrop input data for ace component types specified
		// TODO: when all types are created also add project file(s)
		
		LOG.debug("Creating AquaCrop input files in directory: " + outputDirectory.getAbsolutePath());
		
		// extract the soil data when selected
		if (containsComponentType(components, AceBaseComponentType.ACE_SOIL)) {
			try {
				LOG.debug("Creating soil input files for AquaCrop model from AgMIP data ...");
				new SoilInputCreator().writeFiles(outputDirectory, "ace", data);
				LOG.debug("Done");
			} catch (IOException e) {
				LOG.error("Error creating soil input data from ace dataset: " + e.getMessage());
			}
		}
		
		// extract the weather data when selected
		if (containsComponentType(components, AceBaseComponentType.ACE_WEATHER)) {
			try {
				LOG.debug("Creating climate input files for AquaCrop model from AgMIP data ...");
				new ClimateInputCreator().writeFiles(outputDirectory, "ace", data);
				LOG.debug("Done");
			} catch (IOException e) {
				LOG.error("Error creating climate input data from ace dataset: " + e.getMessage());
			}
		}
		
		// extract the experiment data needed
		if (containsComponentType(components, AceBaseComponentType.ACE_EXPERIMENT)) {
			try {
				Experiments experiments = Experiments.create(data);
				assert(experiments.getItems().size() > 0);
		
				// NOTE: Only generate type of data that is requested, do not generate dependencies, e.g. 
				// referenced climate and soil files !!
				
				// TODO: Irrigation input files for AquaCrop
				// TODO: Field management input file for AquaCrop
				// TODO: Project file with simulation runs for AquaCrop
				
				// process the experiments
				for (Experiment experiment : experiments.getItems()) {
					LOG.debug(experiment.toString());
					List<PlantingEvent> events = experiment.getEvents(PlantingEvent.class);
					for (PlantingEvent event : events) {
						LOG.debug(event.toString());
					}
				}
				LOG.debug("Number of experiment records read: " + experiments.getItems().size());
			} catch (IOException e) {
				LOG.error("Error creating experiment input data from ace dataset: " + e.getMessage());
			}
		}
		
		LOG.debug("Done");
	}
}
