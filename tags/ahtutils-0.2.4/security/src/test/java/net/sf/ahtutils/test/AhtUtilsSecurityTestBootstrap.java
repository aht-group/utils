package net.sf.ahtutils.test;

import net.sf.ahtutils.xml.AhtUtilsNsPrefixMapper;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AhtUtilsSecurityTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(AhtUtilsSecurityTestBootstrap.class);
	
	public static void init()
	{		
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("config.ahtutils-security.test");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new AhtUtilsNsPrefixMapper());
	}
}