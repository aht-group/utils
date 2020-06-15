package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Date;
import java.util.Hashtable;
import net.sf.ahtutils.report.revert.excel.configuration.JeeslDataImporterConstants;

import org.jeesl.api.controller.ImportStrategy;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.ts.config.JeeslTsInterval;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.stat.JeeslTsStatistic;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTimeSeriesData implements ImportStrategy
{
    final static Logger logger = LoggerFactory.getLogger(CreateTimeSeriesData.class);

    private JeeslFacade facade;

    private Hashtable<String, Object> tempPropertyStore;
    public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
    public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

    @Override
    public Object handleObject(Object object, String parameterClass, String property) {

	// Construct the TimeSeries related environment
	// The bridge, value and record will be taken from previous stages of the import (other strategies put them in context)
	Double		value	    = (Double)		tempPropertyStore.get("TsValue");
	Date		record	    = (Date)		tempPropertyStore.get("TsRecord");
	JeeslTsBridge   bridge	    = (JeeslTsBridge)   tempPropertyStore.get("TsBridge");

	// The other domain specific information about classes and objects need to be put on the context by the class executing the importer
	JeeslTsScope        scope   = (JeeslTsScope)    tempPropertyStore.get(JeeslDataImporterConstants.timeSeriesVariables.TsScope.toString());
	Class		dataClass   = (Class)		tempPropertyStore.get(JeeslDataImporterConstants.timeSeriesVariables.TsData.toString());
	JeeslTsInterval interval    = (JeeslTsInterval) tempPropertyStore.get(JeeslDataImporterConstants.timeSeriesVariables.TsInterval.toString());
	JeeslStatus	workspace   = (JeeslStatus)	tempPropertyStore.get(JeeslDataImporterConstants.timeSeriesVariables.TsWorkspace.toString());
	JeeslTsStatistic statistic  = (JeeslTsStatistic)tempPropertyStore.get(JeeslDataImporterConstants.timeSeriesVariables.TsStatistic.toString());

	// For debugging - debugging the other elements is adviced to do in the class that triggers the import
	// logger.info("Using Bridge for Ref ID " +bridge.getRefId());
	// logger.info("Using record "+record.toGMTString());
	// logger.info("Using value " +value);

	// Use the TimeSeries focused facade here and make sure the right one has been set in class that executes the import
	JeeslTsFacade       tsFacade    = (JeeslTsFacade)   facade;

	// Create the TimeSeries for the given parameters.
	JeeslTimeSeries ts	= null;
	JeeslTsData datapoint	= null;
	
	try {
	    // Create the timeseries for this datapoint
	    ts = tsFacade.fcTimeSeries(scope,interval,statistic,bridge);
	    
	    // Create an actual datapoint and put is to the context for first testing
	    datapoint = (JeeslTsData) dataClass.newInstance();
	    datapoint.setRecord(record);
	    datapoint.setTimeSeries(ts);
	    datapoint.setValue(value);
	    datapoint.setWorkspace(workspace);
	    //logger.info(ts.getBridge().getRefId()+ "");
	} catch (InstantiationException | IllegalAccessException | JeeslConstraintViolationException ex) {
	    logger.error("Could not create timeseries/datapoint " +ex.getMessage());
	}
	return datapoint;
    }

    @Override
    public void setFacade(JeeslFacade facade) {
        this.facade = facade;
    }

}
