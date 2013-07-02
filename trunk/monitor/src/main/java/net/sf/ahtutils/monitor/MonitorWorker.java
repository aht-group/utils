package net.sf.ahtutils.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.sf.ahtutils.bootstrap.UtilsMonitorBootstrap;
import net.sf.ahtutils.monitor.controller.MonitoringTask;
import net.sf.ahtutils.monitor.controller.MonitoringTaskFactory;
import net.sf.ahtutils.monitor.processor.net.DnsResultProcessor;
import net.sf.ahtutils.monitor.processor.net.IcmpResultProcessor;
import net.sf.ahtutils.monitor.result.net.DnsResult;
import net.sf.ahtutils.monitor.result.net.IcmpResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorWorker implements Runnable
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTask.class);
	
	private EntityManagerFactory emf;
	private List<Thread> threads;
	
	public MonitorWorker()
	{
    	emf = UtilsMonitorBootstrap.buildEmf(true);
        EntityManager em = emf.createEntityManager();
    
        threads = new ArrayList<Thread>();
        initTasks();
	}

	@Override
	public void run()
	{
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
	
	private void initTasks()
	{
		ExecutorService taskExecutor = Executors.newFixedThreadPool(100);
        CompletionService<DnsResult> csDns = new ExecutorCompletionService<DnsResult>(taskExecutor);
        CompletionService<IcmpResult> csIcmp = new ExecutorCompletionService<IcmpResult>(taskExecutor);
        
        logger.debug("Creating "+MonitoringTaskFactory.class.getSimpleName());
        MonitoringTaskFactory mtf = new MonitoringTaskFactory();
        mtf.setCsDns(csDns);
        mtf.setCsIcmp(csIcmp);
        
		threads.add(new Thread(mtf));
		threads.add(new Thread(new DnsResultProcessor(emf.createEntityManager(),csDns)));
		threads.add(new Thread(new IcmpResultProcessor(emf.createEntityManager(),csIcmp)));
       
	}
}