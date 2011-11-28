package net.sf.ahtutils.test;

import net.sf.exlp.util.io.LoggerInit;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilTest.class);
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
}