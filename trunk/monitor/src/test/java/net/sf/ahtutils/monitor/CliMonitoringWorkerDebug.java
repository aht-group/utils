package net.sf.ahtutils.monitor;

import net.sf.ahtutils.monitor.factory.util.DebugMonitoringTaskFactory;
import net.sf.ahtutils.monitor.processor.util.DebugResultProcessor;
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
	
		DebugMonitoringTaskFactory dMtf1 = new DebugMonitoringTaskFactory("A");
		dMtf1.setResultProcessor(new DebugResultProcessor());
	
		DebugMonitoringTaskFactory dMtf2 = new DebugMonitoringTaskFactory("B");
		dMtf2.setResultProcessor(new DebugResultProcessor());
		
		MonitoringWorker mw = new MonitoringWorker();
		mw.addTaskFactory(dMtf1);
		mw.addTaskFactory(dMtf2);
		
		Thread t = new Thread(mw);
		t.start();
	}
}