package org.agmip.translators.aquacrop;

import java.io.File;
import java.io.IOException;

import org.agmip.ace.AceDataset;
import org.agmip.ace.AceSoil;
import org.agmip.ace.IAceBaseComponent;
import org.agmip.translators.aquacrop.domain.Soil;
import org.agmip.translators.aquacrop.domain.Soils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoilInputCreator extends FileCreator {

    private static final Logger LOG = LoggerFactory.getLogger(SoilInputCreator.class);

    
    public void writeFiles(File outputDirectory, String fileNamePrefix, AceDataset data) throws IOException {
		Soils soils = Soils.create(data);
		if (soils.getItems().size() > 0) {
			writeFiles(outputDirectory, fileNamePrefix, soils);
		} else {
			LOG.warn("No soil data to process in AceDataset");
		}
    }
    
    
    public void writeFiles(File outputDirectory, String fileNamePrefix, IAceBaseComponent data) throws IOException {
    	if (data instanceof AceSoil) {
    		writeFiles(outputDirectory, fileNamePrefix, Soil.create((AceSoil)data));
    	} else {
    		LOG.error("Can only create soil files from AceDataset or AceWeather instances");
    	}
    }

    
    public void writeFiles(File outputDirectory, String fileNamePrefix, Soils data) {
		for (Soil soil : data.getItems()) {
			writeFiles(outputDirectory, fileNamePrefix, soil);
		}
    }
    
    
    private void writeFiles(File outputDirectory, String fileNamePrefix, Soil data) {
		LOG.debug("Processing " + data);

		VelocityContext vc = new VelocityContext();
		vc.put("format", new AquaCropFormatter());
		vc.put("aquacrop_version", AQUACROP_VERSION);
		vc.put("soil", data);

		String basePath = outputDirectory.getAbsolutePath();
		
		Template t = Velocity.getTemplate("src/main/resources/aquacrop_soil_sol.vm", "UTF-8");
		String outFile = basePath + fileNamePrefix + "_" + data.getId() + ".sol";
		writeFile(vc, t, outFile);
    }
	
}
