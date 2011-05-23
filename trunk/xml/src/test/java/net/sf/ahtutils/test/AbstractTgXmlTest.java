package net.sf.ahtutils.test;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;

public class AbstractTgXmlTest
{
	static Log logger = LogFactory.getLog(AbstractTgXmlTest.class);	
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
}