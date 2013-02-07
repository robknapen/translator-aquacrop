package org.agmip.translators.aquacrop;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for the AquaCrop AgMIP data output translators. These create
 * model input data from the data extracted from the AgMIP database.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
public class BaseTranslatorOutput {

	// logger for this translator
    private static final Logger LOG = LoggerFactory.getLogger(BaseTranslatorOutput.class);
	
	public final static String AQUACROP_VERSION = "4.0";
	

	/**
	 * Formatting methods for used with Velocity.
	 */
	public class AquaCropFormatter {
		public String headerInt(int val) {
			return String.format("%7s", String.valueOf(val));
		}
		
		public String num(int val, int size) {
			String frm = String.format("%%%d", size);
			return String.format(frm, val);
		}
		
		public String dbl(double val, int size, int decimals) {
			String frm = String.format("%%%d.%df", size, decimals);
			return String.format(frm, val);
		}
	}

	
	protected void writeFile(VelocityContext context, Template template, String file) {
		try {
            FileOutputStream fstream = new FileOutputStream(file);
            DataOutputStream out = new DataOutputStream(fstream);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, Charset.forName("UTF-8")));
    		template.merge(context, bw);
            bw.flush();
            bw.close();
			LOG.info("Created file: " + file);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Could not create file, error: " + e.getMessage());
		}
	}
}
