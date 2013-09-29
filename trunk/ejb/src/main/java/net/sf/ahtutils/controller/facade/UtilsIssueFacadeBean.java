package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.factory.ejb.issue.EjbTaskFactory;
import net.sf.ahtutils.interfaces.facade.UtilsIssueFacade;
import net.sf.ahtutils.interfaces.model.issue.UtilsTask;
import net.sf.ahtutils.interfaces.model.with.EjbWithTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsIssueFacadeBean extends UtilsFacadeBean implements UtilsIssueFacade
{
	final static Logger logger = LoggerFactory.getLogger(UtilsIssueFacadeBean.class);
	
	public UtilsIssueFacadeBean(EntityManager em)
	{
		super(em);
	}

	@Override
	public <T extends UtilsTask<T>, WT extends EjbWithTask<T>> T fcTask(Class<T> clTask, Class<WT> clWithTask, WT ejb)
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

	@Override
	public <T extends UtilsTask<T>> T load(Class<T> clTask, T task)
	{
		task = em.find(clTask, task.getId());
		if(task.getChilds()!=null && task.getChilds().size()>0)
		{
			for(T t : task.getChilds())
			{
				t = this.load(clTask, t);
			}
		}
		return task;
	}
}