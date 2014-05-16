package net.sf.ahtutils.monitor.worker;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.CompletionService;

import net.sf.ahtutils.monitor.result.net.DnsResult;
import net.sf.ahtutils.monitor.result.net.IcmpResults;
import net.sf.ahtutils.monitor.result.util.DebugResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringScheduler implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringScheduler.class);

	private CompletionService<DnsResult> csDns;
	private CompletionService<IcmpResults> csIcmp;
	
	private CompletionService<DebugResult> csDebug;
	

	public MonitoringScheduler()
    {

    }

	@Override
	public void run()
	{
        MonitoringTaskBuilder monitoringTask  = new MonitoringTaskBuilder();
    	monitoringTask.setCsDns(csDns);
    	monitoringTask.setCsIcmp(csIcmp);
    	monitoringTask.setCsDebug(csDebug);

 	    Timer timer = new Timer();
 	    logger.info("Starting timer");
 	    timer.scheduleAtFixedRate(monitoringTask, new Date(), 1000);
 	    logger.info("Time scheduled");
	}
	
	public void shutdown()
	{
		
	}
	
	public void setCsDns(CompletionService<DnsResult> csDns) {this.csDns = csDns;}
	public void setCsIcmp(CompletionService<IcmpResults> csIcmp) {this.csIcmp = csIcmp;}
	public void setCsDebug(CompletionService<DebugResult> csDebug) {this.csDebug = csDebug;}
}