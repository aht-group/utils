package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Date;
import java.util.Hashtable;

import org.jeesl.api.controller.ImportStrategy;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTimeSeriesRecord implements ImportStrategy
{
    final static Logger logger = LoggerFactory.getLogger(CreateTimeSeriesRecord.class);

    private JeeslFacade facade;

    private Hashtable<String, Object> tempPropertyStore;
    public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
    public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

    @Override
    public Object handleObject(Object object, String parameterClass, String property) {

	// Add the value to be included in the TimeSeries to the context
	Date record = (Date) object;
	tempPropertyStore.put("TsRecord", record);
	//logger.info("Record " +record.toGMTString());
	return record;
    }

    @Override
    public void setFacade(JeeslFacade facade) {
        this.facade = facade;
    }

}
