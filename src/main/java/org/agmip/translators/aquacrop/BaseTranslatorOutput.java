package org.agmip.translators.aquacrop;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Base class for the AquaCrop AgMIP data output translators. These create
 * model input data from the data extracted from the AgMIP database.
 * 
 * @author Rob Knapen, Alterra Wageningen-UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
public class BaseTranslatorOutput {

	public final static String AQUACROP_VERSION = "4.0";
	
	
	public class AquaCropFormatter {
		public String headerInt(int val) {
			return String.format("%7s", String.valueOf(val));
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
            System.out.println("Created output file: " + file);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not create output file: " + e.getMessage());
		}
	}
}
