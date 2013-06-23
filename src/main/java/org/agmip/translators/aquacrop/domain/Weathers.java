package org.agmip.translators.aquacrop.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.agmip.ace.AceDataset;
import org.agmip.ace.AceWeather;

/**
 * Data for a collection of AgMIP weathers, to be used for model input.
 * 
 * @author Rob Knapen, Alterra Wageningen UR, The Netherlands
 */
public class Weathers {

	private List<Weather> items = new ArrayList<Weather>();
	
	
	public static Weathers create(AceDataset aceDataset) throws IOException {
		Weathers instance = new Weathers();
		instance.from(aceDataset);
		return instance;
	}
	
	
	public void from(AceDataset aceDataset) throws IOException {
		List<AceWeather> aceWeathers = aceDataset.getWeathers();
        assert((aceWeathers != null) && (!aceWeathers.isEmpty()));

        items.clear();
        for (AceWeather aw : aceWeathers) {
        	items.add(Weather.create(aw));
        }
	}


	public List<Weather> getItems() {
		return items;
	}
	
}
