package net.sf.ahtutils.controller.factory.ejb.mail;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.mail.UtilsMailTracker;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EjbMailTrackerFactory<T extends UtilsMailTracker<S,L>,S extends UtilsStatus<L>, L extends UtilsLang>
{
	static Log logger = LogFactory.getLog(EjbMailTrackerFactory.class);
	
    final Class<T> clType;
	
    public static <T extends UtilsMailTracker<S,L>,S extends UtilsStatus<L>, L extends UtilsLang> EjbMailTrackerFactory<T,S,L> createFactory(final Class<T> clType)
    {
        return new EjbMailTrackerFactory<T,S,L>(clType);
    }
    
    public EjbMailTrackerFactory(final Class<T> clType)
    {
        this.clType = clType;
    } 
    
    public T create(S type, long refId, Date created)
    {
    	T ejb = null;
    	
    	try
    	{
			ejb = clType.newInstance();
			ejb.setType(type);
			ejb.setRefId(refId);
			ejb.setRecordCreated(created);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}