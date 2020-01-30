package net.sf.ahtutils.controller.bl;

import java.util.Hashtable;

import javax.persistence.EntityManager;

import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;

import net.sf.ahtutils.interfaces.bl.UtilsStatusBl;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;

public class UtilsStatusBlBean extends JeeslFacadeBean implements UtilsStatusBl
{	
	public UtilsStatusBlBean(EntityManager em)
	{
		super(em);
	}

	@Override
	public <T extends EjbWithLangDescription<L, D>, L extends UtilsLang, D extends UtilsDescription>
			T verifiyLangs(Class<T> cl, Class<D> clD, T t, String[] langs)
	{
		t = em.find(cl, t.getId());
		if(t.getDescription()==null){t.setDescription(new Hashtable<String,D>());}
		for(String key : langs)
		{
			if(!t.getDescription().containsKey(key))
			{
				try
				{
					D d = clD.newInstance();
					d.setLkey(key);
					d.setLang("");
					
					t.getDescription().put(key, d);
					t = this.update(t);
				}
				catch (JeeslConstraintViolationException e) {e.printStackTrace();}
				catch (JeeslLockingException e) {e.printStackTrace();}
				catch (InstantiationException e) {e.printStackTrace();}
				catch (IllegalAccessException e) {e.printStackTrace();}
			}
		}
		return t;
	}
}