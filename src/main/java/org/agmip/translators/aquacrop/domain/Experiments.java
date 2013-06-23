package org.agmip.translators.aquacrop.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.agmip.ace.AceDataset;
import org.agmip.ace.AceExperiment;

/**
 * Data for a series of AgMIP crop experiments.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
public class Experiments {

	private List<Experiment> items = new ArrayList<Experiment>();
	
	
	public static Experiments create(AceDataset aceDataset) throws IOException {
		Experiments instance = new Experiments();
		instance.from(aceDataset);
		return instance;
	}

	
	public void from(AceDataset aceDataset) throws IOException {
		List<AceExperiment> aceExperiments = aceDataset.getExperiments();
        assert((aceExperiments != null) && (!aceExperiments.isEmpty()));

        items.clear();
        for (AceExperiment ae : aceExperiments) {
        	items.add(Experiment.create(ae));
        }
	}
	
	
	public List<Experiment> getItems() {
		return items;
	}
}
