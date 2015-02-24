package net.sf.ahtutils.report.revert.excel.strategies;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Hashtable;

import net.sf.ahtutils.db.xml.UtilsIdMapper;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.report.revert.excel.ImportStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadByMappedNameStrategy implements ImportStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(LoadByMappedNameStrategy.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Object handleObject(Object object, String parameterClass) {
		String code          = object.toString();
		Class  lutClass      = null;
    	Object lookupEntity  = null;

    	if (code == null && !tempPropertyStore.containsKey("createEntityForUnknown"))
		{
			return null;
		}
		try {
    		lutClass = (Class) Class.forName(parameterClass);
    		UtilsIdMapper mapper = (UtilsIdMapper) this.tempPropertyStore.get("idMapper");
    		if (mapper.isMapped(lutClass, code))
    		{
    			lookupEntity = mapper.getMappedObject(lutClass, code);
    		}
    		else
    		{
    			lookupEntity = lutClass.newInstance();
    			invokeMethod("setName",
					      new Object[] { code },
					      lutClass,
					      lookupEntity);
    			mapper.addObjectForCode(code, lookupEntity);
    		}
	    	
		} catch (Exception e) {
			logger.error("An error occured while trying to import! " +e.getMessage());
			logger.trace("Creating new Entity of class " +parameterClass +" with Code " +code);
			
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
