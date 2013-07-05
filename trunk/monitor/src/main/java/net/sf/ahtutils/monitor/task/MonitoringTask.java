package net.sf.ahtutils.monitor.task;

import java.util.TimerTask;
import java.util.concurrent.CompletionService;

import net.sf.ahtutils.monitor.result.net.DnsResult;
import net.sf.ahtutils.monitor.result.net.IcmpResults;
import net.sf.ahtutils.monitor.task.net.DnsTask;
import net.sf.ahtutils.monitor.task.net.IcmpTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringTask extends TimerTask 
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTask.class);
	
	private long counter;
	private CompletionService<DnsResult> csDns;
	private CompletionService<IcmpResults> csIcmp;

	public MonitoringTask()
	{
		counter=0;
	}
	
	public void run()
	{
		if(csDns!=null){buildDnsTask();}
		if(csIcmp!=null){buildIcmpTask();}
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
	
	public void setCsDns(CompletionService<DnsResult> csDns) {this.csDns = csDns;}
	public void setCsIcmp(CompletionService<IcmpResults> csIcmp) {this.csIcmp = csIcmp;}
}
