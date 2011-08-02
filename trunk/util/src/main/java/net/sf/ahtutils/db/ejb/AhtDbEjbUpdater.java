package net.sf.ahtutils.db.ejb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsContraintViolationException;
import net.sf.ahtutils.controller.interfaces.AhtUtilsStatusInterface;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.status.UtilsRemoveable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AhtDbEjbUpdater<C extends EjbWithCode>
{
	static Log logger = LogFactory.getLog(AhtDbEjbUpdater.class);
	
	final Class<C> codeClass;
	
	private Map<String,C> ejbInDb;

	public AhtDbEjbUpdater(final Class<C> codeClass)
    {
        this.codeClass = codeClass;
        ejbInDb = new Hashtable<String,C>();
    } 
	
	public static <C extends EjbWithCode> AhtDbEjbUpdater<C> createFactory(final Class<C> codeClass)
	{
		return new AhtDbEjbUpdater<C>(codeClass);
	}
	
	public void dbEjbs(List<C> list)
	{
		for(C c : list){ejbInDb.put(c.getCode(), c);}
	}
	
	public void actualAdd(String code)
	{
		if(ejbInDb.containsKey(code))
		{
			ejbInDb.remove(code);
		}
	}
	
	public List<C> getEjbForRemove()
	{
		List<C> result = new ArrayList<C>();
		for(String key : ejbInDb.keySet())
		{
			result.add(ejbInDb.get(key));
		}
		return result;
	}
	
	public void remove(AhtUtilsStatusInterface fUtils)
	{
		if(getEjbForRemove().size()>0)
		{
			logger.info("Removing "+getEjbForRemove().size()+" from "+codeClass.getSimpleName());
			for(C pc : getEjbForRemove())
			{
				if(pc instanceof UtilsRemoveable)
				{
					try {fUtils.rmAhtUtilsEntity((UtilsRemoveable)pc);}
					catch (AhtUtilsContraintViolationException e) {e.printStackTrace();}
				}
				else
				{
					logger.warn(pc.getClass().getSimpleName()+" does not implement "+UtilsRemoveable.class.getSimpleName());
				}
			}
		}	
	}
}
