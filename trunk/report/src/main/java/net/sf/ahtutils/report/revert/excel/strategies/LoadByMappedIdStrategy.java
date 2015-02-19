package net.sf.ahtutils.report.revert.excel.strategies;

import java.util.Hashtable;

import net.sf.ahtutils.db.xml.UtilsIdMapper;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadByMappedIdStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(LoadByMappedIdStrategy.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass) {
		Number number        = (Number) object;
		Long id              = number.longValue();;
		Class  lutClass      = null;
    	Object lookupEntity  = null;
    	
		if (id == null && !tempPropertyStore.containsKey("createEntityForUnknown"))
		{
			return null;
		}
		try {
    		lutClass = (Class) Class.forName(parameterClass);
    		UtilsIdMapper mapper = (UtilsIdMapper) this.tempPropertyStore.get("idMapper");
	    	lookupEntity = facade.find(lutClass, mapper.getMappedId(lutClass, id));
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
