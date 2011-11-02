package net.sf.ahtutils;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AhtUtilsBootstrap
{
	static Log logger = LogFactory.getLog(AhtUtilsBootstrap.class);
	
	public static Configuration init()
	{
		String configFile = "ahtutils-util/ahtutils.xml";
		return init(configFile);
	}
	
	public static Configuration init(String configFile)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("ahtutils-util");
			loggerInit.init();
						
		ConfigLoader.add(configFile);
		Configuration config = ConfigLoader.init();					
		logger.debug("Config and Logger initialized");
		return config;
	}
}