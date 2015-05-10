package net.sf.ahtutils.monitor;

import net.sf.ahtutils.bootstrap.UtilsMonitorBootstrap;
import net.sf.ahtutils.monitor.worker.MonitoringWorker;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringWorker
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringWorker.class);
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static void main (String[] args)
	{
		UtilsMonitorTestBootstrap.init();
		
		MonitoringWorker mw = new MonitoringWorker(UtilsMonitorBootstrap.buildEmf(true));
		mw.initHardWiredTasks();
		
		Thread t = new Thread(mw);
		t.start();
	}
}