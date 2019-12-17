package org.jeesl.factory.ejb.module.log;

import org.jeesl.interfaces.model.module.log.JeeslLogBook;
import org.jeesl.interfaces.model.module.log.JeeslLogScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLogBookFactory<LOG extends JeeslLogBook<SCOPE,?>,
							SCOPE extends JeeslLogScope<?,?,SCOPE,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLogBookFactory.class);
	
	private final Class<LOG> cLog;
    
	public EjbLogBookFactory(final Class<LOG> cLog)
	{  
        this.cLog = cLog;
	}
	    
	public LOG build(SCOPE scope)
	{
		LOG ejb = null;
		try
		{
			ejb = cLog.newInstance();
			ejb.setScope(scope);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}