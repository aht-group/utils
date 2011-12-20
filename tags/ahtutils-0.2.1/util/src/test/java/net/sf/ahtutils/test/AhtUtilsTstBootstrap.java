package net.sf.ahtutils.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AhtUtilsTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(AhtUtilsTstBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("config");
		loggerInit.init();
	}
}