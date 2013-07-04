package net.sf.ahtutils.monitor;

import net.sf.ahtutils.monitor.IndicatorWorker;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliAnalysisApp
{
	final static Logger logger = LoggerFactory.getLogger(CliAnalysisApp.class);
	
	public static void main (String[] args)
	{
		UtilsMonitorTestBootstrap.init();
		
	    new IndicatorWorker();

	  }
}