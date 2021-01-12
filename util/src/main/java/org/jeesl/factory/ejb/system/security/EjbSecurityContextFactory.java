package org.jeesl.factory.ejb.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSecurityContextFactory <CTX extends JeeslSecurityContext<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityContextFactory.class);
	
    private final Class<CTX> cCtx;
    
    public EjbSecurityContextFactory(final Class<CTX> cCtx)
    {
        this.cCtx = cCtx;
    } 
    
    public CTX build()
    {
    	CTX ejb = null;
    	
    	try
    	{
			ejb = cCtx.newInstance();
			ejb.setPosition(1);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}