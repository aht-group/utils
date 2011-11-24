package net.sf.ahtutils.test;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AhtUtilsTstBootstrap
{
	static Log logger = LogFactory.getLog(AhtUtilsTstBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("config");
		loggerInit.init();
	}
}