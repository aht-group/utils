package net.sf.ahtutils.test;

import net.sf.exlp.util.io.LoggerInit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractUtilsMonitorTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsMonitorTest.class);
	
	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("config.ahtutils-monitor.test");
			loggerInit.init();
		}
    }
	
	@Test public void dummy() {}
}