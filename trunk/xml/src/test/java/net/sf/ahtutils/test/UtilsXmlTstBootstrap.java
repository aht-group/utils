package net.sf.ahtutils.test;

import net.sf.ahtutils.xml.AhtUtilsNsPrefixMapper;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsXmlTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(UtilsXmlTstBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
		
		JaxbUtil.setNsPrefixMapper(new AhtUtilsNsPrefixMapper());
	}
}