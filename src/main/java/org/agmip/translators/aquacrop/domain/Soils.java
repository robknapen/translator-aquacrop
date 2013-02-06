package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.AgMIP;
import org.agmip.util.MapUtil;

/**
 * Data for a collection of AgMIP soils, to be used for model input.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
@SuppressWarnings("rawtypes")
public class Soils {

	private List<Soil> items = new ArrayList<Soil>();
	
	public static Soils create(Map data) {
		Soils instance = new Soils();
		instance.from(data);
		return instance;
	}

	
	@SuppressWarnings("unchecked")
	private void from(Map data) {
		// get the bucket of relevant data
		ArrayList<HashMap<String, Object>> dataItems = MapUtil.getRawPackageContents(data, AgMIP.SOILS_BUCKET_NAME);
        assert(dataItems != null);

        items.clear();
        for (HashMap<String, Object> dataItem : dataItems) {
        	Soil soil = Soil.create(dataItem);
        	items.add(soil);
        }
	}


	public List<Soil> getItems() {
		return items;
	}

}
