package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;

import net.sf.ahtutils.interfaces.facade.UtilsMonitoringFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsMonitoringFacadeBean extends UtilsFacadeBean implements UtilsMonitoringFacade
{
	final static Logger logger = LoggerFactory.getLogger(UtilsMonitoringFacadeBean.class);
	
	protected EntityManager em;
	
	public UtilsMonitoringFacadeBean(EntityManager em)
	{
		super(em);
	}
}