package net.sf.ahtutils.monitor.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManagerFactory;

import net.sf.ahtutils.interfaces.controller.MonitoringResult;
import net.sf.ahtutils.interfaces.controller.MonitoringResultProcessor;
import net.sf.ahtutils.monitor.processor.net.DnsResultProcessor;
import net.sf.ahtutils.monitor.processor.net.IcmpResultProcessor;
import net.sf.ahtutils.monitor.processor.util.DebugResultProcessor;
import net.sf.ahtutils.monitor.result.net.DnsResult;
import net.sf.ahtutils.monitor.result.net.IcmpResults;
import net.sf.ahtutils.monitor.result.util.DebugResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoringWorker implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTaskBuilder.class);
	
	private EntityManagerFactory emf;
	private List<Thread> threads;
	
	private ExecutorService taskExecutor;
	private MonitoringScheduler mtf = new MonitoringScheduler();
	
	public MonitoringWorker(){this(null);}
	
	public MonitoringWorker(EntityManagerFactory emf)
	{
    	this.emf = emf;
    
    	taskExecutor = Executors.newFixedThreadPool(100);
        threads = new ArrayList<Thread>();
        
        mtf = new MonitoringScheduler();
        threads.add(new Thread(mtf));
	}

	@Override
	public void run()
	{
		logger.info("Starting "+this.getClass().getSimpleName());
		for(Thread t : threads)
		{
			t.start();
		}
		boolean active = true;
		while(active)
		{
			try {Thread.sleep(1000*10);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public void initHardWiredTasks()
	{
        CompletionService<DnsResult> csDns = new ExecutorCompletionService<DnsResult>(taskExecutor);
        CompletionService<IcmpResults> csIcmp = new ExecutorCompletionService<IcmpResults>(taskExecutor);
        
        logger.debug("Creating "+MonitoringScheduler.class.getSimpleName());
       
        mtf.setCsDns(csDns);
        mtf.setCsIcmp(csIcmp);
        
		
		threads.add(new Thread(new DnsResultProcessor(emf.createEntityManager(),csDns)));
		threads.add(new Thread(new IcmpResultProcessor(emf.createEntityManager(),csIcmp)));
	}
	
	public void initDebugTasks()
	{
		logger.debug("Creating Debgug "+MonitoringScheduler.class.getSimpleName());
		CompletionService<DebugResult> csDebug = new ExecutorCompletionService<DebugResult>(taskExecutor);
		
		mtf.setCsDebug(csDebug);
		
		threads.add(new Thread(new DebugResultProcessor(csDebug)));
	}
	
	public <R extends MonitoringResult> void addResultProcessor(MonitoringResultProcessor resultProcessor, Class<R> clResult)
	{
		logger.info("Adding "+MonitoringResultProcessor.class.getSimpleName()+": "+resultProcessor.getClass().getSimpleName());
		
		CompletionService<DnsResult> csDns = new ExecutorCompletionService<DnsResult>(taskExecutor);
		
		threads.add(new Thread(resultProcessor));
	}
}