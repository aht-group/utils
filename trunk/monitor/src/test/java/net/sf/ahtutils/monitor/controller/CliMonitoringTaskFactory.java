package net.sf.ahtutils.monitor.controller;

import net.sf.ahtutils.monitor.task.net.MonitoringTaskFactory;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringTaskFactory
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringTaskFactory.class);
	
	public static void main (String[] args)
	{
		UtilsMonitorTestBootstrap.init();
		
	    new MonitoringTaskFactory();

	  }
}