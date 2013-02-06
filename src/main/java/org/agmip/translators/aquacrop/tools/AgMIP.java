package org.agmip.translators.aquacrop.tools;

public class AgMIP {

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
	
}
