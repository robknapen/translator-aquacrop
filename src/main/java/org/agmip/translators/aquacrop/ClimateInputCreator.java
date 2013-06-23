package org.agmip.translators.aquacrop;

import java.io.File;
import java.io.IOException;

import org.agmip.ace.AceDataset;
import org.agmip.ace.AceWeather;
import org.agmip.ace.IAceBaseComponent;
import org.agmip.translators.aquacrop.domain.Weather;
import org.agmip.translators.aquacrop.domain.Weathers;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClimateInputCreator extends FileCreator {

	private final static String CO2_FILE_NAME = "maunaloa.co2";
	
    private static final Logger LOG = LoggerFactory.getLogger(ClimateInputCreator.class);

    
    public void writeFiles(File outputDirectory, String fileNamePrefix, AceDataset data) throws IOException {
		Weathers weathers = Weathers.create(data);
		if (weathers.getItems().size() > 0) {
			writeFiles(outputDirectory, fileNamePrefix, weathers);
		} else {
			LOG.warn("No weather data to process in AceDataset");
		}
    }
    
    
    public void writeFiles(File outputDirectory, String fileNamePrefix, IAceBaseComponent data) throws IOException {
    	if (data instanceof AceWeather) {
    		writeFiles(outputDirectory, fileNamePrefix, Weather.create((AceWeather)data));
    	} else {
    		LOG.error("Can only create climate files from AceDataset or AceWeather instances");
    	}
    }

    
    public void writeFiles(File outputDirectory, String fileNamePrefix, Weathers data) {
		for (Weather weather : data.getItems()) {
			writeFiles(outputDirectory, fileNamePrefix, weather);
		}
    }
    
    
    private void writeFiles(File outputDirectory, String fileNamePrefix, Weather data) {
		LOG.debug("Processing " + data);

		VelocityContext vc = new VelocityContext();
		vc.put("format", new AquaCropFormatter());
		vc.put("aquacrop_version", AQUACROP_VERSION);
		vc.put("weather", data);
	
		String basePath = outputDirectory.getAbsolutePath();
		
		Template t = Velocity.getTemplate("src/main/resources/aquacrop_climate_tmp.vm", "UTF-8");
		String temperatureFile = basePath + fileNamePrefix + "_" + data.getWeatherStationId() + ".tmp";
		writeFile(vc, t, temperatureFile);

		t = Velocity.getTemplate("src/main/resources/aquacrop_climate_et0.vm", "UTF-8");
		String et0File = basePath + fileNamePrefix + "_" + data.getWeatherStationId() + ".et0";
		writeFile(vc, t, et0File);

		t = Velocity.getTemplate("src/main/resources/aquacrop_climate_plu.vm", "UTF-8");
		String rainFile = basePath + fileNamePrefix + "_" + data.getWeatherStationId() + ".plu";
		writeFile(vc, t, rainFile);

		// TODO: Generate custom CO2 file?
		
		// write the overall climate file
		vc.put("temp_file", temperatureFile);
		vc.put("et0_file", et0File);
		vc.put("rain_file", rainFile);
		vc.put("co2_file", CO2_FILE_NAME);
		
		t = Velocity.getTemplate("src/main/resources/aquacrop_climate_cli.vm", "UTF-8");
		writeFile(vc, t, basePath + fileNamePrefix + "_" + data.getWeatherStationId() + ".cli");
    }
}
