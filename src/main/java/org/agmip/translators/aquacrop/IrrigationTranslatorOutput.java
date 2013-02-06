package org.agmip.translators.aquacrop;

import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.aquacrop.domain.Experiment;
import org.agmip.translators.aquacrop.domain.Experiments;
import org.agmip.translators.aquacrop.domain.Irrigation;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translates AgMIP irrigation data into input for the AquaCrop model.
 * 
 * This translator takes the AgMIP experiments data and creates irrigation
 * input files for the AquaCrop model. The experiment id will be added to
 * the file name for cross referencing.
 * 
 * @author Rob Knapen, Alterra Wageningen-UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
public class IrrigationTranslatorOutput extends BaseTranslatorOutput implements TranslatorOutput {

	// logger for this translator
    private static final Logger LOG = LoggerFactory.getLogger(IrrigationTranslatorOutput.class);
	
	@SuppressWarnings("rawtypes")
	public void writeFile(String file, Map data) {

		LOG.debug("Creating irrigation input files for AquaCrop model from AgMIP data ...");
		Velocity.init();
		
		// extract the experiment data needed
		Experiments experiments = Experiments.create(data);
		assert(experiments.getItems().size() > 0);
		
		/// process the irrigation management for each experiment
		for (Experiment experiment : experiments.getItems()) {
			Irrigation irrigation = experiment.getIrrigation();
			LOG.debug(irrigation.toString());

			VelocityContext vc = new VelocityContext();
			vc.put("format", new AquaCropFormatter());
			vc.put("aquacrop_version", AQUACROP_VERSION);
			vc.put("irrigation", irrigation);
			Template t = Velocity.getTemplate("src/main/resources/aquacrop_irrigation_irr.vm", "UTF-8");
			int pos = file.lastIndexOf(".");
			String outFile = file.substring(0, pos) + "_" + experiment.getId() + ".irr";
			writeFile(vc, t, outFile);
		}
		
		LOG.debug("Done");
	}

}
