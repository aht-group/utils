package net.sf.ahtutils.monitor.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.ahtutils.monitor.task.MonitoringTask;
import net.sf.ahtutils.test.UtilsMonitorTestBootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringTask
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringTask.class);
	
	public static void main (String... arguments )
	{
		UtilsMonitorTestBootstrap.init();
		
	    TimerTask fetchMail  = new MonitoringTask(1);

	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(fetchMail, new Date(), 1000);
	  }
}