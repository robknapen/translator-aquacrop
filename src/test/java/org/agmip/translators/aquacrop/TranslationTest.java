package org.agmip.translators.aquacrop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import junit.framework.AssertionFailedError;

import org.agmip.ace.AceBaseComponentType;
import org.agmip.ace.AceComponentType;
import org.agmip.ace.AceDataset;
import org.agmip.ace.AceEvent;
import org.agmip.ace.AceEventCollection;
import org.agmip.ace.AceEventType;
import org.agmip.ace.AceExperiment;
import org.agmip.ace.io.AceParser;
import org.agmip.ace.util.AceFunctions;
import org.agmip.common.functions.BaseFunctions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */

public class TranslationTest {

    private static final Logger LOG = LoggerFactory.getLogger(TranslationTest.class);
	AceDataset dataset;
    
    @Before
    public void setup() throws IOException {
//    	String testDataFileName = "src/test/resources/json-translation-samples/mach_fast.json";
//    	dataset = AceParser.parse(new FileInputStream(testDataFileName));

    	String testDataFileName = "src/test/resources/json-translation-samples/mach_baseline.aceb";
    	dataset = AceParser.parseACEB(new FileInputStream(testDataFileName));
    }

    
    @Test
	public void simplePdateTest() throws IOException {
		for(AceExperiment e: dataset.getExperiments()) {
			LOG.info("PDATE is {}", AceFunctions.deepGetValue("pdate", e));
		}
	}
    
    
    @Test
    public void fertTest() throws IOException {
    	for(AceExperiment e: dataset.getExperiments()) {
    		String fenTotal = e.getValue("fen_tot");
    		AceEventCollection ec = e.getEvents().filterByEvent(AceEventType.ACE_FERTILIZER_EVENT);
    		String fenTest = "0";
    		for(AceEvent event: ec) {
    			fenTest = BaseFunctions.sum(fenTest, event.getValueOr("feamn", "0"));
    		}
    		// Add them together
			LOG.info("Fen Total: {}, {}", fenTotal, fenTest);
    	}
    }

    
	/**
	 * Test the translation of AgMIP experiments to an AquaCrop project 
	 */
    @Test
	public void weatherTranslationTest() {
		AquaCropTranslatorOutput tx = new AquaCropTranslatorOutput();
		try {
			File tempFile = File.createTempFile("tmp_agmip_aquacrop_climate", ".cli");
			tx.write(tempFile, dataset, AceBaseComponentType.ACE_WEATHER);
			// TODO: Add real tests!!!
			Assert.fail("Test not implemented yet");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

    
	/**
	 * Test the translation of AgMIP experiments to an AquaCrop project 
	 */
    @Test
	public void soilTranslationTest() {
		AquaCropTranslatorOutput tx = new AquaCropTranslatorOutput();
		try {
			File tempFile = File.createTempFile("tmp_agmip_aquacrop_soil", ".sol");
			tx.write(tempFile, dataset, AceBaseComponentType.ACE_SOIL);
			// TODO: Add real tests!!!
			Assert.fail("Test not implemented yet");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
    
	
	/**
	 * Test the translation of AgMIP experiments to an AquaCrop project 
	 */
    @Test
	public void projectTranslationTest() {
		AquaCropTranslatorOutput tx = new AquaCropTranslatorOutput();
		try {
			File tempFile = File.createTempFile("agmip_aquacrop", ".prj");
			tx.write(tempFile, dataset, AceBaseComponentType.ACE_EXPERIMENT);
			// TODO: Add real tests!!!
			Assert.fail("Test not implemented yet");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

    
//	/**
//	 * Test the translation of irrigation data
//	 */
//	public void testIrrigationTranslation() {
//		IrrigationTranslatorOutput t = new IrrigationTranslatorOutput();
//
//		try {
//			File tempFile = File.createTempFile("tmp_agmip_aquacrop_irrigation", ".irr");
//			t.writeFile(tempFile.getAbsolutePath(), inputMap);
//			
//			// TODO: Add real tests!!!
//			
//			assertTrue(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}
//	
//
//	@Ignore
//	@Test
//	public void testSoilLayerReducer() {
//		
//		// see: https://github.com/jcufi/translator-stics/blob/master/src/main/java/org/agmip/translators/stics/SoilAndInitOutput.java
//		
//		BucketEntry iniBucket;
//		BucketEntry soilBucket;
//		LayerReducer soilAgg;
//		HashMap<String, String> firstLevelSoilData;
//		ArrayList<HashMap<String, String>> nestedSoilData;
//		HashMap<String, String> firstLevelInitData;
//		ArrayList<HashMap<String, String>> nestedInitData;
//		ArrayList<HashMap<String, String>> aggregatedSoilData;
//		
//		HashMap<String, String> soilAdded = new HashMap<>();		
//		
//		soilAgg = new LayerReducer(new SAReducerDecorator());
//
////		try {
//			iniBucket = MapUtil.getBucket(inputMap, "initial_conditions");
//			ArrayList<BucketEntry> soilsList = MapUtil.getPackageContents(inputMap, AgMIPFunctions.SOILS_BUCKET_NAME);
//			
//			soilBucket = soilsList.get(0);
//			
//			// Soil and initialization structure
//			firstLevelSoilData = new HashMap<String, String>(soilBucket.getValues());
//			firstLevelInitData = soilBucket.getValues();
//			nestedSoilData = new ArrayList<HashMap<String, String>>(soilBucket.getDataList());
//			nestedInitData = iniBucket.getDataList();
//			
//			// Put soil information in the same map
//			LayerReducerUtil.mergeSoilAndInitializationData(nestedSoilData, nestedInitData);
//			
//			// Merge soil layers
//			// 1 - compute layer thickness instead of layer depth
//			LayerReducerUtil.computeSoilLayerSize(nestedSoilData);
//			
//			// 2 - conversion
////			String soilId = firstLevelSoilData.get("soil_id");
////			convertInitValues(nestedSoilData);
////			if(!soilAdded.containsKey(soilId)){
////				convertSoil(nestedSoilData);
////			}
//			
//			// 3 - reduce soil layers
//			aggregatedSoilData = soilAgg.process(nestedSoilData);
//			
//			// 4 - Fill to 5 layers
////			SticsUtil.fill(aggregatedSoilData, new String[]{"ich2o", "icno3", "icnh4", "sllb", "sldul", "slbdm", "slll"}, 5);
//			
//			// Generate initialization file
////			String content = generateInitializationFile(firstLevelInitData, aggregatedSoilData);
////			String iniFileName = soilId + "_" + expId + "_ini" + ".xml";
////			initFile = newFile(content, filePath, iniFileName);
////			log.info("Generating initialization file : "+iniFileName);
////			if(!soilAdded.containsKey(soilId)){
////				// Generate soil file
////				log.info("Generating soil file for : "+soilId);
////				content = generateSoilFile(firstLevelSoilData, aggregatedSoilData);
////				soilBuffer.append(content);
////				soilAdded.put(soilId, soilId);
////			}
////		} catch (IOException e) {
////			LOG.error("Unable to generate soil file.");
////			LOG.error(e.toString());
////		}
//	}
//	
//	
////	public void convertInitValues(ArrayList<HashMap<String, String>> soilsData) {
////		for (HashMap<String, String> currentSoil : soilsData) {
////			String[] paramToConvert = new String[] { ICNO3, ICNH4 };
////			for (String param : paramToConvert) {
////				if (currentSoil.containsKey(param)) {
////					currentSoil.put(param, new Float(parseFloat(currentSoil.get(param)) * parseFloat(currentSoil.get(SLBDM)) * parseFloat(currentSoil.get(SLLB)) / 10f).toString());
////				}
////			}
////		}
////	}
////
////	
////	public void convertSoil(ArrayList<HashMap<String, String>> soilsData) {
////		for (HashMap<String, String> currentSoil : soilsData) {
////			// Convertion
////			String[] paramToConvert = { SLDUL, SLLL, ICH2O };
////			for (String param : paramToConvert) {
////				if (currentSoil.containsKey(param)) {
////					currentSoil.put(param, new Float(parseFloat(currentSoil.get(param)) / parseFloat(currentSoil.get(SLBDM)) * 100f).toString());
////				}
////			}
////		}
////	}
	
}	
