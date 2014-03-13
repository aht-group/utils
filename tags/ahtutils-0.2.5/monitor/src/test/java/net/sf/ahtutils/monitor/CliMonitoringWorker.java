package net.sf.ahtutils.monitor;

import net.sf.ahtutils.monitor.MonitorWorker;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringWorker
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringWorker.class);
	
	public static void main (String[] args)
	{
		UtilsMonitorTestBootstrap.init();
		
		MonitorWorker mw = new MonitorWorker();
		Thread t = new Thread(mw);
		t.start();
	}
}