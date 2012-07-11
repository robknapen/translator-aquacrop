package org.agmip.translators.aquacrop;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.util.MapUtil;
import org.agmip.util.MapUtil.BucketEntry;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.DisplayTool;
import org.apache.velocity.tools.generic.NumberTool;

public class ClimateTranslatorOutput implements TranslatorOutput {

	public void writeFile(String file, Map data) {

		// get the block of weather data
        List<BucketEntry> weatherData = MapUtil.getBucket(data, "weather");
        assert(weatherData.size() == 1);
        
        try {
        	// create the output
            FileOutputStream fstream = new FileOutputStream(file);
            DataOutputStream out = new DataOutputStream(fstream);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, Charset.forName("UTF-8")));

            // get the global weather station data
        	Map<String, String> globalWeatherData = weatherData.get(0).getValues();
            String wsName = MapUtil.getValueOr(globalWeatherData, "wst_name", "<name of weather station>");
            
            // get the daily weather station data
            List<LinkedHashMap<String, String>> dailyWeatherData = weatherData.get(0).getDataList();
            assert(dailyWeatherData.size() > 0);
            
            String date = (String) MapUtil.getValueOr(dailyWeatherData.get(0), "w_date", "19010101");
            String year = String.copyValueOf(date.toCharArray(), 0, 4);
            String month = String.copyValueOf(date.toCharArray(), 4, 2);
            String day = String.copyValueOf(date.toCharArray(), 6, 2);

            bw.write(wsName + "\n");
            bw.write("      1 : Daily records (1=daily, 2=10-daily and 3=monthly\n");
            bw.write(String.format("%7s : First day of record (1, 11 or 21 for 10-day or 1 for months\n", day));
            bw.write(String.format("%7s : First month of record" + "\n", month));
            bw.write(String.format("%7s : First year of record (1901 if not linked to a specific year)\n\n", year));

            bw.write("  TMin (C)   TMax (C)" + "\n");
            bw.write("========================" + "\n");

            for (Map<String, String> dailyData : dailyWeatherData) {
                String tmin = MapUtil.getValueOr(dailyData, "tmin", "");
                String tmax = MapUtil.getValueOr(dailyData, "tmax", "");
                bw.write(String.format("%10s%10s\n", tmin, tmax));
            }
            
            bw.flush();
            bw.close();
            System.out.println("Created file: " + file);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}

	
	public void velocitySample() {
		Velocity.init();
		VelocityContext vc = new VelocityContext();

		vc.put("format", new AquaCropFormatter());
		
		vc.put("wst_name", new String("Weather Station Name"));
		vc.put("record_type", 1);
		vc.put("first_day_of_record", 1);
		vc.put("first_month_of_record", 1);
		vc.put("first_year_of_record", 1901);
		
		Template template = null;
		try {
			template = Velocity.getTemplate("src/main/resources/aquacrop_climate_tmp_file_template.vsl", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not load Velocity template: " + e.getMessage());
		}
		
		StringWriter sw = new StringWriter();
		template.merge(vc, sw);
		
		System.out.println(sw.toString());
	}
	
	
	public class AquaCropFormatter {
		public String headerInt(int val) {
			return String.format("%7s", String.valueOf(val));
		}
	}
}
