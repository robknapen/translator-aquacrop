package org.agmip.translators.aquacrop;

import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.aquacrop.domain.Soil;
import org.agmip.translators.aquacrop.domain.Soils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translates AgMIP soils data into input for the AquaCrop model.
 * 
 * This translator takes the AgMIP soils data and creates a soil file for
 * the AquaCrop model.
 * 
 * @author Rob Knapen, Alterra Wageningen-UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
public class SoilTranslatorOutput extends BaseTranslatorOutput implements TranslatorOutput {

	// logger for this translator
    private static final Logger LOG = LoggerFactory.getLogger(SoilTranslatorOutput.class);
	
	@SuppressWarnings("rawtypes")
	public void writeFile(String file, Map data) {
		
		LOG.debug("Creating soils input files for AquaCrop model from AgMIP data ...");
		Velocity.init();

		// extract all soil data
		Soils soils = Soils.create(data);
		
		for (Soil soil : soils.getItems()) {
			LOG.debug("Processing " + soil);
			
			VelocityContext vc = new VelocityContext();
			vc.put("format", new AquaCropFormatter());
			vc.put("aquacrop_version", AQUACROP_VERSION);
			vc.put("soil", soil);
		
			Template t = Velocity.getTemplate("src/main/resources/aquacrop_soil_sol.vm", "UTF-8");
			int pos = file.lastIndexOf(".");
			String outFile = file.substring(0, pos) + "_" + soil.getId() + ".sol";
			writeFile(vc, t, outFile);
		}
		
		LOG.debug("Done");
	}

}
