package org.agmip.translators.aquacrop;

import java.util.Map;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.translators.aquacrop.domain.Soil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class SoilTranslatorOutput extends BaseTranslatorOutput implements TranslatorOutput {

	public void writeFile(String file, Map data) {
		Velocity.init();
		VelocityContext vc = new VelocityContext();
		
		Soil soil = Soil.create(data);
		assert(soil.getLayers().size() > 0);
		
		vc.put("format", new AquaCropFormatter());
		vc.put("aquacrop_version", AQUACROP_VERSION);
		vc.put("soil", soil);
		
		Template t = Velocity.getTemplate("src/main/resources/aquacrop_soil_sol.vm", "UTF-8");
		int pos = file.lastIndexOf(".");
		String outFile = file.substring(0, pos) + ".sol";
		writeFile(vc, t, outFile);
	}

}
