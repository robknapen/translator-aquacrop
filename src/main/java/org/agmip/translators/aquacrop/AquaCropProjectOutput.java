package org.agmip.translators.aquacrop;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.aquacrop.domain.Experiment;
import org.agmip.translators.aquacrop.domain.Experiments;
import org.agmip.translators.aquacrop.domain.PlantingEvent;
import org.agmip.translators.aquacrop.domain.Soil;
import org.agmip.translators.aquacrop.domain.Soils;
import org.agmip.translators.aquacrop.domain.Weather;
import org.agmip.translators.aquacrop.domain.Weathers;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translates AgMIP data into input for the AquaCrop model.
 * 
 * This translator takes the AgMIP experiments data and creates a Project
 * file for AquaCrop that can be used to run it for the multiple cropping
 * sequences defined.
 * 
 * @author Rob Knapen, Alterra Wageningen-UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
public class AquaCropProjectOutput extends BaseTranslatorOutput implements TranslatorOutput {

    private static final Logger LOG = LoggerFactory.getLogger(AquaCropProjectOutput.class);
	
	// TODO: AquaCrop model installation path? - Environment variable?
	
	// TODO
	// Variable for file path
	// Field management event "planting", will get aquacrop_cul_id field, eg Sorghum_320.cro
	// otherwise use cul_id and if unknown cultivar do crash (and log)
	// Use aquacrop_cul field to select proper AquaCrop crop file
	// Get cropping season length (in calendar days) from filename (encoded)
	// Use cropping season length to set harvesting data (in AquaCrop project file)
	
	// Data can have multi experiments (experiments bucket) or only a single experiment (no experiments bucket)
	// {experiment[].}management.events
	// management.events[] can contain multiple planting events 
    
    // TODO: Process all soils
    // TODO: Process all weathers
    // TODO: Select soil for each experiment based on specified soil id
    
	
	@SuppressWarnings("rawtypes")
	public void writeFile(String file, Map data) throws IOException {
		
		LOG.debug("Creating input files for AquaCrop model from AgMIP data ...");
		
		Velocity.init();
		VelocityContext vc = new VelocityContext();

		// extract the soil data needed
		LOG.debug("Reading soils");
		Soils soils = Soils.create(data);
		assert(soils.getItems().size() > 0);
		for (Soil soil : soils.getItems()) {
			LOG.debug(soil.toString());
		}
		LOG.debug("Number of soil records read: " + soils.getItems().size());
		
		// extract the weather data needed
		LOG.debug("Reading weathers");
		Weathers weathers = Weathers.create(data);
		assert(weathers.getItems().size() > 0);
		for (Weather weather : weathers.getItems()) {
			LOG.debug(weather.toString());
		}
		LOG.debug("Number of weather records read: " + weathers.getItems().size());
		
		// extract the experiment data needed
		Experiments experiments = Experiments.create(data);
		assert(experiments.getItems().size() > 0);
		
		/// process the experiments
		for (Experiment experiment : experiments.getItems()) {
			LOG.debug(experiment.toString());
			List<PlantingEvent> events = experiment.getPlantingEvents();
			for (PlantingEvent event : events) {
				LOG.debug(event.toString());
			}
		}
		
		// TODO
		/*
				Soil soil = Soil.create(data);
				assert(soil.getHorizons().size() > 0);
				
				vc.put("format", new AquaCropFormatter());
				vc.put("aquacrop_version", AQUACROP_VERSION);
				vc.put("soil", soil);
				
				Template t = Velocity.getTemplate("src/main/resources/aquacrop_soil_sol.vm", "UTF-8");
				int pos = file.lastIndexOf(".");
				String outFile = file.substring(0, pos) + ".sol";
				writeFile(vc, t, outFile);
		 */
		
		LOG.debug("Done");
	}

}
