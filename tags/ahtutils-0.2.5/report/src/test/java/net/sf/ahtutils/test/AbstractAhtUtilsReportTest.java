package net.sf.ahtutils.test;

import net.sf.ahtutils.report.AbstractAhtUtilsReportTst;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilsReportTest extends AbstractAhtUtilsReportTst
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilsReportTest.class);
	
	@BeforeClass
	public static void initDir()
	{
		reportFileLocation="src/main/resources/reports.ahtutils-report/reports.xml";
		loggerConfigFile  ="log4junit.xml";
		loggerConfigPath  ="config.ahtutils-report.test";
		initFile();
	}
	
	@Test
	public void test()
	{
		
	}
}