package net.sf.ahtutils.web.rest;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestUrlDelay
{
	final static Logger logger = LoggerFactory.getLogger(RestUrlDelay.class);
	
	public static String getUrl(Configuration config, String key)
	{
		String url = config.getString(key);
		
		StringBuffer sb = new StringBuffer();
		sb.append("REST connection to ");
		sb.append(url);
		
		boolean delay = false;
		if(!url.contains("localhost"))
		{
			sb.append("  .... delaying 5s");
			delay=true;
		}
		logger.warn(sb.toString());
		
		if(delay)
		{
			try {Thread.sleep(5000);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
		return url;
	}
}
