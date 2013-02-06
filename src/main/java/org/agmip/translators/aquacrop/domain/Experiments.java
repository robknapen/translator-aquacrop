package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agmip.translators.aquacrop.tools.AgMIP;
import org.agmip.util.MapUtil;

/**
 * Data for a series of AgMIP crop experiments.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
@SuppressWarnings("rawtypes")
public class Experiments {

	private List<Experiment> items = new ArrayList<Experiment>();
	
	
	public static Experiments create(Map data) {
		Experiments instance = new Experiments();
		instance.from(data);
		return instance;
	}

	
	@SuppressWarnings("unchecked")
	public void from(Map data) {
		// get the bucket of relevant data
		ArrayList<HashMap<String, Object>> dataItems = MapUtil.getRawPackageContents(data, AgMIP.EXPERIMENTS_BUCKET_NAME);
        assert(dataItems != null);

        items.clear();
        for (HashMap<String, Object> dataItem : dataItems) {
        	// create a new item
        	Experiment experiment = Experiment.create(dataItem);
        	// store it
        	items.add(experiment);
        }
	}
	
	
	public List<Experiment> getItems() {
		return items;
	}
}
