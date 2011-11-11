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
	
    final Class<T> clTracker;
	
    public static <T extends UtilsMailTracker<S,L>,S extends UtilsStatus<L>, L extends UtilsLang> EjbMailTrackerFactory<T,S,L> createFactory(final Class<T> clTracker)
    {
        return new EjbMailTrackerFactory<T,S,L>(clTracker);
    }
    
    public EjbMailTrackerFactory(final Class<T> clTracker)
    {
        this.clTracker = clTracker;
    } 
    
    public T create(S type, long refId, Date created)
    {
    	T ejb = null;
    	
    	try
    	{
			ejb = clTracker.newInstance();
			ejb.setType(type);
			ejb.setRefId(refId);
			ejb.setRecordCreated(created);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}