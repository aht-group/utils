package net.sf.ahtutils.db.ejb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AhtDbEjbUpdater<C extends EjbWithCode>
{
	final static Logger logger = LoggerFactory.getLogger(AhtDbEjbUpdater.class);
	
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
	
	public void remove(UtilsFacade fUtils)
	{
		if(getEjbForRemove().size()>0)
		{
			logger.info("Removing "+getEjbForRemove().size()+" from "+codeClass.getSimpleName());
			for(C pc : getEjbForRemove())
			{
				if(pc instanceof EjbRemoveable)
				{
					try {fUtils.rm((EjbRemoveable)pc);}
					catch (UtilsIntegrityException e) {e.printStackTrace();}
				}
				else
				{
					logger.warn(pc.getClass().getSimpleName()+" does not implement "+EjbRemoveable.class.getSimpleName());
				}
			}
		}	
	}
}
