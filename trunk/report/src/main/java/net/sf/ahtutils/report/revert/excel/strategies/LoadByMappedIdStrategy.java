package net.sf.ahtutils.report.revert.excel.strategies;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
    		if (mapper.isObjectMapped(lutClass, id))
    		{
    			lookupEntity = mapper.getMappedObject(lutClass, id);
    			logger.info("Found entity: " +id +lookupEntity.toString());
    		}
    		else
    		{
    			lookupEntity = lutClass.newInstance();
    			invokeMethod("setId",
					      new Object[] { id },
					      lutClass,
					      lookupEntity);
    			mapper.addObjectForId(id, lookupEntity);
    			logger.info("MAPPED");
    		}
	    	
		} catch (Exception e) {
			e.getStackTrace();
			
		}
    	return lookupEntity;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		this.facade = facade;
	}
	
	 private void invokeMethod(String   methodName, 
				Object[] parameters,
				Class    targetClass,
				Object   target)        throws Exception
		{
		logger.trace("Invoking " +methodName);
		
		// Now find the correct method
		Method[] methods = targetClass.getMethods();
		Method m         = null;
		for (Method method : methods)
		{
			if (method.getName().equals(methodName))
			{
				m = method;
			}
		}
		
		if (Modifier.isPrivate(m.getModifiers()))
		{
			m.setAccessible(true);
		}
			m.invoke(target, parameters);
		}

}
