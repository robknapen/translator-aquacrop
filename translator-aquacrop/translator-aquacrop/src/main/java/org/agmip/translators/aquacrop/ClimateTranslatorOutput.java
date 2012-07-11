package org.agmip.translators.aquacrop;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.aquacrop.domain.Weather;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class ClimateTranslatorOutput implements TranslatorOutput {

	private final static String AQUACROP_VERSION = "4.0";
	private final static String CO2_FILE_NAME = "maunaloa.co2";
	
	/* A climate file (Tab. 1) contains in successive lines:
		−	a description (string of characters);
		−	the number of the AquaCrop Version (expressed as a decimal number);
		−	the name of the air temperature file (*.TMP);
		−	the name of the ETo file (*.ETo);
		−	the name of the rainfall file (*.PLU);
		−	the name of the CO2 file (*.CO2).
	 */
	
	public void writeFile(String file, Map data) {
		Velocity.init();
		VelocityContext vc = new VelocityContext();
		
		Weather weather = Weather.create(data);
		assert(weather.getDaily().size() > 0);
		
		vc.put("format", new AquaCropFormatter());
		vc.put("aquacrop_version", AQUACROP_VERSION);
		vc.put("weather", weather);
		
		Template t = Velocity.getTemplate("src/main/resources/aquacrop_climate_tmp_file_template.vsl", "UTF-8");
		int pos = file.lastIndexOf(".");
		String temperatureFile = file.substring(0, pos) + ".tmp";
		writeFile(vc, t, temperatureFile);

		t = Velocity.getTemplate("src/main/resources/aquacrop_climate_et0_file_template.vsl", "UTF-8");
		String et0File = file.substring(0, pos) + ".et0";
		writeFile(vc, t, et0File);

		t = Velocity.getTemplate("src/main/resources/aquacrop_climate_plu_file_template.vsl", "UTF-8");
		String rainFile = file.substring(0, pos) + ".plu";
		writeFile(vc, t, rainFile);

		// write the overall climate file
		vc.put("temp_file", temperatureFile);
		vc.put("et0_file", et0File);
		vc.put("rain_file", rainFile);
		vc.put("co2_file", CO2_FILE_NAME);
		t = Velocity.getTemplate("src/main/resources/aquacrop_climate_cli_file_template.vsl", "UTF-8");
		writeFile(vc, t, file.substring(0, pos) + ".cli");
	}
	
	
	private void writeFile(VelocityContext context, Template template, String file) {
		try {
            FileOutputStream fstream = new FileOutputStream(file);
            DataOutputStream out = new DataOutputStream(fstream);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, Charset.forName("UTF-8")));
    		template.merge(context, bw);
            bw.flush();
            bw.close();
            System.out.println("Created output file: " + file);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not create output file: " + e.getMessage());
		}
	}

	
	public class AquaCropFormatter {
		public String headerInt(int val) {
			return String.format("%7s", String.valueOf(val));
		}
		
		public String dbl(double val) {
			return String.format("%8.1f", val);
		}
	}
}
