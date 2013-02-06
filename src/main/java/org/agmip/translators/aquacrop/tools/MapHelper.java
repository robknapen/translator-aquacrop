package org.agmip.translators.aquacrop.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.agmip.util.MapUtil;

@SuppressWarnings({"rawtypes", "unchecked"}) 
public class MapHelper {
	
	/**
	 * Check if the data contains a values for at least one of the specified
	 * keys.
	 * 
	 * @param data to examine
	 * @param keys to test
	 * @return true if a value is available for at least one of the keys
	 */
	public static boolean hasValueFor(Map data, String... keys) {
		for (String key : keys) {
			if (!"".equals(MapUtil.getValueOr(data, key, ""))) {
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
	 */
	public static String getValueOrNullFor(Map data, String... keys) {
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
	 */
	public static String getValueFor(Map data, String orValue, String... keys) {
		String val;
		for (String key : keys) {
			val = MapUtil.getValueOr(data, key, "");
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
	public static ArrayList<HashMap<String, Object>> getListOrEmptyFor(Map data, String key) {
	    return (ArrayList<HashMap<String, Object>>) MapUtil.getObjectOr(data, key, new ArrayList<HashMap<String, Object>>());        
	}
	
	
}
