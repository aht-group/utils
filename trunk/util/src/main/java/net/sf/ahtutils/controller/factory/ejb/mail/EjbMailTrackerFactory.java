package net.sf.ahtutils.controller.factory.ejb.mail;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.mail.UtilsMailTracker;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EjbMailTrackerFactory<T extends UtilsMailTracker<S,L,U>,S extends UtilsStatus<L>, L extends UtilsLang, U extends EjbWithId>
{
	static Log logger = LogFactory.getLog(EjbMailTrackerFactory.class);
	
    final Class<T> clTracker;
	
    public static <T extends UtilsMailTracker<S,L,U>,S extends UtilsStatus<L>, L extends UtilsLang, U extends EjbWithId> EjbMailTrackerFactory<T,S,L,U> createFactory(final Class<T> clTracker)
    {
        return new EjbMailTrackerFactory<T,S,L,U>(clTracker);
    }
    
    public EjbMailTrackerFactory(final Class<T> clTracker)
    {
        this.clTracker = clTracker;
    } 
    
    public T create(S type, U user, long refId) {return create(type, user, refId, new Date());}
    public T create(S type, U user, long refId, Date created){return create(type, user, refId, created,-1);}
    public T create(S type, U user, long refId, Date created, int retryCounter)
    {
    	T ejb = null;
    	
    	try
    	{
			ejb = clTracker.newInstance();
			ejb.setType(type);
			ejb.setRefId(refId);
			ejb.setRecordCreated(created);
			ejb.setRetryCounter(retryCounter);
			ejb.setUser(user);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}