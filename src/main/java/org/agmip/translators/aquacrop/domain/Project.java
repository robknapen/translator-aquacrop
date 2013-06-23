package org.agmip.translators.aquacrop.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Project {
	
	private ArrayList<SimulationRun> items = new ArrayList<>();

	
	@SuppressWarnings("rawtypes")
	public static Project create(Map data) {
		Project instance = new Project();
		instance.from(data);
		return instance;
	}
	
	
	@SuppressWarnings("rawtypes")
	public void from(Map data) {
        items.clear();

//        ArrayList<HashMap<String, Object>> dataItems = MapUtil.getRawPackageContents(data, AgMIPFunctions.EXPERIMENTS_BUCKET_NAME);
//        assert((dataItems != null) && (dataItems.size() > 0));
        
        // TODO
        // add title and aquacrop version number
        // add global program parameters (these might be in the data)
        
        // create SimulationRuns from experiments
//        for (HashMap<String, Object> dataItem : dataItems) {
//        	SimulationRun sr = SimulationRun.create(dataItem);
//        	items.add(sr);
//        }
	}


	public List<SimulationRun> getItems() {
		return items;
	}
	
}
