package net.sf.ahtutils.monitor;

import net.sf.ahtutils.monitor.worker.MonitoringWorker;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringWorkerDebug
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringWorkerDebug.class);
	
	public static void main (String[] args)
	{
		UtilsMonitorTestBootstrap.init();
		
		MonitoringWorker mw = new MonitoringWorker();
		mw.initDebugTasks();
		
		Thread t = new Thread(mw);
		t.start();
	}
}