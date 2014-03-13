package net.sf.ahtutils.monitor.factory;

import net.sf.ahtutils.interfaces.facade.UtilsMonitoringFacade;
import net.sf.ahtutils.monitor.DbCleaner;

public class AbstractTransmissionFactory
{
	protected UtilsMonitoringFacade fUm;
	protected DbCleaner dbCleaner;
	
	public  AbstractTransmissionFactory(UtilsMonitoringFacade fUm, DbCleaner dbCleaner)
	{
		this.fUm=fUm;
		this.dbCleaner=dbCleaner;
	}
	
	public AbstractTransmissionFactory(UtilsMonitoringFacade fUm)
	{
		this.fUm=fUm;
	}
}
