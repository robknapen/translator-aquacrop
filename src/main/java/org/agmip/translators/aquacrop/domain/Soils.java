package org.agmip.translators.aquacrop.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.agmip.ace.AceDataset;
import org.agmip.ace.AceSoil;

/**
 * Data for a collection of AgMIP soils, to be used for model input.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
public class Soils {

	private List<Soil> items = new ArrayList<Soil>();
	
	public static Soils create(AceDataset aceDataset) throws IOException {
		Soils instance = new Soils();
		instance.from(aceDataset);
		return instance;
	}

	
	private void from(AceDataset aceDataset) throws IOException {
		List<AceSoil> aceSoils = aceDataset.getSoils();
        assert((aceSoils != null) && (!aceSoils.isEmpty()));
        items.clear();
        for (AceSoil as : aceSoils) {
        	items.add(Soil.create(as));
        }
	}


	public List<Soil> getItems() {
		return items;
	}

}
