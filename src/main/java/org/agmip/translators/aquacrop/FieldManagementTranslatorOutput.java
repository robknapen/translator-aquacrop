package org.agmip.translators.aquacrop;

import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.aquacrop.domain.Experiment;
import org.agmip.translators.aquacrop.domain.Experiments;
import org.agmip.translators.aquacrop.domain.FieldManagement;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translates AgMIP field management data into input for the AquaCrop model.
 * 
 * This translator takes the AgMIP experiments data and creates field
 * management input files for the AquaCrop model. The experiment id will
 * be added to the file name for cross referencing.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
@SuppressWarnings("rawtypes")
public class FieldManagementTranslatorOutput extends BaseTranslatorOutput implements TranslatorOutput {

	// logger for this translator
    private static final Logger LOG = LoggerFactory.getLogger(FieldManagementTranslatorOutput.class);
	
	public void writeFile(String file, Map data) {

		LOG.debug("Creating field management input files for AquaCrop model from AgMIP data ...");
		Velocity.init();
		
		// extract the experiment data needed
		Experiments experiments = Experiments.create(data);
		assert(experiments.getItems().size() > 0);
		
		/// process the irrigation management for each experiment
		for (Experiment experiment : experiments.getItems()) {
			FieldManagement management = experiment.getFieldManagement();
			if (management == null) {
				LOG.error("No usable input could be generated from experiment: " + experiment);
				continue;
			}

			// produce field management input file
			LOG.debug(management.toString());
			VelocityContext vc = new VelocityContext();
			vc.put("format", new AquaCropFormatter());
			vc.put("aquacrop_version", AQUACROP_VERSION);
			vc.put("management", management);
			Template t = Velocity.getTemplate("src/main/resources/aquacrop_management_man.vm", "UTF-8");
			int pos = file.lastIndexOf(".");
			String outFile = file.substring(0, pos) + "_" + experiment.getId() + ".man";
			writeFile(vc, t, outFile);
		}
		
		LOG.debug("Done");
	}

}
