package org.jeesl.jsf.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ImplementationVersion
{
	final static Logger logger = LoggerFactory.getLogger(ImplementationVersion.class);
	
    private ImplementationVersion() {}
    
    public static String version(String fqcn)
    {
    	String result = "unknown";
    	try
    	{
			result = Class.forName(fqcn).getPackage().getImplementationVersion();
		}
    	catch (ClassNotFoundException e) {e.printStackTrace();}
		return result;
    }
}
