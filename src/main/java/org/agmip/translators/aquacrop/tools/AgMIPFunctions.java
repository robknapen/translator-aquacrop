package org.agmip.translators.aquacrop.tools;

import java.io.IOException;

import org.agmip.ace.AceRecord;

/**
 * Generic support functions for the mapping of AgMIP to AquaCrop data.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
public class AgMIPFunctions {

	// AgMIP data bucket names
	public final static String EXPERIMENTS_BUCKET_NAME = "experiments";
	public final static String WEATHERS_BUCKET_NAME = "weathers";
	public final static String SOILS_BUCKET_NAME = "soils";
	public final static String MANAGEMENT_BUCKET_NAME = "management";

	// AgMIP nested array data names
	public final static String MANAGEMENT_EVENTS_LIST_NAME = "events";
	public final static String SOILS_LAYERS_LIST_NAME = "soilLayer";
	public final static String WEATHERS_DAILY_LIST_NAME = "dailyWeather";

	// AgMIP management event names
	public final static String MANAGEMENT_EVENT_PLANT = "planting";
	public final static String MANAGEMENT_EVENT_IRRIGATE = "irrigation";
	public final static String MANAGEMENT_EVENT_FERTILIZE = "fertilizer";
	public final static String MANAGEMENT_EVENT_ORGANIC = "organic_matter";
	public final static String MANAGEMENT_EVENT_CHEMICAL = "chemical";
	public final static String MANAGEMENT_EVENT_HARVEST = "harvest";
	public final static String MANAGEMENT_EVENT_TILLAGE = "tillage";
	
	
	/**
	 * Check if the data contains a values for at least one of the specified
	 * keys.
	 * 
	 * @param data to examine
	 * @param keys to test
	 * @return true if a value is available for at least one of the keys
	 * @throws IOException 
	 */
	public static boolean hasValueFor(AceRecord data, String... keys) throws IOException {
		for (String key : keys) {
			if (!"".equals(data.getValueOr(key, ""))) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Returns the value from the data for the first of the specified keys
	 * for which a value is available, or null when none is available.
	 * 
	 * @param data to examine
	 * @param keys to search
	 * @return value for first available key, or null
	 * @throws IOException 
	 */
	public static String getValueOrNullFor(AceRecord data, String... keys) throws IOException {
		return getValueFor(data, null, keys);
	}
	
	
	/**
	 * Returns the value from the data for the first of the specified keys
	 * for which a value is available, or the specified orValue.
	 * 
	 * @param data to examine
	 * @param orValue to return when no key is available
	 * @param keys to search
	 * @return value for first available key, or the orValue
	 * @throws IOException 
	 */
	public static String getValueFor(AceRecord data, String orValue, String... keys) throws IOException {
		String val;
		for (String key : keys) {
			val = data.getValueOr(key, "");
			if (!"".equals(val)) {
				return val;
			}
		}
		return orValue;
	}

	
	/**
	 * Returns the list of maps from the data for the specified key, or an
	 * empty list when the key is not present in the data.
	 * 
	 * @param data to examine
	 * @param key to search
	 * @return value for the key, or an empty list
	 */
//	public static ArrayList<HashMap<String, Object>> getListOrEmptyFor(Map data, String key) {
//	    return (ArrayList<HashMap<String, Object>>) MapUtil.getObjectOr(data, key, new ArrayList<HashMap<String, Object>>());        
//	}
	
	
}
