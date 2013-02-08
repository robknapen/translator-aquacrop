package org.agmip.translators.aquacrop;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.agmip.util.JSONAdapter;

/**
 * Unit test for simple App.
 */
public class TranslationTest extends TestCase {

	@SuppressWarnings("rawtypes")
	private Map inputMap;

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public TranslationTest(String testName) {
		super(testName);

		try {
			File f = new File("src/test/resources/json-translation-samples/fields_short.json");
			List<String> lines = Files.readAllLines(f.toPath(), Charset.forName("UTF-8"));

			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				sb.append(line);
			}
			inputMap = JSONAdapter.fromJSON(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	
	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TranslationTest.class);
	}

	
	/**
	 * Test the translation of AgMIP experiments to an AquaCrop project 
	 */
	public void testProjectTranslation() {
		AquaCropProjectOutput tx = new AquaCropProjectOutput();
		try {
			File tempFile = File.createTempFile("agmip_aquacrop", ".prj");
			tx.writeFile(tempFile.getAbsolutePath(), inputMap);
			
			// TODO: Add real tests!!!
			
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Test the translation of weather data
	 */
	public void testWeatherTranslation() {
		ClimateTranslatorOutput t = new ClimateTranslatorOutput();

		try {
			File tempFile = File.createTempFile("tmp_agmip_aquacrop_climate", ".cli");
			t.writeFile(tempFile.getAbsolutePath(), inputMap);
			
			// TODO: Add real tests!!!
			
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	/**
	 * Test the translation of soil data
	 */
	public void testSoilTranslation() {
		SoilTranslatorOutput t = new SoilTranslatorOutput();

		try {
			File tempFile = File.createTempFile("tmp_agmip_aquacrop_soil", ".sol");
			t.writeFile(tempFile.getAbsolutePath(), inputMap);
			
			// TODO: Add real tests!!!
			
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	
	/**
	 * Test the translation of irrigation data
	 */
	public void testIrrigationTranslation() {
		IrrigationTranslatorOutput t = new IrrigationTranslatorOutput();

		try {
			File tempFile = File.createTempFile("tmp_agmip_aquacrop_irrigation", ".irr");
			t.writeFile(tempFile.getAbsolutePath(), inputMap);
			
			// TODO: Add real tests!!!
			
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	
	/**
	 * Test the translation of field management data
	 */
	public void testFieldManagementTranslation() {
		FieldManagementTranslatorOutput t = new FieldManagementTranslatorOutput();

		try {
			File tempFile = File.createTempFile("tmp_agmip_aquacrop_management", ".man");
			t.writeFile(tempFile.getAbsolutePath(), inputMap);
			
			// TODO: Add real tests!!!
			
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
}	
