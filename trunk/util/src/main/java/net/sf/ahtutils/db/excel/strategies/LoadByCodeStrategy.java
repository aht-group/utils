package net.sf.ahtutils.db.excel.strategies;

import net.sf.ahtutils.db.excel.ImportStrategy;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadByCodeStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(LoadByCodeStrategy.class);
	
	private UtilsFacade facade;

	@Override
	public Object handleObject(Object object, String parameterClass) {
		String code = (String) object;
		logger.debug("Searching for Entity with Code " +code);
    	Class  lutClass      = null;
    	Object lookupEntity  = null;
		try {
			lutClass = (Class) Class.forName(parameterClass);
	    	lookupEntity = facade.fByCode(lutClass, code);
		} catch (Exception e) {
			logger.error("An error occured while trying to import! " +e.getMessage());
		}
    	return lookupEntity;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		this.facade = facade;
	}

}
