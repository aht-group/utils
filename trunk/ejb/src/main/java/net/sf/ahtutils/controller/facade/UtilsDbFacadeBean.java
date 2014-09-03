
package net.sf.ahtutils.controller.facade;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.ahtutils.interfaces.facade.UtilsDbFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsDbFacadeBean extends UtilsFacadeBean implements UtilsDbFacade
{
	final static Logger logger = LoggerFactory.getLogger(UtilsDbFacadeBean.class);
	
	public UtilsDbFacadeBean(EntityManager em){this(em,false);}
	public UtilsDbFacadeBean(EntityManager em, boolean handleTransaction)
	{
		super(em,handleTransaction);
	}
	
	@Override public  long count(Class<?> cl)
	{
		Query q = em.createQuery("select count(*) FROM "+ cl.getSimpleName());

		Object o = q.getSingleResult();
		return (Long)o;
	}
	
	@Override
	public Map<Class<?>, Long> count(List<Class<?>> list)
	{
		Map<Class<?>, Long> result = new Hashtable<Class<?>,Long>();
		for(Class<?> c : list)
		{
			result.put(c,count(c));
		}
		return result;
	}
}