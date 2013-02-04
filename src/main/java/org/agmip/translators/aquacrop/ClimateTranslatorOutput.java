package org.agmip.translators.aquacrop;

import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.aquacrop.domain.Weather;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class ClimateTranslatorOutput extends BaseTranslatorOutput implements TranslatorOutput {

	private final static String CO2_FILE_NAME = "maunaloa.co2";
	
	public void writeFile(String file, Map data) {
		Velocity.init();
		VelocityContext vc = new VelocityContext();
		
		Weather weather = Weather.create(data);
		assert(weather.getDaily().size() > 0);
		
		vc.put("format", new AquaCropFormatter());
		vc.put("aquacrop_version", AQUACROP_VERSION);
		vc.put("weather", weather);
		
		Template t = Velocity.getTemplate("src/main/resources/aquacrop_climate_tmp.vm", "UTF-8");
		int pos = file.lastIndexOf(".");
		String temperatureFile = file.substring(0, pos) + ".tmp";
		writeFile(vc, t, temperatureFile);

		t = Velocity.getTemplate("src/main/resources/aquacrop_climate_et0.vm", "UTF-8");
		String et0File = file.substring(0, pos) + ".et0";
		writeFile(vc, t, et0File);

		t = Velocity.getTemplate("src/main/resources/aquacrop_climate_plu.vm", "UTF-8");
		String rainFile = file.substring(0, pos) + ".plu";
		writeFile(vc, t, rainFile);

		// write the overall climate file
		vc.put("temp_file", temperatureFile);
		vc.put("et0_file", et0File);
		vc.put("rain_file", rainFile);
		vc.put("co2_file", CO2_FILE_NAME);
		t = Velocity.getTemplate("src/main/resources/aquacrop_climate_cli.vm", "UTF-8");
		writeFile(vc, t, file.substring(0, pos) + ".cli");
	}
}
