package org.agmip.translators.aquacrop.tools;

import java.util.Map;

import org.agmip.util.MapUtil;

@SuppressWarnings({"rawtypes", "unchecked"}) 
public class MapHelper {
	
	public static boolean hasValueForKey(Map data, String key) {
		return (!"".equals(MapUtil.getValueOr(data, key, "")));
	}
	
	
	public static String getValueForFirstAvailableKey(Map data, String[] keys, String orValue) {
		String val;
		for (String key : keys) {
			val = MapUtil.getValueOr(data, key, "");
			if (!"".equals(val)) {
				return val;
			}
		}
		return orValue;
	}

}
