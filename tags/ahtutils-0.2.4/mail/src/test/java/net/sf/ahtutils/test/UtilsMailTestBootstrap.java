package net.sf.ahtutils.test;

import net.sf.ahtutils.xml.AhtUtilsNsPrefixMapper;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsMailTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(UtilsMailTestBootstrap.class);
		
	public static Configuration init()
	{
		return init("src/test/resources/config.ahtutils-mail.test/mail.xml");
	}
	
	public static Configuration init(String configFile)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("src/test/resources/config.ahtutils-mail.test");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new AhtUtilsNsPrefixMapper());
		
		ConfigLoader.add(configFile);
		try
		{
			String cfn = ExlpCentralConfigPointer.getFile("ahtutils","mail.test").getAbsolutePath();
			ConfigLoader.add(cfn);
			logger.info("Using additional config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {e.printStackTrace();}
		Configuration config = ConfigLoader.init();					
		logger.debug("Config and Logger initialized");
		return config;
	}
}