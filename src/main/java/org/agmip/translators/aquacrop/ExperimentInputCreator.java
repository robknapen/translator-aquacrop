package org.agmip.translators.aquacrop;
import java.io.File;
import java.io.IOException;

import org.agmip.ace.AceDataset;
import org.agmip.translators.aquacrop.domain.Experiment;
import org.agmip.translators.aquacrop.domain.Experiments;
import org.agmip.translators.aquacrop.domain.FieldManagement;
import org.agmip.translators.aquacrop.domain.Irrigation;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Translates AgMIP experiments data into input for the AquaCrop model.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
public class ExperimentInputCreator extends FileCreator {

	// logger for this translator
    private static final Logger LOG = LoggerFactory.getLogger(ExperimentInputCreator.class);
	
    
    public void writeFiles(File outputDirectory, String fileNamePrefix, AceDataset data) throws IOException {
		Experiments experiments = Experiments.create(data);
		if (experiments.getItems().size() > 0) {
			writeFiles(outputDirectory, fileNamePrefix, experiments);
		} else {
			LOG.warn("No experiment data to process in AceDataset");
		}
    }
    
    
    public void writeFiles(File outputDirectory, String fileNamePrefix, Experiments data) throws IOException {
    	writeIrrigationFiles(outputDirectory, fileNamePrefix, data);
    	writeFieldManagementFiles(outputDirectory, fileNamePrefix, data);
    	writeProjectFiles(outputDirectory, fileNamePrefix, data);
    }
	
	
	private void writeIrrigationFiles(File outputDirectory, String fileNamePrefix, Experiments data) {
		String basePath = outputDirectory.getAbsolutePath();
		
		for (Experiment experiment : data.getItems()) {
			Irrigation irrigation = experiment.getIrrigation();
			
			if (irrigation == null) {
				LOG.error("No usable input could be generated from experiment: " + experiment);
				continue;
			}
			if (irrigation.getItems().size() == 0) {
				LOG.info("No irrigation used in experiment: " + experiment);
				continue;
			}

			LOG.debug("processing " + irrigation);
			
			VelocityContext vc = new VelocityContext();
			vc.put("format", new AquaCropFormatter());
			vc.put("aquacrop_version", AQUACROP_VERSION);
			vc.put("irrigation", irrigation);
			Template t = Velocity.getTemplate("src/main/resources/aquacrop_irrigation_irr.vm", "UTF-8");
			String outFile = basePath + fileNamePrefix + "_" + experiment.getId() + ".irr";
			writeFile(vc, t, outFile);
		}
	}
	
	
	private void writeFieldManagementFiles(File outputDirectory, String fileNamePrefix, Experiments data) {
		String basePath = outputDirectory.getAbsolutePath();
		
		for (Experiment experiment : data.getItems()) {
			FieldManagement management = experiment.getFieldManagement();
			
			if (management == null) {
				LOG.error("No usable input could be generated from experiment: " + experiment);
				continue;
			}

			LOG.debug("Processing " + management);
			
			VelocityContext vc = new VelocityContext();
			vc.put("format", new AquaCropFormatter());
			vc.put("aquacrop_version", AQUACROP_VERSION);
			vc.put("management", management);
			Template t = Velocity.getTemplate("src/main/resources/aquacrop_management_man.vm", "UTF-8");
			String outFile = basePath + fileNamePrefix + "_" + experiment.getId() + ".man";
			writeFile(vc, t, outFile);
		}
	}

	
	private void writeProjectFiles(File outputDirectory, String fileNamePrefix, Experiments data) {
		String basePath = outputDirectory.getAbsolutePath();
		
		// TODO: get AquaCrop specific DOME variables from Experiments root JSON object
		// TODO: Use AceLookup functions for e.g. irrigation types
		
		// TODO: create project files -> needs to reference soil and climate files
			// isolate file name creation rules (based on soil and weather station ids)
		
		// See Project and SimulationRun classes
	}
}
