package org.agmip.translators.aquacrop;

import java.io.IOException;
import java.util.Map;

import org.agmip.core.types.TranslatorOutput;

/**
 * Translates AgMIP field management data into input for the AquaCrop model.
 * 
 * This translator takes the AgMIP experiments data and creates field
 * management input files for the AquaCrop model. The experiment id will
 * be added to the file name for cross referencing.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 * @author Dirk Raes, Leuven University, Belgium
 */
@SuppressWarnings("rawtypes")
public class FieldManagementTranslatorOutput  extends BaseTranslatorOutput implements TranslatorOutput {

	public void writeFile(String outputDirectory, Map data) throws IOException {

		// TODO

	}

}
