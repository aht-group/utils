package net.sf.ahtutils.test;

import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilsJsfTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilsJsfTest.class);
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("config.ahtutils-jsf.test");
		loggerInit.init();
    }
	
	protected NsPrefixMapperInterface getPrefixMapper()
	{
		if(nsPrefixMapper==null){nsPrefixMapper = new AhtUtilsNsPrefixMapper();}
		return nsPrefixMapper;
	}
}