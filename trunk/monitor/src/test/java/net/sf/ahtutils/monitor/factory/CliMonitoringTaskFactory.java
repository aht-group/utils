package net.sf.ahtutils.monitor.factory;

import net.sf.ahtutils.monitor.worker.MonitoringScheduler;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringTaskFactory
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringTaskFactory.class);
	
	public static void main (String[] args)
	{
		UtilsMonitorTestBootstrap.init();
		
	    new MonitoringScheduler();

	  }
}