package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.AgMIP;
import org.agmip.util.MapUtil;

/**
 * Data for a collection of AgMIP weathers, to be used for model input.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
@SuppressWarnings("rawtypes")
public class Weathers {

	private List<Weather> items = new ArrayList<Weather>();
	
	
	public static Weathers create(Map data) {
		Weathers instance = new Weathers();
		instance.from(data);
		return instance;
	}
	
	
	@SuppressWarnings("unchecked")
	public void from(Map data) {
		ArrayList<HashMap<String, Object>> dataItems = MapUtil.getRawPackageContents(data, AgMIP.WEATHERS_BUCKET_NAME);
        assert(dataItems != null);

        items.clear();
        for (HashMap<String, Object> dataItem : dataItems) {
        	Weather weather = Weather.create(dataItem);
        	items.add(weather);
        }
	}


	public List<Weather> getItems() {
		return items;
	}
	
}
