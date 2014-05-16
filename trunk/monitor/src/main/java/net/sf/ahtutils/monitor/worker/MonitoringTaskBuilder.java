package net.sf.ahtutils.monitor.worker;

import java.util.TimerTask;
import java.util.concurrent.CompletionService;

import net.sf.ahtutils.monitor.result.net.DnsResult;
import net.sf.ahtutils.monitor.result.net.IcmpResults;
import net.sf.ahtutils.monitor.result.util.DebugResult;
import net.sf.ahtutils.monitor.task.net.DnsTask;
import net.sf.ahtutils.monitor.task.net.IcmpTask;
import net.sf.ahtutils.monitor.task.util.DebugTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringTaskBuilder extends TimerTask 
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTaskBuilder.class);
	
	private long counter;
	private CompletionService<DnsResult> csDns;
	private CompletionService<IcmpResults> csIcmp;
	private CompletionService<DebugResult> csDebug;

	public MonitoringTaskBuilder()
	{
		counter=0;
	}
	
	public void run()
	{
		if(csDns!=null){buildDnsTask();}
		if(csIcmp!=null){buildIcmpTask();}
		if(csDebug!=null){buildDebug();}
		counter++;
	}
	
	private void buildDnsTask()
	{
		DnsTask task = new DnsTask("8.8.8.8","test"+counter+".google.com");
		csDns.submit(task);
	}
	
	private void buildIcmpTask()
	{
		IcmpTask task = new IcmpTask("www.google.com");
		if(counter%60==0)
		{
			csIcmp.submit(task);
		}
	}
	
	private void buildDebug()
	{
		DebugTask task = new DebugTask();
		csDebug.submit(task);
	}
	
	public void setCsDns(CompletionService<DnsResult> csDns) {this.csDns = csDns;}
	public void setCsIcmp(CompletionService<IcmpResults> csIcmp) {this.csIcmp = csIcmp;}
	public void setCsDebug(CompletionService<DebugResult> csDebug) {this.csDebug = csDebug;}
}
