package net.sf.ahtutils.controller.factory.ejb.tracker;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.tracker.UtilsTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTrackerLog;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbTrackerLogFactory<TR extends UtilsTracker<TR,TL,T,S,L,D>,
			TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,
			T extends JeeslStatus<T,L,D>,
			S extends JeeslStatus<S,L,D>,
			L extends JeeslLang,
			D extends JeeslDescription>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTrackerLogFactory.class);
	
    final Class<TL> clTrackerLog;
	
    public static <TR extends UtilsTracker<TR,TL,T,S,L,D>,
	  TL extends UtilsTrackerLog<TR,TL,T,S,L,D>,
	  T extends JeeslStatus<T,L,D>,
	  S extends JeeslStatus<S,L,D>,
	  L extends JeeslLang,
	  D extends JeeslDescription> EjbTrackerLogFactory<TR,TL,T,S,L,D> createFactory(final Class<TL> clTrackerLog)
    {
        return new EjbTrackerLogFactory<TR,TL,T,S,L,D>(clTrackerLog);
    }
    
    public EjbTrackerLogFactory(final Class<TL> clTrackerLog)
    {
        this.clTrackerLog = clTrackerLog;
    } 
    
    public TL create(TR tracker, S status)
    {
    	TL ejb = null;
    	
    	try
    	{
			ejb = clTrackerLog.newInstance();
			ejb.setTracker(tracker);
			ejb.setStatus(status);
			ejb.setRecord(new Date());
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}