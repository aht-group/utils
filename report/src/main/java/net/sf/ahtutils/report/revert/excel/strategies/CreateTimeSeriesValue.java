package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;

import org.jeesl.api.controller.ImportStrategy;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTimeSeriesValue implements ImportStrategy
{
    final static Logger logger = LoggerFactory.getLogger(CreateTimeSeriesValue.class);

    private JeeslFacade facade;

    private Hashtable<String, Object> tempPropertyStore;
    public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
    public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

    @Override
    public Object handleObject(Object object, String parameterClass, String property) {

	// Add the value to be included in the TimeSeries to the context
	Double value = (Double) object;
	tempPropertyStore.put("TsValue", value);
	//logger.info("Value " +value);
	return value;
    }

    @Override
    public void setFacade(JeeslFacade facade) {
        this.facade = facade;
    }

}
