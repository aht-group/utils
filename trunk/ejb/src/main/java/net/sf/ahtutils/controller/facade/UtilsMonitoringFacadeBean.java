package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.factory.ejb.task.EjbTaskFactory;
import net.sf.ahtutils.interfaces.facade.UtilsMonitoringFacade;
import net.sf.ahtutils.interfaces.model.task.Task;
import net.sf.ahtutils.interfaces.model.with.EjbWithTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsMonitoringFacadeBean extends UtilsFacadeBean implements UtilsMonitoringFacade
{
	final static Logger logger = LoggerFactory.getLogger(UtilsMonitoringFacadeBean.class);
	
	public UtilsMonitoringFacadeBean(EntityManager em)
	{
		super(em);
	}

	@Override
	public <T extends Task, WT extends EjbWithTask<T>> T fcTask(Class<T> clTask, Class<WT> clWithTask, WT ejb)
	{
		ejb = em.find(clWithTask, ejb.getId());
		if(ejb.getTask()==null)
		{
			EjbTaskFactory<T> efTask = EjbTaskFactory.factory(clTask);
			T task = efTask.build(null);
			ejb.setTask(task);
			try
			{
				ejb = this.update(ejb);
			}
			catch (UtilsContraintViolationException e) {return fcTask(clTask, clWithTask, ejb);}
			catch (UtilsLockingException e) {return fcTask(clTask, clWithTask, ejb);}
		}
		return ejb.getTask();
	}
}