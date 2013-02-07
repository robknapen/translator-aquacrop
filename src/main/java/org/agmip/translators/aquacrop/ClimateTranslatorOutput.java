package org.agmip.translators.aquacrop;

import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.aquacrop.domain.Weather;
import org.agmip.translators.aquacrop.domain.Weathers;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Translates AgMIP weather data into input for the AquaCrop model.
 * 
 * This translator takes the AgMIP weather data and creates climate files for
 * the AquaCrop model. The weather station ID value is added to the names of
 * the files created and should be used to related climate files to AgMIP
 * experiments.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
public class ClimateTranslatorOutput extends BaseTranslatorOutput implements TranslatorOutput {

	private final static String CO2_FILE_NAME = "maunaloa.co2";

	// logger for this translator
    private static final Logger LOG = LoggerFactory.getLogger(ClimateTranslatorOutput.class);
	
	@SuppressWarnings("rawtypes")
	public void writeFile(String file, Map data) {
		
		LOG.debug("Creating climate input files for AquaCrop model from AgMIP data ...");
		
		Velocity.init();
		
		// extract all weather data
		Weathers weathers = Weathers.create(data);
		for (Weather weather : weathers.getItems()) {
			LOG.debug("Processing " + weather);

			VelocityContext vc = new VelocityContext();
			vc.put("format", new AquaCropFormatter());
			vc.put("aquacrop_version", AQUACROP_VERSION);
			vc.put("weather", weather);
		
			Template t = Velocity.getTemplate("src/main/resources/aquacrop_climate_tmp.vm", "UTF-8");
			int pos = file.lastIndexOf(".");
			String temperatureFile = file.substring(0, pos) + "_" + weather.getWeatherStationId() + ".tmp";
			writeFile(vc, t, temperatureFile);

			t = Velocity.getTemplate("src/main/resources/aquacrop_climate_et0.vm", "UTF-8");
			String et0File = file.substring(0, pos) + "_" + weather.getWeatherStationId() + ".et0";
			writeFile(vc, t, et0File);
	
			t = Velocity.getTemplate("src/main/resources/aquacrop_climate_plu.vm", "UTF-8");
			String rainFile = file.substring(0, pos) + "_" + weather.getWeatherStationId() + ".plu";
			writeFile(vc, t, rainFile);

			// write the overall climate file
			vc.put("temp_file", temperatureFile);
			vc.put("et0_file", et0File);
			vc.put("rain_file", rainFile);
			vc.put("co2_file", CO2_FILE_NAME);
			t = Velocity.getTemplate("src/main/resources/aquacrop_climate_cli.vm", "UTF-8");
			writeFile(vc, t, file.substring(0, pos) + "_" + weather.getWeatherStationId() + ".cli");
		}

		LOG.debug("Done");
	}
}
