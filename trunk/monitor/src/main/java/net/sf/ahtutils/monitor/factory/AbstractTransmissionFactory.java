package net.sf.ahtutils.monitor.factory;

import net.sf.ahtutils.bootstrap.UtilsMonitorBootstrap;
import net.sf.ahtutils.controller.facade.UtilsMonitoringFacadeBean;
import net.sf.ahtutils.interfaces.facade.UtilsMonitoringFacade;

public class AbstractTransmissionFactory
{
	protected UtilsMonitoringFacade fUm;
	
	public AbstractTransmissionFactory()
	{
		fUm = new UtilsMonitoringFacadeBean(UtilsMonitorBootstrap.buildEmf(false).createEntityManager());
	}
}
