package net.sf.ahtutils.monitor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.sf.ahtutils.bootstrap.UtilsMonitorBootstrap;
import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.controller.interfaces.UtilsFacade;
import net.sf.ahtutils.monitor.result.net.DnsResult;
import net.sf.ahtutils.monitor.result.net.IcmpResult;
import net.sf.ahtutils.monitor.task.MonitoringTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisTransmission
{
	final static Logger logger = LoggerFactory.getLogger(MonitoringTask.class);
	
	public AnalysisTransmission()
	{		
		EntityManagerFactory emf = UtilsMonitorBootstrap.buildEmf(false);
        EntityManager em = emf.createEntityManager();
	
        UtilsFacade ufb = new UtilsFacadeBean(em);
        
        logger.info("First is "+ufb.fFirst(IcmpResult.class).toString());
        logger.info("Last is "+ufb.fLast(IcmpResult.class).toString());
        
        List<IcmpResult> list = ufb.all(IcmpResult.class);
        logger.debug("Size: "+list.size());
        
        for(IcmpResult item : list)
        {
        	logger.debug(item.toString());
        }
        
	}
}