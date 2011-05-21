package net.sf.ahtutils.test.model.ejb.status.cli;

import java.util.Random;

import net.sf.ahtutils.model.ejb.status.AhtUtilsStatus;
import net.sf.ahtutils.test.model.ejb.status.TestStatus;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TstStatus
{
	static Log logger = LogFactory.getLog(TstStatus.class);
	
	private TestStatus test;
	private Random rnd;
	
	public TstStatus()
	{
		test = new TestStatus();
		rnd = new Random();
	}
	
	public void create()
	{
		AhtUtilsStatus status = TestStatus.create(rnd, "code");
		logger.debug(status);
	}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("config");
			loggerInit.init();	

		TstStatus test = new TstStatus();
		test.create();
    }
}